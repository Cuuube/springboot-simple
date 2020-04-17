package com.zxod.springbootsimple.module;

import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void sayHello() {
        System.out.print("hello");
    }
}