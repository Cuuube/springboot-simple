package com.zxod.springbootsimple.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorServiceConfig {
    @Bean
    @Primary
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(0, 2000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }
}
