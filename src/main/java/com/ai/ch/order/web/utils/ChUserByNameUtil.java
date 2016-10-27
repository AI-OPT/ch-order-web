package com.ai.ch.order.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public  class ChUserByNameUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ChUserByNameUtil.class);
	//获取用户id
	public static String getUserInfo(String userName){
		 Map<String,String> params=new HashMap<String,String>();
		   params.put("userName", userName);
	       String url=Constants.CH_USERNAME_URL;
	       String param=JSON.toJSONString(params);
	       Map<String,String> mapHeader = new HashMap<String,String>();
	       mapHeader.put("appkey", Constants.CH_USERNAME_APPKEY);
	       String result ="";
	       Object openId=null;
			try {
				result = HttpClientUtil.sendPost(url, param, mapHeader);
				JSONObject object = ParseO2pDataUtil.getData(result);
				Object obj = object.get("resultCode");
				if(obj!=null) {
					return null;
				}
				openId = object.get("openId");
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("请求出现错误!");
			}
			return openId==null?null:openId.toString();
	}
	
	public static void main(String[] args) {
		getUserInfo("liangkf11");
	}
}

