package com.zxod.springbootsimple.controller;

import com.zxod.springbootsimple.entity.ScrawlRule;
import com.zxod.springbootsimple.mapper.student.SScrawlRuleMapper;
import com.zxod.springbootsimple.mapper.test.ScrawlRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScrawlController {

    @Autowired
    private ScrawlRuleMapper scrawlRuleMapper;

    @Autowired
    private SScrawlRuleMapper sscrawlRuleMapper;



    @GetMapping("/scrawl_rules")
    public List<List<ScrawlRule>> getScrawlRules() {
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
