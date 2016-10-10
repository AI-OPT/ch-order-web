package com.ai.ch.order.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public  class ChUserByNameUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ChUserByNameUtil.class);
	//获取用户id
	public static String getUserInfo(String userName){
		 Map<String,String> params=new HashMap<String,String>();
		   params.put("userName", userName);
	       String url="http://10.19.13.16:28151/opaas/http/srv_up_user_getuserdetialbyname_qry";
	       String param=JSON.toJSONString(params);
	       Map<String,String> mapHeader = new HashMap<String,String>();
	       mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
	       String result ="";
			try {
				result = HttpClientUtil.sendPost(url, param, mapHeader);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     //将返回结果，转换为JSON对象 
		 JSONObject dataJson=null;
	     JSONObject json=JSON.parseObject(result);
	     String reqResultCode=json.getString("resultCode");
	     if("000000".equals(reqResultCode)){
	         String dataStr=(String)json.get("data");
	         dataJson=JSON.parseObject(dataStr);
	         Object uid =dataJson.get("uid");
	         if(uid!=null) {
	        	 return uid.toString();
	         }
	     }else{
	     	//请求过程失败
	    	 LOG.info("请求失败,请求错误码:"+reqResultCode);
	     }
	     return null;
	}
}

