package com.zxod.springbootsimple.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {


    private final ExecutorService executorService;

    @Override
    public Executor getAsyncExecutor() {
        return executorService;
    }
}
