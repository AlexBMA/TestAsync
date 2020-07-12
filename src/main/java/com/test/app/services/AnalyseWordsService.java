package com.test.app.services;

import com.test.app.dto.SmartDocumentDTO;
import com.test.app.mapper.SolrInputDocumentMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnalyseWordsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyseWordsService.class);

    @Value("${solr.url}")
    private String SOLR_URL;


    @Async("taskExecutorWords")
    public CompletableFuture<List<SmartDocumentDTO>> getAllSolrDocument(String coreName){
        HttpSolrClient httpSolrClient = new  HttpSolrClient
                .Builder(SOLR_URL + coreName)
                .build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:*");
        solrQuery.setSort("name", SolrQuery.ORDER.asc);
        solrQuery.setRows(1000);

        try {
            QueryResponse response = httpSolrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();

            List<SmartDocumentDTO> resultList = results
                    .stream()
                    .map(SolrInputDocumentMapper::mapSolrInputDocumentToDto)
                    .collect(Collectors.toList());

            return CompletableFuture.completedFuture(resultList);

        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(new ArrayList<>());
        }

    }


    @Async("taskExecutorWords")
    public CompletableFuture<List<SolrInputDocument>> saveWords(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<SolrInputDocument> solrItems = parserFileForSolr(file);
        LOGGER.info("saving list of user of size {}", solrItems.size(), "" + Thread.currentThread().getName());
        long endTime = System.currentTimeMillis();

        LOGGER.info("Saving to solr done");
        LOGGER.info("Total time {}", endTime - start);
        return CompletableFuture.completedFuture(solrItems);
    }


    private List<SolrInputDocument> parserFileForSolr(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            PDDocument doc = PDDocument.load(inputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String[] split = pdfTextStripper
                    .getText(doc)
                    .trim()
                    .split("\\s");

            Set<String> unique = Stream
                    .of(split)
                    .filter(item -> !item.isBlank())
                    .collect(Collectors.toSet());

            return unique
                    .stream()
                    .map(this::mapItemToSolrInputDocument)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private SolrInputDocument mapItemToSolrInputDocument(String item) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("id",System.currentTimeMillis()+Math.random());
        solrInputDocument.setField("name",item);
        return solrInputDocument;
    }

    @Async("taskExecutorWords")
    public Set<String> extractWords(List<SmartDocumentDTO> list) {

        Set<String> allWords = new HashSet<>();

        list.parallelStream().forEach(item->{

            PDDocument document = null;
            try {
                document = PDDocument.load(new File(item.folderPath));
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                String text = pdfTextStripper.getText(document);

                String[] split = text.trim().split("\\s");

                Set<String> wordSet = Stream
                        .of(split)
                        .filter(word -> !word.trim().isBlank())
                        .collect(Collectors.toSet());

                allWords.addAll(wordSet);

                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return allWords;
    }

    public CompletableFuture<List<SolrInputDocument>> fromWordsToSolrInputDocument(Set<String> set) {




        return null;
    }
}
