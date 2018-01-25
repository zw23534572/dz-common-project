package com.dazong.common.trans.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dazong.common.trans.annotation.EnableAutoRetry;

@SpringBootApplication
@EnableAutoRetry
public class TransApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(TransApplicationStart.class, args);
	}
}
