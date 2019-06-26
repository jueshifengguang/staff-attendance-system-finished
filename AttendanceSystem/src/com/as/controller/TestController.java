package com.as.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("index")
	public String toHome(){
		System.out.println("test111");
		return "home";
	}
	
	@RequestMapping("index1")
	public ModelAndView toHome1(){
		ModelAndView mv1 = new ModelAndView();
		System.out.println("mv1？？这是什么？？？？？");
		mv1.setViewName("home1");
		return mv1;
	}
	
	@RequestMapping("/index222")
	public ModelAndView index(){
		ModelAndView mv1 = new ModelAndView("index");
		System.out.println("mv1？？这是什么？？？？？");
		mv1.addObject("welcome", "hello");
		return mv1;
	}
	
	@RequestMapping("/hello")
    public String hello(){        
        return "home";
    }
	
	@RequestMapping("/hello2")
    public String hello2(){        
        return "home";
    }
	
	
	
	
}