package com.ai.ch.order.web.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class WcfUtils {
	/**
	 * 获取授权ID
	 * @param httpPost
	 * @param client
	 * @return
	 * @author zhouxh
	 */
	public static String getID(HttpPost httpPost,CloseableHttpClient client){
		JSONObject authorizationJson = new JSONObject();
		authorizationJson.put("Name", "123456");
		authorizationJson.put("Password", "123456");
		authorizationJson.put("InterFaceCode", "BatchAdd");
		String id="";
		try {
			httpPost.setEntity(new StringEntity(authorizationJson.toString()));
			httpPost.setHeader(HTTP.CONTENT_TYPE, "text/json");
			CloseableHttpResponse response = client.execute(httpPost);
			EntityUtils.toString(response.getEntity());
			String authorizationResult="{'AuthId':'df126b3c-0ecb-4278-9b0d-5859174cba8d','ActiveTime':0}";
			JSONObject authorizationResultJson = JSONObject.parseObject(authorizationResult);
			id = authorizationResultJson.getString("AuthId");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	}
	/**
	 * post请求wcf服务
	 * @param httpPost
	 * @param client
	 * @param entity
	 * @return
	 * @author zhouxh
	 */
	public static String postWcf(HttpPost httpPost,CloseableHttpClient client,String entity){
		String retVal="";
		try {
			httpPost.setEntity(new StringEntity(entity)); 
			httpPost.setHeader(HTTP.CONTENT_TYPE, "text/json"); 
			CloseableHttpResponse response = client.execute(httpPost); 
			retVal = EntityUtils.toString(response.getEntity()); 
			System.out.println(retVal);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return retVal;
	}

}
