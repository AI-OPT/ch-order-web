package com.ai.ch.order.web.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.order.api.ordercheck.interfaces.IOrderCheckSV;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@Controller
public class PaidOrderController {
	private static final Logger LOG = Logger.getLogger(PaidOrderController.class);
	@RequestMapping("/toPaidOrder")
	public ModelAndView toPaidOrder(HttpServletRequest request) {

		return new ModelAndView("jsp/order/paidOrderList");
	}
	@RequestMapping("/toChangeOrderFirst")
	public ModelAndView toChangeOrderFirst(HttpServletRequest request) {

		return new ModelAndView("jsp/order/changeGoodsFirst");
	}
	@RequestMapping("/toChangeOrderSecond")
	public ModelAndView toChangeOrderSecond(HttpServletRequest request) {

		return new ModelAndView("jsp/order/changeGoodsSecond");
	}
	@RequestMapping("/toBackOrderFirst")
	public ModelAndView toBackOrderFirst(HttpServletRequest request) {

		return new ModelAndView("jsp/order/backGoodsFirst");
	}
	@RequestMapping("/toBackOrderSecond")
	public ModelAndView toBackOrderSecond(HttpServletRequest request) {

		return new ModelAndView("jsp/order/backGoodsSecond");
	}
	
	
	
	/**
     * 数据查询
     * @param request
     * @return
     */
    @RequestMapping("/getPaidOrderData")
    @ResponseBody
    public ResponseData<PageInfo<BehindParentOrdOrderVo>> getList(HttpServletRequest request,BehindQueryOrderListRequest req){
        //HttpSession session = request.getSession();
        //SSOClientUser user = (SSOClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
        ResponseData<PageInfo<BehindParentOrdOrderVo>> responseData = null;
       // req.setTenantId(user.getTenantId());
        req.setTenantId("SLP");
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        try {
            req.setPageNo(Integer.parseInt(strPageNo));
            req.setPageSize(Integer.parseInt(strPageSize));
            BehindQueryOrderListResponse resultInfo = iOrderListSV.behindQueryOrderList(req);
            PageInfo<BehindParentOrdOrderVo> result= resultInfo.getPageInfo();
            responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", result);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
	
	
	
	@RequestMapping("/changeFirstDetail")
	public ModelAndView changeFirstDetail(HttpServletRequest request, String orderId) {
		Map<String, OrdOrderVo> model = new HashMap<String, OrdOrderVo>();
		try {
			OrdOrderVo order= getOrder(request,orderId);
			List<OrdProductVo> proList = order.getProductList();
			//获取图片
			if (!CollectionUtil.isEmpty(proList)) {
				for (OrdProductVo vo:proList) {
					vo.setImageUrl(
							ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
				}
			}
			 model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("订单详情查询报错：", e);
		}
		return new ModelAndView("jsp/order/changeGoodsFirst", model);
	}

	public OrdOrderVo getOrder(HttpServletRequest request,String orderId) {
		OrdOrderVo responseData = new OrdOrderVo();
		try {
			QueryOrderRequest req = new QueryOrderRequest();
			req.setOrderId(35913355l);
			req.setTenantId("SLP");
			IOrderListSV orderListSV=DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderResponse response=orderListSV.queryOrder(req);
			if(response!=null && response.getResponseHeader().isSuccess()){
				responseData =  response.getOrdOrderVo();
			}
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("换货详情查询报错：", e);
		}
		return responseData;
	}
	//第一次换货点击同意调用换货服务
	@RequestMapping("/firstChange")
	@ResponseBody
	public ResponseData<String> Change(HttpServletRequest request, String orderId,String refuseInfo,boolean isRefuse) {
		ResponseData<String> responseData = null;
		BaseResponse base = null;
		OrderCheckRequest req = new OrderCheckRequest();
		try {
			IOrderCheckSV iOrderCheckSV = DubboConsumerFactory.getService(IOrderCheckSV.class);
			req.setTenantId("SLP");
			req.setOrderId(35913355l);
			req.setOperId("33213");
			//判断是拒绝还是同意换货
			if(isRefuse==false){
				//改变状态
				 req.setCheckResult("1");
				 base =  iOrderCheckSV.check(req);
			}else{
				 req.setCheckResult("2");
				 req.setRemark(refuseInfo);
				 base = iOrderCheckSV.check(req);
			}
			
			if(base.getResponseHeader().getIsSuccess()==true){
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "同意换货成功", null);
			}else{
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "同意换货失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("同意换货审核查询报错：", e);
		}
		return responseData;
	}
		
		
		@RequestMapping("/backFirstDetail")
		public ModelAndView backFirstDetail(HttpServletRequest request, String orderId) {
			Map<String, OrdOrderVo> model = new HashMap<String, OrdOrderVo>();
			try {
				OrdOrderVo order= getOrder(request,orderId);
				List<OrdProductVo> proList = order.getProductList();
				//获取图片
				if (!CollectionUtil.isEmpty(proList)) {
					for (OrdProductVo vo:proList) {
						vo.setImageUrl(
								ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
					}
				}
				 model.put("order", order);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("订单详情查询报错：", e);
			}
			return new ModelAndView("jsp/order/backGoodsFirst", model);
		}
		
		
		//第一次换货点击同意调用换货服务
		@RequestMapping("/firstBack")
		@ResponseBody
		public ResponseData<String> Back(HttpServletRequest request, String orderId,String refuseInfo,boolean isRefuse) {
			ResponseData<String> responseData = null;
			BaseResponse base = null;
			OrderCheckRequest req = new OrderCheckRequest();
			try {
				IOrderCheckSV iOrderCheckSV = DubboConsumerFactory.getService(IOrderCheckSV.class);
				req.setTenantId("SLP");
				req.setOrderId(35913355l);
				req.setOperId("33213");
				//判断是拒绝还是同意换货
				if(isRefuse==false){
					//改变状态
					 req.setCheckResult("1");
					 base =  iOrderCheckSV.check(req);
				}else{
					 req.setCheckResult("2");
					 req.setRemark(refuseInfo);
					 base = iOrderCheckSV.check(req);
				}
				
				if(base.getResponseHeader().getIsSuccess()==true){
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "同意退货成功", null);
				}else{
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "同意退货失败", null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("同意换货审核查询报错：", e);
			}
			return responseData;
		}		
		
}
