package com.ai.ch.order.web.controller.order;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.common.ChUserController;
import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.StasticOrderReqVo;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.order.api.stasticsorder.interfaces.IStasticsOrderSV;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderResponse;
import com.ai.slp.order.api.stasticsorder.param.StasticParentOrderVo;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;

@Controller
public class StasticOrderController {
	private static final Logger LOG = Logger.getLogger(StasticOrderController.class);
	
	@RequestMapping("/toStasticOrder")
	public ModelAndView toAlertOrder(HttpServletRequest request) {
		return new ModelAndView("jsp/order/stasticOrder");
	}
	/**
     * 数据查询
     * @param request
     * @return
     */
    @RequestMapping("/getStasticOrderData")
    @ResponseBody
    public ResponseData<PageInfo<StasticParentOrderVo>> getList(HttpServletRequest request,StasticOrderReqVo reqVo){
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    	IStasticsOrderSV iStasticsOrderSV = DubboConsumerFactory.getService(IStasticsOrderSV.class);
        ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        StasticsOrderRequest req = new StasticsOrderRequest();
        ResponseData<PageInfo<StasticParentOrderVo>> responseData = null;
        if(!StringUtil.isBlank(reqVo.getSupplierName())){
        	//根据店铺名称获取销售商ID
            IShopInfoSV iShopInfoSV = DubboConsumerFactory.getService(IShopInfoSV.class);
            QueryShopInfoRequest shopReq = new QueryShopInfoRequest();
            shopReq.setTenantId(user.getTenantId());
            shopReq.setShopName(reqVo.getSupplierName());
            QueryShopInfoResponse base = iShopInfoSV.queryShopInfo(shopReq);
            if(base.getResponseHeader().getIsSuccess()==true){
            	req.setSupplierId(base.getUserId());
            }else{
            	req.setSupplierId("-1");
            }
        }
        if(!StringUtil.isBlank(reqVo.getUserName())){
        	//获取用户id
        	String id = ChUserController.getUserId(reqVo.getUserName());
        	if(!StringUtil.isBlank(id)){
        		req.setUserId(id);
        	}else{
        		req.setUserId("-1");
        	}
        }
        String startT =  reqVo.getStartTime();
        String endT = reqVo.getEndTime();
        if(!StringUtil.isBlank(startT)){
        	startT = startT + " 00:00:00";
			Timestamp ts  = Timestamp.valueOf(startT);
			req.setOrderTimeStart(ts);
	    }
	    if(!StringUtil.isBlank(endT)){
	    	endT = endT + " 23:59:59";
			Timestamp ts  = Timestamp.valueOf(endT);
			req.setOrderTimeEnd(ts);
	    }
        if(!StringUtil.isBlank(reqVo.getOrdParenOrderId())){
        	 Long Id = Long.parseLong(reqVo.getOrdParenOrderId());
        	 req.setOrderId(Id);
        }
        req.setTenantId(user.getTenantId());
        req.setState(reqVo.getState());
        req.setProdName(reqVo.getProdName());
        
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        try {
            req.setPageNo(Integer.parseInt(strPageNo));
            req.setPageSize(Integer.parseInt(strPageSize));
            StasticOrderResponse resultInfo = iStasticsOrderSV.queryStasticOrdPage(req);
            PageInfo<StasticParentOrderVo> result= resultInfo.getPageInfo();
            List<StasticParentOrderVo> list = result.getResult();
            if(!CollectionUtil.isEmpty(list)){
            	for(StasticParentOrderVo vo:list){
            		//翻译订单来源
					SysParamSingleCond	param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(vo.getChlId());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_CHL_ID);
            		SysParam chldParam = iCacheSV.getSysParamSingle(param);
            		if(chldParam!=null){
            			vo.setChlId(chldParam.getColumnDesc());
            		}
            		//翻译是否需要物流
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(vo.getDeliveryFlag());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_DELIVERY_FLAG);
            		SysParam ifDlive = iCacheSV.getSysParamSingle(param);
            		if(ifDlive!=null){
            			vo.setDeliveryFlag(ifDlive.getColumnDesc());
            		}
            	}
            }
            responseData = new ResponseData<PageInfo<StasticParentOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", result);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<StasticParentOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
}
