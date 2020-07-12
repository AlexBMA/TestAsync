package com.test.app.controller;

import com.test.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class NamesController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users"
            ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity saveUsers(@RequestParam("file") MultipartFile[] files) throws Exception {

        for (MultipartFile file : files) {
            userService.saveNames(file);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
