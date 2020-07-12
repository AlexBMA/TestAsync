package com.test.app.configuration;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TaskExecutorSingleton {

    private static ThreadPoolTaskExecutor executor;

    public static ThreadPoolTaskExecutor getExecutor() {

        if (executor == null) {
            executor = new ThreadPoolTaskExecutor();
            executor.setQueueCapacity(10000);
            executor.setCorePoolSize(3);
            executor.setMaxPoolSize(3);
            executor.setThreadNamePrefix("Upload-");
            executor.setThreadGroupName("Tg");
            executor.initialize();

        }
        return executor;
    }

    public static void setExecutor(ThreadPoolTaskExecutor executor) {
        TaskExecutorSingleton.executor = executor;
    }
}
