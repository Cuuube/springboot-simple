package com.zxod.springbootsimple.modules;

import com.zxod.springbootsimple.DemoApplicationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
@ActiveProfiles("unittest")
public class HelloTests {
    @Test
    public void hello() {
        System.out.print("hello");
    }

    @Test
    public void getConfig() {
        System.out.print(System.getProperty(""));
    }
}