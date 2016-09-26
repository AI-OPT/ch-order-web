package com.ai.ch.order.web.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.ch.order.web.controller.constant.Constants;
import com.alibaba.fastjson.JSONObject;

/**
 * 发票打印工具类
 *
 * Date: 2016年9月26日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhouxh
 */
public class InvoiceUtils {
	private static final Logger log = LoggerFactory.getLogger(InvoiceUtils.class);
	
	public static String QUERY_AUTH = "PubicInterFace/QueryAuth";//获取授权Id接口
	public static String BATCH_ADD = "PubicInterFace/BatchAdd";//开具发票接口
	public static String GET_FILE = "PubData/GetFileByAuthInfo";//下载电子发票接口
	
	public static String TYPE_BATCH_ADD ="BatchAdd";//开具发票
	public static String TYPE_Query ="Query";//查询
	public static String TYPE_GetFile ="GetFileByAuthInfo";//下载电子发票

	

	/**
	 * 设置http代理
	 * 
	 * @return
	 * @author zhouxh
	 */
	private static RequestConfig getProxy() {
		if (Constants.HTTP_PROXY_URL == null || Constants.HTTP_PROXY_URL.isEmpty())
			return null;
		HttpHost proxy = new HttpHost(Constants.HTTP_PROXY_URL, Integer.parseInt(Constants.HTTP_PROXY_PORT), "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		return config;
	}

	/**
	 * 获取授权ID
	 * 
	 * @param httpPost
	 * @param client
	 * @return
	 * @author zhouxh
	 */
	public static String getID(String operateType) {
		// 服务地址
		HttpPost httpPost = new HttpPost(Constants.INVOICE_PRINT_URL + InvoiceUtils.QUERY_AUTH);
		CloseableHttpClient client = HttpClients.createDefault();
		JSONObject authorizationJson = new JSONObject();
		authorizationJson.put("loginName",Constants.INVOICE_PRINT_USERNAME);
		authorizationJson.put("password", Constants.INVOICE_PRINT_PASSWORD);
		authorizationJson.put("interFaceCode", operateType);
		try {
			httpPost.setEntity(new StringEntity(authorizationJson.toString(), "UTF-8"));
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			if(getProxy() !=null)
				httpPost.setConfig(getProxy());
			CloseableHttpResponse response = client.execute(httpPost);
			String authorizationResult = EntityUtils.toString(response.getEntity());
			JSONObject authorizationResultJson = JSONObject.parseObject(authorizationResult);
			return authorizationResultJson.getString("Id");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("请求失败："+e);
		}
		return null;
	}

	/**
	 * post请求wcf服务
	 * 
	 * @param httpPost
	 * @param client
	 * @param entity
	 * @return
	 * @author zhouxh
	 */
	public static String postBatchAdd(String entity) {
		// 服务地址
		HttpPost httpPost = new HttpPost(Constants.INVOICE_PRINT_URL + InvoiceUtils.BATCH_ADD);
		CloseableHttpClient client = HttpClients.createDefault();
		String retVal = "";
		try {
			httpPost.setEntity(new StringEntity(entity, "UTF-8"));
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			if(getProxy() !=null)
				httpPost.setConfig(getProxy());
			CloseableHttpResponse response = client.execute(httpPost);
			retVal = EntityUtils.toString(response.getEntity());
			System.out.println(retVal);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("请求失败："+e);
		}
		return retVal;
	}

}
