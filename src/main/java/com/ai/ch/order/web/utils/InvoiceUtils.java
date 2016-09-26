package com.ai.ch.order.web.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.ai.ch.order.web.controller.constant.Constants;
import com.alibaba.fastjson.JSONObject;

public class InvoiceUtils {
	public static String QUERY_AUTH = "PubicInterFace/QueryAuth";// 三、获取授权Id接口
	public static String BATCH_ADD = "PubicInterFace/BatchAdd";// 四、开具发票接口
	public static String GET_FILE = "PubData/GetFileByAuthInfo";// 下载电子发票接口

	/**
	 * 获取授权ID
	 * 
	 * @param httpPost
	 * @param client
	 * @return
	 * @author zhouxh
	 */
	public static String getID() {
		// 服务地址
		HttpPost httpPost = new HttpPost(Constants.INVOICE_PRINT_URL + InvoiceUtils.QUERY_AUTH);
		CloseableHttpClient client = HttpClients.createDefault();
		JSONObject authorizationJson = new JSONObject();
		authorizationJson.put("loginName", "123456");
		authorizationJson.put("password", "123456");
		authorizationJson.put("interFaceCode", "BatchAdd");
		String id = "";
		try {
			httpPost.setEntity(new StringEntity(authorizationJson.toString(), "UTF-8"));
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			CloseableHttpResponse response = client.execute(httpPost);
			EntityUtils.toString(response.getEntity());
			String authorizationResult = "{'AuthId':'df126b3c-0ecb-4278-9b0d-5859174cba8d','ActiveTime':0}";
			JSONObject authorizationResultJson = JSONObject.parseObject(authorizationResult);
			id = authorizationResultJson.getString("AuthId");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
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
