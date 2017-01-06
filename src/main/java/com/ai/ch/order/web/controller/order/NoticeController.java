package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.vo.Key;
import com.ai.ch.order.web.vo.KeyType;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.ordermodify.interfaces.IOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.changhong.upp.crypto.rsa.RSACoder;
import com.changhong.upp.exception.UppException;
import com.changhong.upp.util.XBConvertor;
@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(NoticeController.class);
	
	//支付通知
	@Resource(name="key")
	private Key key;
	@RequestMapping("/payNotice")
	public String payNotice( @RequestParam("msgHeader") String msgHead,@RequestParam("xmlBody") String xmlBody,@RequestParam("signMsg") String signMsg){
		 LOG.info(">>>>>>>>>>>>支付通知开始");
		 //验签
			try{
				IOrderPaySV iOrderPaySV = DubboConsumerFactory.getService(IOrderPaySV.class);	
				boolean flag = RSACoder.verify(key.getKey(KeyType.PUBLIC_KEY), xmlBody, signMsg);
				if (!flag) {
					LOG.error("验签失败......");
					throw new UppException("验签失败");
				}
				LOG.info(">>>>>>>>>>>>支付通知发起参数"+xmlBody);
				com.changhong.upp.business.entity.upp_103_001_01.RespInfo receive = (com.changhong.upp.business.entity.upp_103_001_01.RespInfo) XBConvertor.toBean(xmlBody, com.changhong.upp.business.entity.upp_103_001_01.RespInfo.class);
				LOG.info(">>>>>>>>>>>>支付通知返回参数"+receive);
				//获取支付状态
	             String state =  receive.getGrpBody().getPayStatus();
	             //02表示支付成功，03表示支付失败
	             if(!StringUtil.isBlank(state)){
	            	 if("02".equals(receive.getGrpBody().getPayStatus())){
	            		 LOG.info(">>>>>>>>>>>>支付成功");
	            		 //更新订单状态
	            		 OrderPayRequest request = new OrderPayRequest();
	            		 List<Long> orderIds = new ArrayList<Long>();
	            		 //获取支付金额
	            		 String money = receive.getGrpBody().getOrderAmt();
	            		 Long ordAmt = AmountUtil.FToL(money);
	            		 request.setPayFee(ordAmt);
	            		 if("00".equals(receive.getGrpBody().getPaymentChannel())){
	            			 request.setPayType(Constants.OrdOrder.PayStyle.CHANG_HONG_STYLE);
	            		 }else if("01".equals(receive.getGrpBody().getPaymentChannel())){
	            			 request.setPayType(Constants.OrdOrder.PayStyle.ZHIFUBAO_STYLE);
	            		 }else if("02".equals(receive.getGrpBody().getPaymentChannel())){
	            			 request.setPayType(Constants.OrdOrder.PayStyle.WEIXIN_STYLE);
	            		 }else if("03".equals(receive.getGrpBody().getPaymentChannel())){
	            			 request.setPayType(Constants.OrdOrder.PayStyle.YINLIAN_STYLE); 
	            		 }
	            		 String ordId = receive.getGrpBody().getMerOrderId();
	            		 orderIds.add(Long.valueOf(ordId));
	            		 request.setTenantId(Constants.TENANT_ID);
	            		 request.setOrderIds(orderIds);
	            		 request.setExternalId(receive.getGrpBody().getPayTranSn());
	            		 BaseResponse base= iOrderPaySV.pay(request);
	            		 LOG.info("支付数据沉淀"+base.getResponseHeader().getResultMessage());
	            		 return "SUCCESS";
	            	 }else{
	            		 return "FAILED";
	            	 }
	             }else{
	            	 return "FAILED";
	             }
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}
	
	//退款通知
	@RequestMapping("/refundNotice")
	public String refundNotice( @RequestParam("msgHeader") String msgHead,@RequestParam("xmlBody") String xmlBody,@RequestParam("signMsg") String signMsg){
		LOG.info(">>>>>>>>>>>>退款通知开始");
		OrdRequest request = new OrdRequest();
		request.setTenantId("changhong");
		//验签
			try{
				boolean flag = RSACoder.verify(key.getKey(KeyType.PUBLIC_KEY), xmlBody, signMsg);
				if (!flag) {
					LOG.error("验签失败......");
					throw new UppException("验签失败");
				}
				LOG.info(">>>>>>>>>>>>退款通知发起参数"+xmlBody);
				com.changhong.upp.business.entity.upp_803_001_01.RepsInfo receive = (com.changhong.upp.business.entity.upp_803_001_01.RepsInfo) XBConvertor.toBean(xmlBody, com.changhong.upp.business.entity.upp_803_001_01.RepsInfo.class);
				LOG.info(">>>>>>>>>>>>退款通知回传信息"+receive);
				//获取售后订单
				String cusOrderId = receive.getGrpBody().getMerRefundSn();
				IOrderModifySV iOrderModifySV = DubboConsumerFactory.getService(IOrderModifySV.class);
				if("01".equals(receive.getGrpBody().getRefundStatus())){
					LOG.info(">>>>>>>>>>>>退款通知成功");
					//修改售后订单为退款完成
					request.setOrderId(Long.parseLong(cusOrderId));
					request.setState(Constants.OrdOrder.State.REFUND_COMPLETE);
					BaseResponse base = iOrderModifySV.modify(request);
					//判断父订单是否只有一个商品将父订单状态改为退款完成
					LOG.info("退款修改订单服务>>>>>>"+base.getResponseHeader().getResultMessage());
					return "SUCCESS";
				}else if("00".equals(receive.getGrpBody().getRefundStatus())){
					LOG.info(">>>>>>>>>>>>退款中");
					request.setOrderId(Long.parseLong(cusOrderId));
					request.setState(Constants.OrdOrder.State.REFUND_ING);
					BaseResponse base = iOrderModifySV.modify(request);
				}else{
					LOG.info(">>>>>>>>>>>>退款失败");
					request.setOrderId(Long.parseLong(cusOrderId));
					request.setState(Constants.OrdOrder.State.REFUND_FAILD);
					BaseResponse base = iOrderModifySV.modify(request);
					return "FAILED";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}
}
