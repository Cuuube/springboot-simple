package com.zxod.springbootsimple.mapper.test;

import java.util.List;

import com.zxod.springbootsimple.entity.ScrawlRule;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ScrawlRuleMapper {
    @Select("select * from scrawl_rule")
    List<ScrawlRule> getAll();

    @Insert("insert into scrawl_rule (name, description, home_url, page_url, rule, creator) values (#{name,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, #{homeUrl,jdbcType=VARCHAR}, #{pageUrl,jdbcType=VARCHAR}, #{rule,jdbcType=VARCHAR}, #{creator,jdbcType=VARCHAR});")
    void insert(String name, String description, String homeUrl, String pageUrl, String rule, String creator);

    @Update("update scrawl_rule set rule = #{rule,jdbcType=VARCHAR} where id = #{id, jdbcType=INTEGER}")
    void updateRuleById(Integer id, String rule);
}