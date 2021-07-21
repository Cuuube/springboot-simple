package com.zxod.springbootsimple.module;

import com.zxod.springbootsimple.innotation.Inno;
import org.apache.ibatis.type.TypeException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void sayHello() {
        System.out.print("hello");
    }

    @Inno(name = "schedule")
    @Scheduled(cron="*/5 * * * * ?")
    public void testHello(){
        // sayHi("Aliss");
        System.out.println("schedule!!");
    }

    @Inno(name = "hahahah")
    public void sayHo() {
        System.out.print("Hi");
    }

    @Inno(name = "hohoho")
    public void sayHi(String name) {
        System.out.println(String.format("%s, HO!", name));
    }

    @Inno(name = "lololo")
    public void sayError() {
        System.out.println("lllll");
        throw new TypeException("出错啦出错啦！！");
    }

    public void simpleSayHello() {
        System.out.println("hello without anything");
    }
}