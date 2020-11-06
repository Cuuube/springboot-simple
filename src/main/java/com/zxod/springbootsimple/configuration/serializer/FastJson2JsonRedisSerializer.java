package com.zxod.springbootsimple.configuration.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import javax.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    private ObjectMapper objectMapper;
    private Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz, ObjectMapper om) {
        super();
        this.clazz = clazz;
        this.objectMapper = om;
    }

    @Override
    public byte[] serialize(@Nullable T t) throws SerializationException {
        if (Objects.isNull(t)) {
            return new byte[0];
        }
        return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
    }

    @Override
    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes) || bytes.length <= 0) {
            return null;
        }
        try {
            String str = new String(bytes, "UTF-8");
            return JSON.parseObject(str, clazz);
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    // protected JavaType getJavaType(Class<?> clazz) {
    //     return TypeFactory.defaultInstance().constructType(clazz);
    // }
}
