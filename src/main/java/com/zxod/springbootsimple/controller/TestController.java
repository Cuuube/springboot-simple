package com.zxod.springbootsimple.controller;

import com.zxod.springbootsimple.module.CacheModule;
import com.zxod.springbootsimple.module.CacheModule.TestArgs;
import com.zxod.springbootsimple.util.RocketMQProducerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private CacheModule cacheModule;

    // @GetMapping("/")
    // public Object test() {
    //     Map<String, Object> ret = new HashMap<>();
    //     Long num = 111111111111111111L;
    //     ret.put("long", 123456L);
    //     ret.put("Long", num);
    //     ret.put("string", "hahaha");
    //     return ret;
    // }

    @GetMapping("/cache")
    public Object testCache(
        @RequestParam("aaa") String aaa,
        @RequestParam("bbb") Integer bbb
    ) {
        // return cacheModule.testCache();
        // return cacheModule.testCache2(aaa, bbb);
        return cacheModule.testCache3(new TestArgs(aaa, bbb));
    }

    @GetMapping("/clear_cache")
    public Object testChearCache() {
        return cacheModule.clearCache();
    }

    @GetMapping("/rocketmq/post")
    public Object testRocketMQPost(
        @RequestParam("topic") String topic,
        @RequestParam(value = "tags", defaultValue = "*") String tags,
        @RequestParam("msg") String msg,
        @RequestParam(value="times",defaultValue = "1") Integer times
    ) throws Exception {
        for (;times>0;times--) {
            RocketMQProducerUtils.send(topic, tags, msg);
        }
        return "ok";
    }
}
