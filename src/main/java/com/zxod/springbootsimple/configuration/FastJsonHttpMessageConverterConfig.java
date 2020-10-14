package com.zxod.springbootsimple.configuration;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zxod.springbootsimple.configuration.serializer.ToStringSerializer;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Collections;

@Configuration
public class FastJsonHttpMessageConverterConfig {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
        serializeConfig.put(Long.TYPE, ToStringSerializer.INSTANCE);
        serializeConfig.put(Long.class, ToStringSerializer.INSTANCE);

        // 初始化一个转换器配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNonStringKeyAsString, // 忽视null的key
                SerializerFeature.DisableCircularReferenceDetect); // 忽视循环引用类

        // 初始化转换器
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        fastConvert.setFastJsonConfig(fastJsonConfig);
        fastConvert.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        // 将配置设置给转换器并添加到HttpMessageConverter转换器列表中
        return new HttpMessageConverters(fastConvert);
    }
}
