package com.zxod.springbootsimple.service.HelloProxy;

import javax.annotation.Resource;

import com.zxod.springbootsimple.module.Hello;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloProxy { //implements MethodInterceptor {

    @Resource
    Hello hello;

    @Resource
    HelloBeforeAdvice helloBeforeAdvice;

    @Resource
    HelloAfterAdvice helloAfterAdvice;

    @Bean(name = "proxyHello")
    public Hello getHelloProxy() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(hello);
        proxyFactory.addAdvice(helloBeforeAdvice);
        proxyFactory.addAdvice(helloAfterAdvice);
        return (Hello) proxyFactory.getProxy();
    }

    // @Override
	// public Object invoke(MethodInvocation invocation) throws Throwable {
        

    // }

}