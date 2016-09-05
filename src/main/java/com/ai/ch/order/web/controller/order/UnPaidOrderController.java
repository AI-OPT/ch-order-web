package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.OrdOrderResVo;
import com.ai.ch.order.web.model.order.OrdProdInfo;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.alibaba.fastjson.JSON;

@Controller
public class UnPaidOrderController {
	private static final Logger LOG = Logger.getLogger(UnPaidOrderController.class);
	@RequestMapping("/toUnpaidOrder")
	public ModelAndView register(HttpServletRequest request) {
		BehindQueryOrderListRequest orderListRequest=new BehindQueryOrderListRequest();
		orderListRequest.setTenantId("SLP");
		orderListRequest.setPageNo(1);
		orderListRequest.setPageSize(5);
		IOrderListSV orderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
		BehindQueryOrderListResponse response = orderListSV.behindQueryOrderList(orderListRequest);
		System.out.println(JSON.toJSONString(response));
		return new ModelAndView("jsp/order/unpaidOrderList");
	}
	
	@RequestMapping("/unpaidDetail")
	public ModelAndView alertDetail(HttpServletRequest request, String orderId) {
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    	Map<String, OrdOrderResVo> model = new HashMap<String, OrdOrderResVo>();
    	IOrderListSV orderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		QueryOrderRequest  query =new QueryOrderRequest ();
		OrdOrderResVo orderDetail = new OrdOrderResVo();
		List<OrdProdInfo> productList = new ArrayList<OrdProdInfo>();
		SysParamSingleCond param = new SysParamSingleCond();
		try {
			//Long Id = Long.parseLong(orderId);
			query.setOrderId(3345703912l);
			query.setTenantId("changhong");
			QueryOrderResponse response = orderListSV.queryOrder(query);
			if(response!=null && response.getResponseHeader().isSuccess()){
				OrdOrderVo  ordOrderVo =  response.getOrdOrderVo();
				if(ordOrderVo!=null){
					BeanUtils.copyProperties(orderDetail, ordOrderVo);
            		//翻译订单来源
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(orderDetail.getChlId());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_CHL_ID);
            		SysParam chldParam = iCacheSV.getSysParamSingle(param);
            		if(chldParam!=null){
            			orderDetail.setChlId(chldParam.getColumnDesc());
            		}
            		orderDetail.setOrdAdjustFee(AmountUtil.LiToYuan(ordOrderVo.getAdjustFee()));
					List<OrdProductVo> proList = ordOrderVo.getProductList();
					//获取图片
					if (!CollectionUtil.isEmpty(proList)) {
						for (OrdProductVo vo:proList) {
							OrdProdInfo product = new OrdProdInfo();
							//翻译金额
							product.setProdDiscountFee(AmountUtil.LiToYuan(vo.getDiscountFee()));
							product.setProdSalePrice(AmountUtil.LiToYuan(vo.getSalePrice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(vo.getAdjustFee()));
							product.setImageUrl(ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
							product.setProdName(vo.getProdName());
							product.setBuySum(vo.getBuySum());
							//product.setJf(vo.getJf());
							productList.add(product);
						}
					}
					orderDetail.setProdInfo(productList);
				}
			}
			 model.put("order", orderDetail);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("待付款订单详情查询报错：", e);
		}
		return new ModelAndView("jsp/order/unpaidOrderDetail", model);
	}
}
