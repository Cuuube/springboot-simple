package com.zxod.springbootsimple.modules;

import javax.annotation.Resource;

import com.zxod.springbootsimple.DemoApplicationTests;
import com.zxod.springbootsimple.module.Hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
@ActiveProfiles("unittest")
public class HelloTests {

    @Resource
    Hello hello;

    @Resource
    Hello proxyHello;

    @Test
    public void hello() {
        // System.out.print("hello");
        proxyHello.sayHello();
        proxyHello.sayHi();
    }

    @Test
    public void getConfig() {
        System.out.print(System.getProperty(""));
    }
}