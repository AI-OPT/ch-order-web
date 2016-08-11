package com.ai.ch.order.web.controller.demo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	@RequestMapping(value="/demo/test",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String test(HttpServletRequest request){
		String flag = "true";
		
		return flag;
	}

	
}
