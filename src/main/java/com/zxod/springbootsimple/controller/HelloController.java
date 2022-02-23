package com.zxod.springbootsimple.controller;

import com.zxod.springbootsimple.module.AsyncModule;
import com.zxod.springbootsimple.module.Hello;
import com.zxod.springbootsimple.module.LazyModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private Hello proxyHello;

    @Autowired
    private Hello hello;

    @Resource
    private ExecutorService executorService;


    @Autowired
    private AsyncModule asyncModule;


    @Autowired
    private LazyModule lazyModule;

    @GetMapping("/hello")
    public String hello() {
        hello.sayHello();
        hello.sayHo();
        hello.sayHi("Tom");
        // hello.sayError();
        lazyModule.sayHello();
        asyncModule.voidAsyncMethod();
        String result = "";
        try {
            asyncModule.asyncMethodWithReturn(); // 不get只会异步执行，不阻塞；加了get会等待get结束
            result = asyncModule.asyncMethodWithReturn().get();

            // 单独触发
            Future fu1 = executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                System.out.println("手动触发async！ done!！");
            });
            Future fu2 = executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                System.out.println("手动触发async！ done!！");
            });
            Future fu3 = executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                System.out.println("手动触发async！ done!！");
            });
            fu1.get();
            fu2.get();
            fu3.get();
        } catch (Exception e) {
        }
        return result;
    }
}
