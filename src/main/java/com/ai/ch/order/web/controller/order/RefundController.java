package com.ai.ch.order.web.controller.order;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.upp.docking.covn.MsgString;
import com.upp.docking.enums.TranType;
import com.ylink.itfin.certificate.SecurityUtil;
import com.ylink.upp.base.oxm.XmlBodyEntity;
import com.ylink.upp.base.oxm.util.Dom4jHelper;
import com.ylink.upp.base.oxm.util.HandlerMsgUtil;
import com.ylink.upp.base.oxm.util.HeaderBean;
import com.ylink.upp.base.oxm.util.MsgUtils;
import com.ylink.upp.base.oxm.util.OxmHandler;
import com.ylink.upp.oxm.entity.upp_801_001_01.GrpBody;
import com.ylink.upp.oxm.entity.upp_801_001_01.GrpHdr;
import com.ylink.upp.oxm.entity.upp_801_001_01.RespInfo;

@Controller
public class RefundController {
	

	@RequestMapping("/refund")
	@ResponseBody
	public String reFund(String banlanceIfId, String money, String parentOrderId, String orderId) {
		OxmHandler oxmHandler = DubboConsumerFactory.getService(OxmHandler.class);
		GrpHdr hdr = new GrpHdr();
		String merNo = "CO20160900000009";
		hdr.setMerNo(merNo);
		hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		hdr.setTranType(TranType.REFUND.getValue());
		// 消息体
		GrpBody body = new GrpBody();
		// 退款金额
		body.setRefundAmt(AmountUtil.YToFen(money));
		// 子商户号
		body.setSonMerNo("CO20160900000010");
		// 明细订单号
		body.setMerSeqId(parentOrderId);
		// 交易流水号
		body.setPayTranSn(banlanceIfId);
		// 退款日期
		body.setRefundDate(new Date().getTime());
		// 保留与
		body.setResv(parentOrderId);
		// 商户退款单号
		body.setMerRefundSn(orderId);
		// 后台通知地址
		body.setNotifyUrl("http://song.ngrok.tech:7777/upp-demo/pay/result");
		// 前台通知地址
		body.setReturnUrl("http://www.baidu.com");
		RespInfo respInfo = new RespInfo();
		respInfo.setGrpHdr(hdr);
		respInfo.setGrpBody(body);
		// 发送消息
		String xmlMsg = null;
		try {
			xmlMsg = oxmHandler.marshal(respInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> param = new TreeMap<String, String>();
		// 加签
		String sign = null;
		try {
			sign = sign(xmlMsg);
			param.put("signMsg", sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拼装报文头
		String msgHeader = initMsgHeader(merNo, TranType.REFUND.getValue());
		param.put("msgHeader", msgHeader);
		param.put("xmlBody", xmlMsg);
		String result = null;
		try {
			result = sendHttpPost("http://111.9.116.138:7001/upp-route/entry.html", param, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MsgString msgString = MsgUtils.patch(result);
		String rh = msgString.getHeaderMsg();
		String rb = msgString.getXmlBody();
		String rs = msgString.getDigitalSign();

		com.ylink.upp.oxm.entity.upp_801_001_01.RespInfo receive = (com.ylink.upp.oxm.entity.upp_801_001_01.RespInfo) receiveMsg(
				rh, rb, rs);

		return receive.toString();
	}

	@RequestMapping("/result")
	public String result(String xmlBody, String signMsg, String msgHeader) {
		try {
			boolean flag = verify(xmlBody, signMsg);
			if (!flag) {

			}
			com.ylink.upp.oxm.entity.upp_801_001_01.RespInfo receive = (com.ylink.upp.oxm.entity.upp_801_001_01.RespInfo) receiveMsg(
					msgHeader, xmlBody, signMsg);
			System.out.println(receive.getGrpBody().getMerSeqId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private XmlBodyEntity receiveMsg(String msgHeader, String xmlMsg, String sign) {
		OxmHandler oxmHandler = DubboConsumerFactory.getService(OxmHandler.class);
		try {
			boolean verify = this.verify(xmlMsg, sign);
			if (!verify) {
				System.out.println("验签失败");
			}
			HeaderBean headerBean = new HeaderBean();
			HandlerMsgUtil.conversion(msgHeader, headerBean);
			xmlMsg = Dom4jHelper.addNamespace(xmlMsg, headerBean.getMesgType(), "UTF-8");
			return (XmlBodyEntity) oxmHandler.unmarshaller(xmlMsg);
		} catch (Exception e) {
			System.out.println("接收数据时发生异常，错误信息为:" + e.getMessage());
			throw new RuntimeException(e);
		}

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

	/**
	 * 拼装报文头
	 * 
	 * @return
	 */
	private String initMsgHeader(String merNo, String tranType) {
		StringBuffer buffer = new StringBuffer("{H:01");
		buffer.append(merNo);
		buffer.append("1000000000000000");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		buffer.append(dateFormat.format(new Date(System.currentTimeMillis())));
		buffer.append(tranType);
		buffer.append("}");
		return buffer.toString();
	}

	/**
	 * 加签
	 * 
	 * @param xmlMsg
	 * @return
	 * @throws Exception
	 */
	private String sign(String xmlMsg) throws Exception {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource pfxResource = resourceLoader.getResource("classpath:CO20160800000017.pfx"); // 商户公钥加签
		InputStream in = new FileInputStream(pfxResource.getFile());
		byte[] pfxByte = IOUtils.toByteArray(in);
		String sign = SecurityUtil.pfxWithSign(pfxByte, xmlMsg, "111111");
		return sign;
	}

	/**
	 * 验签
	 * 
	 * @param xmlMsg
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	private boolean verify(String xmlMsg, String sign) throws Exception {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource pfxResource = resourceLoader.getResource("classpath:mobile.cer"); // 商户私钥解签
		InputStream in = new FileInputStream(pfxResource.getFile());
		byte[] cerByte = IOUtils.toByteArray(in);
		;
		return SecurityUtil.verify(cerByte, xmlMsg, sign);
	}

}
