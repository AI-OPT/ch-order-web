package com.ai.ch.order.web.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;


public class ParameterMapUtils {
	
	public static final String PREFIX = "command.";
	protected transient final Log log = LogFactory.getLog(getClass());
	public static Map<String,String> getPropertiesName(String paramName){
		Map<String ,String> hashMap = new HashMap<String,String>();
		return hashMap;
	}
	/**
	 * @desc 将驼峰写法转换为下划线的方式
	 * @param param
	 * @return
	 */
	public static String camel4underline(String param){
		Pattern  p=Pattern.compile("[A-Z]");
		if(param==null ||param.equals("")){
			return "";
		}
		StringBuilder builder=new StringBuilder(param);
		Matcher mc=p.matcher(param);
		int i=0;
		while(mc.find()){
			builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());
			i++;
		}

		if('_' == builder.charAt(0)){
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	/**
	 * @desc 根据前缀得到相关的参数信息,前缀为 "commond."
	 * @param request
	 * @param prefix
	 * @return
	 */
	public static Map<String, String> getParametersStartingWith(ServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				//
				if(values != null) {
					int iLen = values.length;
					for(int i = 0; i < iLen; i ++) {
						//
						params.put(unprefixed, values[i]);
					}
				}
			}
		}
		return params;
	}
	
	public static Map<String, String> getCommandParametersMap(ServletRequest request) {
		return ParameterMapUtils.getParametersStartingWith(request, PREFIX);
	}

}
