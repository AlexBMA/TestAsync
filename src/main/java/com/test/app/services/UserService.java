package com.test.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    Object target;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<String>>  saveNames(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<String> names = parserCsv(file);
        LOGGER.info("saving list of user of size {}",names.size(),""+Thread.currentThread().getName());
        long endTime = System.currentTimeMillis();

        LOGGER.info("Total time {}",endTime-start);

        return CompletableFuture.completedFuture(names);
    }

    private List<String> parserCsv(MultipartFile file) throws Exception {
        final List<String> names = new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine())!=null){
                final String[] data = line.split(",");
                names.add(data[1]);
            }

            return names;

        } catch (IOException e) {
            LOGGER.error("Failed to parse csv file {}",e);
            throw new Exception("Failed to parse csv file {}",e);
        }

    }
}
