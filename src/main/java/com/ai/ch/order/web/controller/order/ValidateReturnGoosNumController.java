package com.ai.ch.order.web.controller.order;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value="/validateReturnGoosNum")
@RestController
public class ValidateReturnGoosNumController {
	private static final Logger log = LoggerFactory.getLogger(ValidateReturnGoosNumController.class);
	public boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("^[1-9]+\\d*$"); 
	   Matcher isNum = pattern.matcher(str);
//	   if( !isNum.matches() ){
//	       return false; 
//	   } 
	   if (!isNum.find()){
		   return false;
	   }
	   
	   return true; 
	}
	@RequestMapping(value="/validateNum",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String validateNum(String str,String buyNum){
		log.info("str:"+str);
		log.info("buyNum:"+buyNum);
		boolean flag = this.isNumeric(str);
		log.info("flag:"+flag);
		String msg = "success";
		if(flag == false){
			msg = "请输入退换货数量";
			return msg;
		}
		if(Integer.parseInt(str) > Integer.parseInt(buyNum)){
			msg = "退货数量不能大于当前数量";
			return msg;
		}
		return msg;
		
	}
}
