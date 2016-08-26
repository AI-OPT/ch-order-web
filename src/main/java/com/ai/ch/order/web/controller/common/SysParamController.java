package com.ai.ch.order.web.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamMultiCond;

@RestController
public class SysParamController {
    /**
     * 获取客户等级
     * @param param
     * @param request
     * @return
     * @author zhanglh
     * @ApiCode
     */
    @RequestMapping("/getSelect")
    @ResponseBody
    public ResponseData<List<SysParam>> getSelect(SysParamMultiCond param,HttpServletRequest request) {
        ResponseData<List<SysParam>> responseData=null;
        
        try{
            ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
            param.setTenantId("changhong");
            List<SysParam> info=iCacheSV.getSysParamList(param);
            responseData=new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_SUCCESS,"获取数据成功",info);
        }catch(Exception e){
            responseData=new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_FAILURE,"获取数据失败",null);
        }
        return responseData;
    }
    
   
}
