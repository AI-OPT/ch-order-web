package com.ai.ch.order.web.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
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
	@RequestMapping("/aggreeChange")
	public ModelAndView aggreeChange(HttpServletRequest request, String orderId) {
		Map<String, OrdOrderVo> model = new HashMap<String, OrdOrderVo>();
		try {
			OrderReturnRequest req = new OrderReturnRequest();
			IOrderAfterSaleSV iOrderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
			req.setTenantId("SLP");
			req.setProdDetalId(1232l);
			req.setOrderId(35913355l);
			//改变状态，跳转页面
			iOrderAfterSaleSV.exchange(req);
			OrdOrderVo order= getOrder(request,orderId);
			List<OrdProductVo> proList = order.getProductList();
			/*//获取图片
			if (!CollectionUtil.isEmpty(proList)) {
				for (OrdProductVo vo:proList) {
					vo.setImageUrl(
							ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
				}
			}*/
			 model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("同意换货审核查询报错：", e);
		}
		return new ModelAndView("jsp/order/changeGoodsSecond", model);
	}
		//第一次换货点击拒绝调用换货服务
		@RequestMapping("/refuseChange")
		public ModelAndView refuseChange(HttpServletRequest request, String orderId,String refuseInfo) {
			try {
				OrderReturnRequest req = new OrderReturnRequest();
				IOrderAfterSaleSV iOrderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
				req.setTenantId("SLP");
				req.setProdDetalId(1232l);
				req.setOrderId(35913355l);
				System.out.println("================"+refuseInfo);
				//填写拒绝理由，跳转售后列表页面
				iOrderAfterSaleSV.exchange(req);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("拒绝换货审核查询报错：", e);
			}
			return new ModelAndView("jsp/order/paidOrderList");
		}
		
		
		@RequestMapping("/backFirstDetail")
		public ModelAndView backFirstDetail(HttpServletRequest request, String orderId) {
			Map<String, OrdOrderVo> model = new HashMap<String, OrdOrderVo>();
			try {
				OrdOrderVo order= getOrder(request,orderId);
				List<OrdProductVo> proList = order.getProductList();
				/*//获取图片
				if (!CollectionUtil.isEmpty(proList)) {
					for (OrdProductVo vo:proList) {
						vo.setImageUrl(
								ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
					}
				}*/
				 model.put("order", order);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("订单详情查询报错：", e);
			}
			return new ModelAndView("jsp/order/backGoodsFirst", model);
		}
		//第一次换货点击同意调用换货服务
		@RequestMapping("/aggreeBack")
		public ModelAndView aggreeBack(HttpServletRequest request, String orderId) {
			Map<String, OrdOrderVo> model = new HashMap<String, OrdOrderVo>();
			try {
				OrderReturnRequest req = new OrderReturnRequest();
				IOrderAfterSaleSV iOrderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
				req.setTenantId("SLP");
				req.setProdDetalId(1232l);
				req.setOrderId(35913355l);
				//改变状态，跳转页面
				iOrderAfterSaleSV.back(req);
				OrdOrderVo order= getOrder(request,orderId);
				List<OrdProductVo> proList = order.getProductList();
				/*//获取图片
				if (!CollectionUtil.isEmpty(proList)) {
					for (OrdProductVo vo:proList) {
						vo.setImageUrl(
								ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
					}
				}*/
				 model.put("order", order);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("同意换货审核查询报错：", e);
			}
			return new ModelAndView("jsp/order/backGoodsSecond", model);
		}
			//第一次换货点击拒绝调用换货服务
			@RequestMapping("/refuseBack")
			public ModelAndView refuseBack(HttpServletRequest request, String orderId,String refuseInfo) {
				try {
					OrderReturnRequest req = new OrderReturnRequest();
					IOrderAfterSaleSV iOrderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
					req.setTenantId("SLP");
					req.setProdDetalId(1232l);
					req.setOrderId(35913355l);
					//填写拒绝理由，跳转售后列表页面
					iOrderAfterSaleSV.back(req);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error("拒绝退货审核查询报错：", e);
				}
				return new ModelAndView("jsp/order/paidOrderList");
			}

}
