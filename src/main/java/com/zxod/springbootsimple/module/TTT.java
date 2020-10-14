package com.zxod.springbootsimple.module;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class TTT {
    @Data
    public static class A {
        private Integer id;
        public Integer say() {
            return this.id;
        }
        public Integer sayByMethod() {
            return this.getId();
        }
        public void sayThis() {
            System.out.println(JSON.toJSONString(this));
        }
    }

    @Data
    public static class B extends A {
        private Integer id;
        private Integer name;
        // public Integer say() {
        //     return this.id;
        // }
        // public Integer sayByMethod() {
        //     return this.getId();
        // }
    }

    @Data
    public static class C extends A {
        private Integer id;
        public Integer say() {
            return this.id;
        }
        public Integer sayByMethod() {
            return this.getId();
        }
    }

    public static void main(String[] args) {
        // A a = new A();
        // a.setId(1);
        B b = new B();
        b.setId(2);
        b.setName(2222);
        C c = new C();
        c.setId(3);
        System.out.println(b.say());
        System.out.println(b.sayByMethod());
        b.sayThis();

        System.out.println(c.say());
        System.out.println(c.sayByMethod());
        c.sayThis();
    }
    
}