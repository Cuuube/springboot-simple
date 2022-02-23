package com.zxod.springbootsimple.controller;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class GraphDbController {
    @Resource
    private Driver driver;

    @GetMapping("/neo4j")
    public Object testNeo4jDriver() {
        Session session = driver.session();
        // Query q = new Query();
        Result result = session.run("MATCH (ee:Person)-[:KNOWS]-(friends) WHERE ee.name = \"Emil\" RETURN ee, friends");
        // session.close();
        // 返回值里包含ee和friends的结果，这里只用friends的结果。取ee的话，会有两个相同的ee
        return result.list(v -> v.get("friends").asMap());
    }
    
}
