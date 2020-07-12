package com.test.app.controller;

import com.test.app.dto.SmartDocumentDTO;
import com.test.app.services.AnalyseWordsService;
import com.test.app.services.UploadAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class FileAsyncControllerTest {

    @Autowired
    private AnalyseWordsService analyseWordsService;

    @Autowired
    private UploadAsyncService uploadAsyncService;

    @Value("${solr.coreName}")
    private String coreName;

    @GetMapping(value = "/files-words-to-solr")
    public ResponseEntity saveToSolrWords(){

        analyseWordsService.getAllSolrDocument(coreName)
                .thenApply(list -> analyseWordsService.extractWords(list))
                .thenApply(set -> analyseWordsService.fromWordsToSolrInputDocument(set));

        //TODO save in solr

        return ResponseEntity.ok("done");
    }

    @PostMapping(value = "/files"
            ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity saveToSolrWords(@RequestParam("files") MultipartFile[] files) throws Exception {

        for (MultipartFile file : files) {
            analyseWordsService.saveWords(file);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(value = "/files-save-solr"
            ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity saveToSolr(@RequestParam("files") MultipartFile[] files){

        for (MultipartFile file : files) {
            uploadAsyncService.saveDocumentToDisk(file);
        }

        CompletableFuture<List<SmartDocumentDTO>> listOfFutureDTOS = uploadAsyncService.transformToDTO(files);

        try {
            List<SmartDocumentDTO> smartDocumentDTOList = listOfFutureDTOS.get();

            uploadAsyncService.transformToSolrInputDocument(smartDocumentDTOList)
                    .thenApply(list -> uploadAsyncService.saveMultipleDocumentsToSolr(list, coreName));

            return ResponseEntity.ok(smartDocumentDTOList);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

}
