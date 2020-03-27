package com.zxod.springbootsimple.controller;

import java.util.Arrays;

import com.zxod.springbootsimple.mapper.student.SScrawlRuleMapper;
import com.zxod.springbootsimple.mapper.test.ScrawlRuleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private ScrawlRuleMapper scrawlRuleMapper;

    @Autowired
    private SScrawlRuleMapper sscrawlRuleMapper;

    @GetMapping("/ping")
    public String ping() {
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
}
