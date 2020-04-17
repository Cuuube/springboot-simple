package com.zxod.springbootsimple.service.HelloProxy;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class HelloBeforeAdvice implements MethodBeforeAdvice  {
    @Override
    public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
        System.out.print("before!");
    }
}