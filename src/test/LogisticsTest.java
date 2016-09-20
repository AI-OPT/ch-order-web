
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ai.ch.order.web.model.order.LogisticsDetail;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LogisticsTest {

	@Test
	public void test() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("oderNo", "755009000");
		params.put("com", "shunfeng");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", "fd6b6e82c806dff4ffe298b1f3425e45");
		String url = "http://10.19.13.16:28151/opaas/22/http/srv_up_3rdlogistics_iddetails";
		String param = JSON.toJSONString(params);
		
//		String a = "{ 'responseHeader':{'resultCode':'响应状态','resultMessage':'错误描述','success':true},'com': 'shunfeng','orderNo': '755009000','state': '0',"
//				            +"'messages':[{'context': '取件员已x收件','time': '2016-08-16 11:11:23'},"
//                            +"{'context': '上海分练中心已收件','time': '2016-08-16 11:11:23'}]}";

		try {
			String result = HttpClientUtil.sendPost(url,param,headers);
			 //将返回结果，转换为JSON对象 
	        JSONObject json=JSON.parseObject(result);
	        String reqResultCode=json.getString("resultCode");
	        if("000000".equals(reqResultCode)){
	        	JSONObject data=JSON.parseObject(json.getString("data"));
				String dataStr =data.getString("messages");
				JSONArray messages = JSONArray.parseArray(dataStr);
				Iterator<Object> it = messages.iterator();
				List<LogisticsDetail> logisticsDetails = new ArrayList<LogisticsDetail>();
				while (it.hasNext()) {
					LogisticsDetail detail = new LogisticsDetail();
					JSONObject ob = (JSONObject) it.next();
					detail.setTime(ob.getString("time"));
					detail.setContext(ob.getString("context"));
					logisticsDetails.add(detail);
				}
			} else {
				// 请求过程失败
				System.out.println("请求失败,请求错误码:" + reqResultCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
