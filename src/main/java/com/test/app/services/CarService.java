package com.test.app.services;

import com.test.app.configuration.DoneQue;
import com.test.app.configuration.SingleWordsMap;
import com.test.app.domain.Car;
import com.test.app.repository.CarRepository;
import com.test.app.smartbox.SmartBoxMapper;
import com.test.app.smartbox.SmartDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

@Service
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository carRepository;

    public void saveCarsToSolr(){

        Map<String, Long> wordMap = SingleWordsMap.getWordMap();

        CopyOnWriteArrayList<SolrInputDocument> vector = new CopyOnWriteArrayList<>();
        //Vector<SolrInputDocument> vector = new Vector<>();

        wordMap.keySet()
                .parallelStream()
                .forEach(item->{
                    SmartDocument smartDocument = new SmartDocument();
                    smartDocument.setName(item);
                    smartDocument.setPath(String.valueOf(wordMap.get(item)));
                    vector.add(SmartBoxMapper.createSmartDocument(smartDocument));
        });

        int sliceSize = vector.size() / 4;

        for (int i = 0; i < 4; i++) {
            int toIndex = sliceSize + (sliceSize*i);
            int fromIndex = i *sliceSize;

            addToSolr(vector,toIndex,fromIndex);
        }

//        int size = listSubmits.size();
//        long count = listSubmits
//                .stream()
//                .map(Future::isDone)
//                .count();
//
//        while (count != size) System.out.println("not done");

        System.out.println("done with solr");
    }

    private String addToSolr(CopyOnWriteArrayList<SolrInputDocument> vector, int toIndex, int fromIndex) {
        List<SolrInputDocument> list = vector.subList(fromIndex, toIndex);

        HttpSolrClient httpSolrClient = new  HttpSolrClient
                .Builder("http://localhost:8983/solr"+"/testS2")
                .build();

        try {
            httpSolrClient.add(list);
            httpSolrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

        return "true";
    }

    public Future<String> constructWordsMapVersion2(final MultipartFile file) throws Exception {
        getWords(file);
        return CompletableFuture.completedFuture("done");
    }


    public CompletableFuture<String> constructWordsMap(final MultipartFile file) throws Exception {
        getWords(file);
        return CompletableFuture.completedFuture("done");
    }

    public CompletableFuture<List<Car>> saveCars(final MultipartFile file) throws Exception {

        final long start = System.currentTimeMillis();

        List<Car> cars = parseCSVFile(file);

        //LOGGER.info("Saving a list of cars of size {} records", cars.size());

        carRepository.saveAll(cars);

        //LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
        return CompletableFuture.completedFuture(cars);

    }

    public void getWords(final MultipartFile file) throws Exception {

        Map<String, Long> wordMap = SingleWordsMap.getWordMap();

        System.out.println(file.getOriginalFilename());
        InputStream inputStream = null;
        PDDocument document = null;
        try {
            inputStream = file.getInputStream();

            document = PDDocument.load(inputStream);

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            String[] split = text.split("\\s");
            Arrays.asList(split).forEach(item->{

                Long count = wordMap.get(item);
                if (count == null) {
                    wordMap.put(item, 1L);
                } else {
                    count++;
                    wordMap.put(item, count);
                }

            });

            document.close();

            System.out.println("Done "+file.getOriginalFilename());
            DoneQue.getVector().add(file.getOriginalFilename());

        } catch (Exception e) {
            if (document != null) document.close();
            LOGGER.error("Failed to parse PDF file {}", e);
            DoneQue.getVector().add(file.getOriginalFilename());
            throw new Exception("Failed to parse PDF file {}", e);
        }
        System.out.println(Thread.currentThread().getName());
    }

    private List<Car> parseCSVFile(final MultipartFile file) throws Exception {

        final List<Car> cars = new ArrayList<>();

        System.out.println(file.getOriginalFilename());
        InputStream inputStream = null;
        PDDocument document = null;
        try {
            inputStream = file.getInputStream();

            document = PDDocument.load(inputStream);

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            String[] split = text.split("\\s");
            Arrays.asList(split).forEach(item->{
                final Car car = new Car();
                car.setWord(item);
                cars.add(car);
            });

            document.close();

        } catch (Exception e) {
            if (document != null) document.close();
            LOGGER.error("Failed to parse PDF file {}", e);
            throw new Exception("Failed to parse PDF file {}", e);
        }

        return cars;


//        try {
//
//            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//
//                String line;
//
//                while ((line=br.readLine()) != null) {
//
//                    final String[] data=line.split(";");
//                    final Car car=new Car();
//                    car.setManufacturer(data[0]);
//                    car.setModel(data[1]);
//                    car.setType(data[2]);
//                    cars.add(car);
//
//                }
//
//                return cars;
//
//            }
//
//        } catch(final IOException e) {
//            LOGGER.error("Failed to parse CSV file {}", e);
//            throw new Exception("Failed to parse CSV file {}", e);
//        }

    }




}
