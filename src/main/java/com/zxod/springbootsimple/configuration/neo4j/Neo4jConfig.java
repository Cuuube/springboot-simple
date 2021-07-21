package com.zxod.springbootsimple.configuration.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig {

    private final Driver driver; 

	public Neo4jConfig(Driver driver) { 
		this.driver = driver;
	}

    // @Bean("neo4jDriver")
    // public Driver neo4jDriver() {
    //     return driver;
    // }
}
