package com.test.app.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }

    @Bean(name ="taskExecutorWords")
    public Executor taskExecutorWords(){
        LOGGER.debug("Creating Async Task Executor for extracting words");

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("wordsThread-");
        executor.initialize();
        return executor;

    }


    @Bean(name ="taskExecutorSmartBox")
    public Executor taskExecutorSmartBox(){
        LOGGER.debug("Creating Async Task Executor for extracting words");

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("smartbox-");
        executor.initialize();
        return executor;
    }

    @Value("${pool.io.max}")
    private int maxPoolSize;

    @Value("${pool.io.queue}")
    private int queue;

    @Bean(name = "taskExecutorDocumentIo")
    public Executor taskExecutorDocumentIo(){
        LOGGER.debug("Creating Async Task Executor for extracting words");

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(maxPoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queue);
        executor.setThreadNamePrefix("io-");
        executor.initialize();
        return executor;
    }


}
