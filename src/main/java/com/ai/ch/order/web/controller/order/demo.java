package com.ai.ch.order.web.controller.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.ai.ch.order.web.vo.Key;
import com.ai.ch.order.web.vo.KeyType;
import com.changhong.upp.business.entity.upp_103_001_01.GrpBody;
import com.changhong.upp.business.entity.upp_103_001_01.GrpHdr;
import com.changhong.upp.business.entity.upp_103_001_01.RespInfo;
import com.changhong.upp.business.type.TranType;
import com.changhong.upp.crypto.rsa.RSACoder;
import com.changhong.upp.util.XBConvertor;

public class demo {
	@Resource(name="key")
	private Key key;
	@Test
	public void testDemo(){		
		GrpHdr hdr = new GrpHdr();
		String merNo="CO20160900000009";
		hdr.setMerNo(merNo);
		hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		hdr.setTranType(TranType.PAY_NOTICE.getValue());
		// 消息体
		GrpBody body = new GrpBody();
		body.setMerOrderId("4232342");
		body.setOrderAmt("200");
		body.setPayStatus("00");
		body.setRemark("11");
		body.setResv("11");
		body.setOrderDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setPaymentChannel("09");
		body.setPayTranSn("23");
		RespInfo respInfo = new RespInfo();
		respInfo.setGrpHdr(hdr);
		respInfo.setGrpBody(body);
		String data = null;
		// 加签
		String sign = null;
		try {
			data = XBConvertor.toXml(respInfo, "UTF-8");
			sign = RSACoder.sign(key.getKey(KeyType.PRIVATE_KEY),data);
			Map<String, String> param = new TreeMap<String, String>();
			String msgHeader = initMsgHeader(merNo, TranType.PAY_NOTICE.getValue());
			param.put("msgHeader", msgHeader);
			param.put("xmlBody", data);
			param.put("signMsg", sign);
			String result = sendHttpPost("http://127.0.0.1:8080/ch-order-web/notice/payNotice", param,"UTF-8");
			System.out.println("===="+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 拼装报文头
	 * 
	 * @return
	 */
	public  String initMsgHeader(String merNo, String tranType) {
		StringBuffer buffer = new StringBuffer("{H:01");
		buffer.append(merNo);
		buffer.append("1000000000000000");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		buffer.append(dateFormat.format(new Date(System.currentTimeMillis())));
		buffer.append(tranType);
		buffer.append("}");
		return buffer.toString();
	}
	private String sendHttpPost(String url, Map<String, String> param, String charset) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		try {
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			
			if (!CollectionUtils.isEmpty(param)) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					params.add(new BasicNameValuePair(key, param.get(key)));
				}
				HttpEntity fromEntity = new UrlEncodedFormEntity(params, charset);
				post.setEntity(fromEntity);
			}
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), charset);
			} else if (302 == response.getStatusLine().getStatusCode()) {
				Header hs = response.getFirstHeader("Location");
				if (hs != null) {
					String lo = hs.getValue();
					if (!checkUrl(lo)) {
						lo = findUrl(post.getURI().toASCIIString()) + lo;
					}
					return sendHttpPostGet(lo, null, charset);
				}
				String result = EntityUtils.toString(response.getEntity(), charset);
				return result;
			} else {
				throw new Exception("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
		}
	}
	
	private static boolean checkUrl(String url) {
		Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
		Matcher m = p.matcher(url);
		return m.find();
	}
	
	protected static String findUrl(String url) {
		Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
		Matcher m = p.matcher(url);
		if (m.find()) {
			return m.group();
		}
		return null;
	}
	
	private static String sendHttpPostGet(String url, Map<String, String> data, String charset) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		try {
			url = url.replaceFirst("^http://|^http://", "");
			URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(url);
			if (!CollectionUtils.isEmpty(data)) {
				for (String key : data.keySet()) {
					uriBuilder.setParameter(key, data.get(key));
				}
			}
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), charset);
			} else if (302 == response.getStatusLine().getStatusCode()) {
				Header hs = response.getFirstHeader("Location");
				if (hs != null) {
					String lo = hs.getValue();
					if (!checkUrl(lo)) {
						lo = findUrl(httpGet.getURI().toASCIIString()) + lo;
					}
					return sendHttpPostGet(lo, null, charset);
				}
				String result = EntityUtils.toString(response.getEntity(), charset);
				return result;
			} else {
				throw new Exception("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
		}
	}
}
