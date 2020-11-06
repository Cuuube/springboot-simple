package com.zxod.springbootsimple.module;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheModule {

    // 简单无参数
    @Cacheable(value="cache1", sync=true) // 有则返回，没则设置
    public Object testCache() {
        System.out.println("我被触发了！");
        return ImmutableMap.of("key", "testCache!");
    }

    // 一般类型参数，注意，Integer不可以为空
    @Cacheable(value="cache2", sync=true, key="#root.methodName+':'+#aaa+':'+#bbb") // 有则返回，没则设置
    public Object testCache2(String aaa, Integer bbb) {
        System.out.println("我被触发了2！");
        return ImmutableMap.of("key", "testCache!", "aaa", aaa, "bbb", bbb);
    }

    // 引用类型参数
    @Cacheable(value="cache3", sync=true, key="#root.methodName+':'+#arg.aaa+':'+#arg.bbb") // 有则返回，没则设置
    public Object testCache3(TestArgs arg) {
        System.out.println("我被触发了3！");
        return ImmutableMap.of("key", "testCache!", "aaa", arg.getAaa(), "bbb", arg.getBbb());
    }

    @CacheEvict({"cache1", "cache2", "cache3"}) // 清除
    public Object clearCache() {
        System.out.println("clearCache!");
        return "clearCache!";
    }

    @CachePut("cache1")  // 都重新设置
    public Object putCache() {
        System.out.println("putCache!");
        return ImmutableMap.of("key", "putCache!");
    }


    @Data
    public static class TestArgs {
        private String aaa;
        private Integer bbb;

        public TestArgs(String a, Integer b) {
            this.setAaa(a);
            this.setBbb(b);
        }
    }
}
