package com.ai.ch.order.web.controller.common;

import java.util.HashMap;
import java.util.Map;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ChUserController {
	public static void main(String sd[]) throws Exception{
		   Map<String,String> params=new HashMap<String,String>();
		   params.put("userName", "ac_JYRVs");
	       String url="http://10.19.13.16:28151/opaas/http/srv_up_user_getuserdetialbyname_qry";
	       String param=JSON.toJSONString(params);
	       Map<String,String> mapHeader = new HashMap<String,String>();
	       mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
	      String result = HttpClientUtil.sendPost(url, param, mapHeader);
	     //将返回结果，转换为JSON对象 
	     JSONObject json=JSON.parseObject(result);
	     String reqResultCode=json.getString("resultCode");
	     if("000000".equals(reqResultCode)){
	         String dataStr=(String)json.get("data");
	         JSONObject dataJson=JSON.parseObject(dataStr);
	         JSONObject responseHeader=dataJson.getJSONObject("responseHeader");
	         //JSONObject ordOrderVo=dataJson.getJSONObject("ordOrderVo");
	         System.out.println("isSuccess="+responseHeader.getBoolean("success"));
	         //System.out.println("orderId="+ordOrderVo.getString("orderId"));
	         
	         
	         System.out.println("请求参数报文体[param]="+JSON.toJSONString(params));
	         System.out.println("请求结果报文头[result]="+result);
	         System.out.println("请求结果真实数据报文体[dataJson]="+JSON.toJSONString(dataJson));
	     }
	     else{
	     	//请求过程失败
	     	System.out.println("请求失败,请求错误码:"+reqResultCode);
	     }
		}
	}
