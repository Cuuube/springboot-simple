package com.zxod.springbootsimple.modules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.zxod.springbootsimple.DemoApplicationTests;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
@ActiveProfiles("unittest")
public class JsonTests {
    @Test
    public void testJson() {
        TestObj1 obj1 = new TestObj1();
        obj1.setId(1234124312321312L);
        obj1.setName("nihao");
        obj1.setViaNames(Arrays.asList("kakakaka"));
        obj1.setSub(
            TestInnerObj1.builder()
                .score(3.3d)
                .name("inner1")
                .build()
        );

        String obj1Str = JSONObject.toJSONString(obj1);
        System.out.print(obj1Str); // "{"id":1234124312321312,"name":"nihao","sub":{"name":"inner1","score":3.3},"viaNames":["kakakaka"]}"
        TestObj1 recover1 = JSONObject.toJavaObject(
            JSONObject.parseObject(obj1Str),
            TestObj1.class
        ); // 完整


        TestObj2 obj2 = new TestObj2();
        obj2.setId(1234124312321312L);
        obj2.setName("nihao");
        obj2.setViaNames(Arrays.asList("kakakaka"));
        obj2.setSub(
            TestInnerObj1.builder()
                .score(3.3d)
                .name("inner1")
                .build()
        );

        String obj2Str = JSONObject.toJSONString(obj2);
        System.out.print(obj2Str); // "{"id":1234124312321312,"name":"nihao","sub":{"name":"inner1","score":3.3},"via_names":["kakakaka"]}"

        TestObj2 recover2 = JSONObject.toJavaObject(
            JSONObject.parseObject(obj2Str),
            TestObj2.class
        ); // 完整


        TestObj3 obj3 = new TestObj3();
        obj3.setFirstName("nihao");
        obj3.setLastName("kakakaka");

        // 驼峰转下划线
        // SerializeConfig config = new SerializeConfig();
        // config.put("propertyNamingStrategy", PropertyNamingStrategy.SnakeCaseStrategy);
        //  propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String obj3Str = JSONObject.toJSONString(obj3);
        System.out.print(obj3Str);

        TestObj3 recover3 = JSONObject.toJavaObject(
            JSONObject.parseObject(obj3Str),
            TestObj3.class
        ); // 完整
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestInnerObj1 {
        private double score;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestObj1 {
        private long id;
        private String name;
        private List<String> viaNames;
        private TestInnerObj1 sub;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestObj2 {
        @JSONField(serialize = false)
        private long id;
        private String name;
        @JSONField(name = "via_names")
        private List<String> viaNames;
        private TestInnerObj1 sub;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestObj3 {
        private String firstName;
        private String lastName;
    }

    @Test
    public void testDateJsonConvert() {
        TestDateObj1 a = new TestDateObj1();
        a.setDd(new Date());

        String jsonStr = JSONObject.toJSONString(a);
        System.out.print(jsonStr);

        TestDateObj2 b = JSON.toJavaObject(
            JSONObject.parseObject(jsonStr), TestDateObj2.class);
        System.out.print(b.getDd());
        System.out.print(JSONObject.toJSONString(b));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestDateObj1 {
        private Date dd;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestDateObj2 {
        private Long dd;
    }

    @Test
    public void testJsonStrMatch() {
        String jsonStr = "{\"privateByUser\": \"true\"}";
        String clip = "\"privateByUser\": \"true\"";
        System.out.println(jsonStr.contains(clip));

        JSONObject obj = JSONObject.parseObject(jsonStr);
        System.out.println(obj.getBooleanValue("privateByUser"));
    }
}