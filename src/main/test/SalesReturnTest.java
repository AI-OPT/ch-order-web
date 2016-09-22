import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SalesReturnTest {

	@Test
	public void test() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountId", "jfat1.201609256101549914_0001");
		params.put("openId", "2ecee85451c3460a");
		params.put("appId", "30a10e21");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", Constants.INTEGRAL_SEARCH_APPKEY);
		String param = JSON.toJSONString(params);
		try {
			String result = HttpClientUtil.sendPost(Constants.INTEGRAL_SEARCH_URL,param,headers);
			 //将返回结果，转换为JSON对象 
	        JSONObject json=JSON.parseObject(result);
	        String reqResultCode=json.getString("resultCode");
	        if("000000".equals(reqResultCode)){
	        	JSONObject data=JSON.parseObject(json.getString("data"));
				String dataStr =data.getString("code");
				if("200".equals(dataStr)){
					String cash =data.getString("cash");
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
