package com.zxod.springbootsimple.mapper.test;

import java.util.List;

import com.zxod.springbootsimple.entity.ScrawlRule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ScrawlRuleMapper {
    @Select("select * from scrawl_rule")
    List<ScrawlRule> getAll();
}