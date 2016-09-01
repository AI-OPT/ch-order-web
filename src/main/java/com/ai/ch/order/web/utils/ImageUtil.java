package com.ai.ch.order.web.utils;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.StringUtil;

public class ImageUtil {
    public static String getImage(String vsid, String pictype) {
    	vsid="57bea0d9c9e77c0006309cb4";
    	pictype="jpg";
        IImageClient im = null;
        // 应用场景
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        im = IDPSClientFactory.getImageClient(idpsns);
        // 获取上传图片的URL
        if(StringUtil.isBlank(vsid) && StringUtil.isBlank(pictype)){
        	return null;
        }else{
        	  return im.getImageUrl(vsid,pictype,"12x13");
        }
      
    }
}
