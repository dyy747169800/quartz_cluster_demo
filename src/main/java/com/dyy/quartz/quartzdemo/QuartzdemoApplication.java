package com.dyy.quartz.quartzdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dyy.quartz.quartzdemo.dao")
public class QuartzdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzdemoApplication.class, args);
	}
}
