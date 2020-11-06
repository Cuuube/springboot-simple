package com.zxod.springbootsimple.module;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncModule {
    @Async
    public void voidAsyncMethod() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("voidAsyncMethod done!！");
    }

    @Async
    public Future<String> asyncMethodWithReturn() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("asyncMethodWithReturn done!！");
        return new AsyncResult<String>("Ahaha async got me?");
    }
}
