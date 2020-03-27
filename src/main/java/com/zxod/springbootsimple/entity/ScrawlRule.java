package com.zxod.springbootsimple.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ScrawlRule {
    private Integer id;
    private String name;
    private String description;
    private String homeUrl;
    private String pageUrl;
    private String rule;
    private Date createTime;
    private Date updateTime;
    private String creator;
}
