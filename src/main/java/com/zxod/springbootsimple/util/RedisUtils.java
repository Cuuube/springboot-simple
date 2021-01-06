package com.zxod.springbootsimple.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class RedisUtils {
    private static final String SUCCESS_CODE = "OK";
    private static final int RAND_MIN_SECONDS = 0;
    private static final int RAND_MAX_SECONDS = 30;
    private static final int DEFAULT_LOCK_EXPIRED = 60;
    private static final String EMPTY_SYMBOL = "null";

    public static boolean tryLock(Jedis jedis, String key, String lockValue, int expireInSeconds) {
        return SUCCESS_CODE.equals(jedis.set(key, lockValue, "NX", "EX", expireInSeconds));
    }

    public static boolean tryReleaseLock(Jedis jedis, String key, String lockValue) {
        boolean isSuccess = false;
        String getValue = jedis.get(key);
        if (lockValue.equals(getValue)) {
            long reply = jedis.del(key);
            isSuccess = reply == 1;
            if (!isSuccess) {
                log.error("try release lock failed: request reply {}, expected 1", reply);
            }
        } else {
            log.error("try release lock failed: lockValue not equals: key={} lockValue={} getValue={}", key, lockValue, getValue);
        }
        return isSuccess;
    }

    public static <T, R> List<R> batchExecute(Jedis jedis, Iterable<T> todoList, BiFunction<Pipeline, T, Response<R>> dealFunc) {
        Pipeline pipeline = jedis.pipelined();
        List<Response<R>> resList = new ArrayList<>();
        for (T todoItem: todoList) {
            resList.add(dealFunc.apply(pipeline, todoItem));
        }
        pipeline.sync();

        return resList.stream().map(res -> res != null ? res.get() : null).collect(Collectors.toList());
    }

    /**
     * 从缓存里取数据，没有的话执行函数，获取数据然后存到redis里，再返回数据
     * @param <T>
     * @param jedis
     * @param key
     * @param cls
     * @param expiredTimeSeconds
     * @param saveFunction
     * @return
     */
    public static <T> T getDataOrSaveItFromCache(Jedis jedis, String key, TypeReference<T> type, Integer expiredTimeSeconds, Supplier<T> saveFunction, @Nullable Boolean adminMode) {
        if (!Objects.isNull(adminMode) && adminMode) {
            return saveFunction.get();
        }

        String datastr = jedis.get(key);
        T ret = null;
        if (Strings.isNullOrEmpty(datastr)) {
            // 防击穿
            String lockKey = "lock:" + key;
            String lockValue = UUID.randomUUID().toString();
            if (tryLock(jedis, lockKey, lockValue, DEFAULT_LOCK_EXPIRED)) {
                try {
                    ret = saveFunction.get();
                } catch (Exception e) {
                    log.error("getDataOrSaveItFromCache尝试获取数据失败", e);
                }

                tryReleaseLock(jedis, lockKey, lockValue);
            } else {
                return null;
            }

            // 方法返回null也会缓存一个“null”值，防止缓存穿透
            if (ret == null) {
                datastr = EMPTY_SYMBOL;
            } else {
                datastr = JSON.toJSONString(ret);
            }

            // 缓存&防雪崩
            int expiredTime = 0;
            if (expiredTimeSeconds != null) {
                int randomNum = ThreadLocalRandom.current().nextInt(RAND_MIN_SECONDS, RAND_MAX_SECONDS + 1);
                expiredTime = expiredTimeSeconds + randomNum;
            }
            jedis.setex(key, expiredTime, datastr);
        } else {
            // 如果缓存是个"null"，当成null返回
            if (EMPTY_SYMBOL.equals(datastr)) {
                return null;
            }
            try {
                ret = JSON.parseObject(datastr, type);
            } catch (Exception e) {
                log.warn(String.format("从redis获取数据时反序列化失败：%s, %s", key, datastr), e);
            }
        }
        return ret;
    }
}
