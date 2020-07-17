package com.zxod.springbootsimple.service.HelloProxy;

import javax.annotation.Resource;

import com.zxod.springbootsimple.module.Hello;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloProxy {

    @Resource
    Hello hello;

    @Bean(name = "proxyHello")
    public Hello getHelloProxy() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(hello);
        proxyFactory.addAdvice(new HelloBeforeAdvice());
        proxyFactory.addAdvice(new HelloAfterAdvice());
        return (Hello) proxyFactory.getProxy();
    }
}