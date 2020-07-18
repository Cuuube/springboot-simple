package com.zxod.springbootsimple.module;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class LazyModule {

    public LazyModule() {
        System.out.println("LazyModule loaded!");
    }

    @Resource
    Hello hello;

    public void sayHello() {
        hello.simpleSayHello();
    }
}