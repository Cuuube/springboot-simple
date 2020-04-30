package com.zxod.springbootsimple.controller;

import java.util.Arrays;

import com.zxod.springbootsimple.mapper.student.SScrawlRuleMapper;
import com.zxod.springbootsimple.mapper.test.ScrawlRuleMapper;
import com.zxod.springbootsimple.module.Hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private ScrawlRuleMapper scrawlRuleMapper;

    @Autowired
    private SScrawlRuleMapper sscrawlRuleMapper;

    @Autowired
    private Hello proxyHello;

    @GetMapping("/ping")
    public String ping() {
        proxyHello.sayHello();
        return "pong";
    }

    @GetMapping("/scrawl_rules")
    public Object getScrawlRules() {
        // List<ScrawlRule> dataList = scrawlRuleMapper.getAll();
        return Arrays.asList(
            scrawlRuleMapper.getAll(),
            sscrawlRuleMapper.getAll()
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
}
