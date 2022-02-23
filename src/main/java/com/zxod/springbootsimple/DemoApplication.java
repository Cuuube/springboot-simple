package com.zxod.springbootsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// 测试代码
		// AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		// AnnotationConfigWebApplicationContext a  = new AnnotationConfigWebApplicationContext();
		// Math.round(22.5);
		// a.getBean(args)
		// System.gc();
	}
}
