package com.zxod.springbootsimple.controller;

import com.zxod.springbootsimple.mapper.student.SScrawlRuleMapper;
import com.zxod.springbootsimple.mapper.test.ScrawlRuleMapper;
import com.zxod.springbootsimple.module.Hello;
import com.zxod.springbootsimple.module.LazyModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private ScrawlRuleMapper scrawlRuleMapper;

    @Autowired
    private SScrawlRuleMapper sscrawlRuleMapper;

    @Autowired
    private Hello proxyHello;

    @Autowired
    private Hello hello;

    @Autowired
    private LazyModule lazyModule;

    @GetMapping("/ping")
    public String ping() {
        hello.sayHello();
        hello.sayHi();
        hello.sayHo("Tom");
        hello.sayError();
        lazyModule.sayHello();
        return "pong";
    }

    @GetMapping("/test")
    public Object test() {
        Map<String, Object> ret = new HashMap<>();
        Long num = 111111111111111111L;
        ret.put("long", 123456L);
        ret.put("Long", num);
        ret.put("string", "hahaha");
        return ret;
    }

    @GetMapping("/scrawl_rules")
    public Object getScrawlRules() {
        // List<ScrawlRule> dataList = scrawlRuleMapper.getAll();
        return Arrays.asList(
            scrawlRuleMapper.getAll(),
            // sscrawlRuleMapper.getAll(),
            sscrawlRuleMapper.selectAll()
        );
    }

    @PostMapping("/scrawl_rules")
    public Object createScrawlRules(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("homeUrl") String homeUrl,
        @RequestParam("pageUrl") String pageUrl,
        @RequestParam("rule") String rule,
        @RequestParam("creator") String creator
    ) {
        scrawlRuleMapper.insert(name, description, homeUrl, pageUrl, rule, creator);
        return null;
    }

    @PatchMapping("/scrawl_rules")
    public Object updateScrawlRules(
        @RequestParam("id") Integer id,
        @RequestParam("rule") String rule
    ) {
        scrawlRuleMapper.updateRuleById(id, rule);
        return null;
    }

    @DeleteMapping("/scrawl_rules")
    public Object deleteScrawlRules(
        @RequestParam("id") Integer id
    ) {
        scrawlRuleMapper.deleteById(id);
        return null;
    }
}
