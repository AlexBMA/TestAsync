package com.test.app.services;

import com.test.app.dto.SmartDocumentDTO;
import com.test.app.mapper.SolrInputDocumentMapper;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UploadAsyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadAsyncService.class);

    @Value("${upload.location}")
    private String UPLOAD_DIR;

    @Value("${solr.url}")
    private String SOLR_URL;

    @Async("taskExecutorSmartBox")
    public CompletableFuture<List<SmartDocumentDTO>> transformToDTO(MultipartFile[] multipartFiles){

        List<SmartDocumentDTO> result = Stream
                .of(multipartFiles)
                .parallel()
                .map(item -> SolrInputDocumentMapper.mapMultipartToDocumentDTO(item, UPLOAD_DIR))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(result);
    }

    @Async("taskExecutorSmartBox")
    public CompletableFuture<List<SmartDocumentDTO>> transformToDTO(List<SolrInputDocument> solrInputDocuments){

        List<SmartDocumentDTO> result = solrInputDocuments
                .stream()
                .map(SolrInputDocumentMapper::mapSolrInputDocumentToDto)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(result);
    }

    @Async("taskExecutorSmartBox")
    public List<SolrInputDocument> saveMultipleDocumentsToSolr(List<SolrInputDocument> solrInputDocuments, String coreName){
        HttpSolrClient httpSolrClient = new  HttpSolrClient
                .Builder(SOLR_URL + coreName)
                .build();

        try {
            httpSolrClient.add(solrInputDocuments);
            httpSolrClient.commit();
            return solrInputDocuments;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return solrInputDocuments;
        }
    }

    @Async("taskExecutorDocumentIo")
    public CompletableFuture<Boolean> saveDocumentToDisk(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + originalFilename);

        try {
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
            LOGGER.info("Done with writing document to disk {} ",originalFilename,""+Thread.currentThread().getName());
            return CompletableFuture.completedFuture(Boolean.TRUE);
        } catch (IOException e) {
            LOGGER.info("Error with writing document to disk {} error is {} ",originalFilename,e,""+Thread.currentThread().getName());
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }

    }

    @Async("taskExecutorSmartBox")
    public CompletableFuture<List<SolrInputDocument>> transformToSolrInputDocument(List<SmartDocumentDTO> smartDocumentDTOList) {
        List<SolrInputDocument> result = smartDocumentDTOList
                .stream()
                .map(SolrInputDocumentMapper::mapDTOToSolrInputDocument)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(result);
    }


}
