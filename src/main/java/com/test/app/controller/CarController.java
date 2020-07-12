package com.test.app.controller;

import com.test.app.configuration.DoneQue;
import com.test.app.configuration.SingleFolderByteMap;
import com.test.app.configuration.TaskExecutorSingleton;
import com.test.app.dto.FileForLater;
import com.test.app.dto.SmartDocumentDTO;
import com.test.app.services.CarService;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    private static final String UPLOAD_DIR ="C:\\Users\\Alexandru\\Desktop\\outpuFolder\\";

    @RequestMapping (value = "/ve3"
            , method = RequestMethod.POST
            , consumes={MediaType.MULTIPART_FORM_DATA_VALUE}
            , produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile3(@RequestParam(value = "files") MultipartFile[] files) {

        ConcurrentHashMap<String, List<FileForLater>> folderMultipartMap = SingleFolderByteMap.getFolderMultipartMap();
        ConcurrentLinkedQueue<SolrInputDocument> linkedQueue = new ConcurrentLinkedQueue<>();
        createFolderArray(folderMultipartMap);

        Stream.of(files).parallel().forEach(file->{
            String originalFilename = file.getOriginalFilename();
            String filenameExtension = StringUtils.getFilenameExtension(originalFilename);

            String idValue = String.valueOf(System.currentTimeMillis() + Math.random());
            SolrInputDocument solrInputDocument = mapToSolrInputDocument(originalFilename, filenameExtension, idValue);
            linkedQueue.add(solrInputDocument);

            addFileForLaterProcessing(folderMultipartMap, file, idValue);
        });

        writeNewFilesToDisk("aaa",folderMultipartMap);

        folderMultipartMap.remove("aaa");

        ConcurrentLinkedQueue<SmartDocumentDTO> dtoQue = new ConcurrentLinkedQueue<>();
        mapToOutputDTO(linkedQueue, dtoQue);

        return  ResponseEntity.ok(dtoQue);

    }

    private void createFolderArray(ConcurrentHashMap<String, List<FileForLater>> folderMultipartMap) {
        List<FileForLater> currentFolder = folderMultipartMap.computeIfAbsent("aaa", k -> new ArrayList<>());
    }

    private void writeNewFilesToDisk(String path, ConcurrentHashMap<String, List<FileForLater>> folderMultipartMap){
        List<FileForLater> list = folderMultipartMap.get(path);
        ThreadPoolTaskExecutor executor = TaskExecutorSingleton.getExecutor();

        int poolSize = executor.getMaxPoolSize();
        if(!list.isEmpty()){

            int size = list.size();
            int sliceSize = size / poolSize;
            for(int i=0;i<poolSize;i++){
                int fromIndex = i*sliceSize;
                int toIndex = (i*sliceSize) + sliceSize;
                List<FileForLater> fileForLater = list.subList(fromIndex, toIndex);
                executor.execute(()->writeToDisk(fileForLater));
            }

            int finalSlice = size % poolSize;
            List<FileForLater> fileForLater = list.subList(size -finalSlice, size);
            executor.execute(()->writeToDisk(fileForLater));

        }
    }

    private void writeToDisk(List<FileForLater> list) {
        list.forEach(item->{

            try {
                OutputStream outputStream = new FileOutputStream(UPLOAD_DIR+item.getName());
                outputStream.write(item.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private SolrInputDocument mapToSolrInputDocument(String originalFilename, String filenameExtension, String idValue) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("id", idValue);
        solrInputDocument.setField("name", StringUtils.getFilename(originalFilename));
        solrInputDocument.setField("path", StringUtils.cleanPath(originalFilename));
        solrInputDocument.setField("type", filenameExtension);
        if (filenameExtension.equals("pdf")) solrInputDocument.setField("is_pdf", "true");
        else solrInputDocument.setField("is_pdf", "false");
        solrInputDocument.setField("ai_finished", "false");
        solrInputDocument.setField("folder_path", UPLOAD_DIR + originalFilename);
        return solrInputDocument;
    }

    private void mapToOutputDTO(ConcurrentLinkedQueue<SolrInputDocument> linkedQueue, ConcurrentLinkedQueue<SmartDocumentDTO> dtoQue) {
        linkedQueue.parallelStream().forEach(item->{
            SmartDocumentDTO smartDocumentDTO = new SmartDocumentDTO();
            smartDocumentDTO.id = item.get("id").getValue().toString();
            smartDocumentDTO.name =  item.get("name").getValue().toString();
            smartDocumentDTO.path = item.get("path").getValue().toString();
            smartDocumentDTO.type = item.get("type").getValue().toString();
            smartDocumentDTO.isPdf = item.get("is_pdf").getValue().toString();
            smartDocumentDTO.aiReady = item.get("ai_finished").getValue().toString();
            smartDocumentDTO.folderPath = item.get("folder_path").getValue().toString();
            dtoQue.add(smartDocumentDTO);

        });
    }

    private void addFileForLaterProcessing(ConcurrentHashMap<String, List<FileForLater>> folderMultipartMap, MultipartFile file, String idValue) {
        List<FileForLater> filesForFolder = folderMultipartMap.get("aaa");

        addToListFromFile(file, idValue, filesForFolder);

        folderMultipartMap.put("aaa",filesForFolder);
    }

    private void addToListFromFile(MultipartFile file, String idValue, List<FileForLater> filesForFolder) {
        FileForLater fileForLater = new FileForLater();
        fileForLater.setId(idValue);
        fileForLater.setName(file.getOriginalFilename());
        try {
            fileForLater.setBytes(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        filesForFolder.add(fileForLater);
    }


    @RequestMapping (value = "/ve2"
            , method = RequestMethod.POST
            , consumes={MediaType.MULTIPART_FORM_DATA_VALUE}
            , produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile2(@RequestParam(value = "files") MultipartFile[] files) {
        List<Future<?>> listFutures = new LinkedList<>();
        ThreadPoolTaskExecutor executor = TaskExecutorSingleton.getExecutor();

        MultipartFile[] clone = files.clone();

        for(final MultipartFile file: files){
            try {

                Future<?> submit = executor.submit(() -> {
                    try {
                        carService.getWords(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                listFutures.add(submit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Set<?> collect = listFutures
                .stream()
                .map(item -> {
            try {
                return item.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return "not";
            }
        }).collect(Collectors.toSet());
        int size = listFutures.size();
        int sizeVector = DoneQue.getVector().size();

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @RequestMapping (method = RequestMethod.POST
            , consumes={MediaType.MULTIPART_FORM_DATA_VALUE}
            , produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile(@RequestParam(value = "files") MultipartFile[] files) {

        List<Future<CompletableFuture<String>>> list = new LinkedList<>();
        ThreadPoolTaskExecutor executor = TaskExecutorSingleton.getExecutor();

        for(final MultipartFile file: files) {
                Future<CompletableFuture<String>> submit = executor
                        .submit(() -> carService.constructWordsMap(file));
                list.add(submit);
            }

        Set<String> collect = list.stream().map(item -> {
            try {
                return item.get().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return "not done";
        }).collect(Collectors.toSet());

        long size = list.size();
        long listSize = collect.size();


//        while (listSize != size) {
//            System.out.println("Not done");
//        }

        carService.saveCarsToSolr();

        return ResponseEntity.status(HttpStatus.CREATED).build();



//        List<Future<CompletableFuture<List<Car>>>> list = new LinkedList<>();
//        try {
//            for(final MultipartFile file: files) {
//                Future<CompletableFuture<List<Car>>> submit = executor
//                        .submit(() -> carService.saveCars(file));
//                list.add(submit);
//            }
//
//            int size = list.size();
//            List<Boolean> collect = list
//                    .stream()
//                    .map(Future::isDone)
//                    .collect(Collectors.toList());
//
//            while (collect.size() != size){
//                System.out.println("Not done");
//            }
//
//
////            for(final MultipartFile file: files) {
////                listCompletableFutureCallable.add(() -> carService.saveCars(file));
////            }
////
////            executor.invokeAll(listCompletableFutureCallable)
////                    .forEach(item->{
////
////                        while (!item.isDone()){
////                            System.out.println("Is not done");
////                        }
////
////                    });
//
//
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//
//        } catch(final Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//
//        }

    }



}
