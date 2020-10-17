package com.zxod.springbootsimple.mapper.student;

import com.zxod.springbootsimple.entity.ScrawlRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SScrawlRuleMapper {
    @Select("select * from scrawl_rule")
    List<ScrawlRule> getAll();
}