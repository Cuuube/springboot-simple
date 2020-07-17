package com.zxod.springbootsimple.service.HelloProxy;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloAspect {
    @Pointcut("execution(public * com.zxod.springbootsimple.module.Hello.sayHello(..))") // 固定方法
    // @Pointcut("execution(public * com.zxod.springbootsimple.module.Hello.*(..))") // 所有方法
    public void do1() {}

    // 进入方法前
    @Before("do1()")
    public void doBefore() {
        // System.out.println(joinPoint.getArgs());
        System.out.println("dododododo before!");
    }

    // 方法体执行完毕
    @After("do1()")
    public void doAfter() {
        System.out.println("dododododo after!");
    }

    // 返回值调用后
    @AfterReturning(pointcut = "do1()", returning = "ret")
    public void doAfterReturning(Object ret) {
        System.out.println(String.format("return data: ", ret));
        System.out.println("dododododo after returning!");
    }    

    // 注解切点
    @Pointcut("@annotation(com.zxod.springbootsimple.innotation.Inno)")
    public void annoAspect() {}

    // 一般注解的before使用
    // @Before("annoAspect()")
    // public void ttt() {
    //     // System.out.println(in.name());
    //     System.out.println("dododododo before by Annotation!");
    // }

    // 环绕，相当于before + after
    @Around("annoAspect()")
    public void ttt1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(JSON.toJSONString(joinPoint.getArgs()));
        System.out.println("before by Annotation!");
        joinPoint.proceed();
        System.out.println("after by Annotation!");
    }

    @AfterThrowing(pointcut = "annoAspect()", throwing = "e")
    public void afterThrowing(Exception e) {
        System.out.println(e.getMessage());
    }


}