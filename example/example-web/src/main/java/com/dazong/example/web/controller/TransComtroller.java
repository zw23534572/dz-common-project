package com.dazong.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dazong.example.service.trans.ITransService;


@RestController
@RequestMapping("/trans")
public class TransComtroller {

	@Autowired
	private ITransService transService;

	@RequestMapping("/test")
	public String test(){
		this.transService.test("test", 1);
		return "success";
	}

	@RequestMapping("/testForException")
	public String testForException(){
		this.transService.testForException("testForException", 2);
		return "success";
	}
}
