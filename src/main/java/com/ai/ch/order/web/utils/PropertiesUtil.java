package com.ai.ch.order.web.utils;
import java.util.Properties;  
import java.util.concurrent.ConcurrentHashMap;  
import java.util.concurrent.ConcurrentMap;

import com.ai.opt.base.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;  

/**
 * 配置信息公共类 
 */
public class PropertiesUtil {

	private static ResourceLoader loader = ResourceLoader.getInstance();  
	    private static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<String, String>();  
	    private static final String DEFAULT_CONFIG_FILE = "ofcConfig.properties";  
	  
	    private static Properties prop = null;  
	    
	    /**
	     * 读取配置文件信息
	     * @throws Exception 
	     */
	    public static String getStringByKey(String key, String propName) throws Exception {  
	        try {  
	            prop = loader.getPropFromProperties(propName);  
	        } catch (BusinessException e) {  
	            throw new BusinessException(e);  
	        }  
	        key = key.trim();  
	        if (!configMap.containsKey(key)) {  
	            if (prop.getProperty(key) != null) {  
	                configMap.put(key, prop.getProperty(key));  
	            }  
	        }  
	        return configMap.get(key);  
	    }  
	  
	    public static String getStringByKey(String key) throws Exception {  
	        return getStringByKey(key, DEFAULT_CONFIG_FILE);  
	    }  
	    /** 获取配置信息*/
	    public static Properties getProperties() throws Exception {  
	        try {  
	            return loader.getPropFromProperties(DEFAULT_CONFIG_FILE);  
	        } catch (BusinessException e) {  
	           Log.error("操作失败"+JSON.toJSONString(e));  
	            return null;  
	        }  
	    }  
}
