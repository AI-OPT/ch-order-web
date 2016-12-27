package com.ai.ch.order.web.utils;
import org.junit.Test;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.util.ImageByteUtil;
import com.ai.paas.ipaas.image.IImageClient;

/**
 * 图片信息处理测试
 */
public class ImageTest {

    @Test
    public void testAddImg(){
    //应用场景
    String idpsns="slp-mall-web-idps";
    //获取imageClient
    IImageClient im = IDPSClientFactory.getImageClient(idpsns);

    //待上传的图片路径
    String filepath="E:/picture/test.jpg";
    //将路径转换为byte[]
    byte[] buff=ImageByteUtil.image2byte(filepath);
    
    //上传图片，获取上传后的ID
    String vps=im.upLoadImage(buff, "test.jpg");
    System.out.println("idpsId="+vps);
    //获取上传图片的URL
    System.out.println(im.getImageUrl(vps, ".jpg"));
    //获取上传图片指定尺寸的URL
    System.out.println(im.getImageUrl("vps", ".jpg","100x80"));

    }


}
