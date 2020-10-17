package com.zxod.springbootsimple.util;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ThreadUtils {
    public static List<? extends Object> batchDeal(List<? extends Object> todoes, Function<List<? extends Object>, List<? extends Object>> dealFunc, Integer batchSize, Integer threadSize) {
        if (CollectionUtils.isEmpty(todoes)) {
            return null;
        }

        // 生成thread pool
        ThreadFactory threadFactory = new ThreadFactoryBuilder().build();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadSize, threadSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory, new AbortPolicy());

        // 执行任务
        for (List<? extends Object> dealList: Lists.partition(todoes, batchSize)) {
            threadPool.submit(() -> {
                dealFunc.apply(dealList);
            });
        }

        threadPool.shutdown();

        // 等待全部线程执行完毕
        try {
            boolean loop;
            do {
                loop = !threadPool.awaitTermination(1, TimeUnit.MINUTES);
            } while (loop);
        } catch (InterruptedException e) {
            System.out.printf("wait thread pool error, %s \n", e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        long startTime = new Date().getTime();
        List<Integer> todoes = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        ThreadUtils.batchDeal(todoes, nums -> {
            nums.stream().forEach(x -> System.out.println(x));
            System.out.println("------");
            return Arrays.asList();
        }, 5, 4);

        long endTime = new Date().getTime();
        System.out.printf("cose time: %s\n", endTime - startTime);
    }
}
