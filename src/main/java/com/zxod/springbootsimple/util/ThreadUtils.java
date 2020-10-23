package com.zxod.springbootsimple.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ThreadUtils {
    
    /**
     * 多线程执行任务
     * @param todoes 待处理列表
     * @param dealFunc 处理函数
     * @param batchSize 每个线程（每批）多少个todo元素
     * @param threadSize 并行最大线程数
     * @return
     */
    public static <T, K> List<K> batchDeal(List<T> todoes, Function<List<T>, List<K>> dealFunc, Integer batchSize, Integer threadSize) {
        if (CollectionUtils.isEmpty(todoes)) {
            return null;
        }

        // 生成thread pool
        ThreadFactory threadFactory = new ThreadFactoryBuilder().build();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadSize, threadSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory, new AbortPolicy());
        Map<Integer, List<K>> results = new ConcurrentHashMap<Integer, List<K>>();

        // 执行任务
        int idx = 0;
        for (List<T> dealList: Lists.partition(todoes, batchSize)) {
            int innerIdx = idx;
            threadPool.submit(() -> {
                List<K> returns = dealFunc.apply(dealList);
                results.put(innerIdx, returns);
            });
            idx++;
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

        // 获取结果
        List<K> ret = new ArrayList<>();
        for (int i = 0; i < idx; i++) {
            ret.addAll(results.getOrDefault(i, new ArrayList<>()));
        }
        return ret;
    }

    public static void main(String[] args) {
        long startTime = new Date().getTime();
        List<Integer> todoes = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        List<Integer> ret1 = ThreadUtils.batchDeal(todoes, nums -> {
            nums.stream().forEach(x -> System.out.println(x));
            System.out.println("------");
            return nums;
        }, 5, 4);

        long endTime = new Date().getTime();
        System.out.printf("cose time: %s\n", endTime - startTime);
        System.out.printf("ret: %s\n", JSON.toJSONString(ret1));
    }
}
