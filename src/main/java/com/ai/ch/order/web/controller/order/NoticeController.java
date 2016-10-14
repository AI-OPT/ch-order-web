package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.vo.Key;
import com.ai.ch.order.web.vo.KeyType;
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
	@Resource(name="key")
	private Key key;
	@RequestMapping("/payNotice")
	public String payNotice( @RequestParam("msgHeader") String msgHead,@RequestParam("xmlBody") String xmlBody,@RequestParam("signMsg") String signMsg){
		//验签
			try{
				IOrderPaySV iOrderPaySV = DubboConsumerFactory.getService(IOrderPaySV.class);	
				boolean flag = RSACoder.verify(key.getKey(KeyType.PUBLIC_KEY), xmlBody, signMsg);
				if (!flag) {
					throw new UppException("验签失败");
				}
				com.changhong.upp.business.entity.upp_103_001_01.RespInfo receive = (com.changhong.upp.business.entity.upp_103_001_01.RespInfo) XBConvertor.toBean(xmlBody, com.changhong.upp.business.entity.upp_103_001_01.RespInfo.class);
				//获取支付状态
	             String state =  receive.getGrpBody().getPayStatus();
	             //02表示支付成功，03表示支付失败
	             if(!StringUtil.isBlank(state)){
	            	 if("02".equals(receive.getGrpBody().getPayStatus())){
	            		 //更新订单状态
	            		 OrderPayRequest request = new OrderPayRequest();
	            		 List<Long> orderIds = new ArrayList<Long>();
	            		 //获取支付金额
	            		 String money = receive.getGrpBody().getOrderAmt();
	            		 Long ordAmt = AmountUtil.FToL(money);
	            		 request.setPayFee(ordAmt);
	            		 request.setPayType(receive.getGrpBody().getPaymentChannel());
	            		 String ordId = receive.getGrpBody().getMerOrderId();
	            		 orderIds.add(Long.valueOf(ordId));
	            		 request.setTenantId(Constants.TENANT_ID);
	            		 request.setOrderIds(orderIds);
	            		 request.setExternalId(receive.getGrpBody().getPayTranSn());
	            		 iOrderPaySV.pay(request);
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
	@RequestMapping("/refundNotice")
	public String refundNotice( @RequestParam("msgHeader") String msgHead,@RequestParam("xmlBody") String xmlBody,@RequestParam("signMsg") String signMsg){
		OrdRequest request = new OrdRequest();
		//验签
			try{
				boolean flag = RSACoder.verify(key.getKey(KeyType.PUBLIC_KEY), xmlBody, signMsg);
				if (!flag) {
					throw new UppException("验签失败");
				}
				com.changhong.upp.business.entity.upp_803_001_01.RepsInfo receive = (com.changhong.upp.business.entity.upp_803_001_01.RepsInfo) XBConvertor.toBean(xmlBody, com.changhong.upp.business.entity.upp_803_001_01.RepsInfo.class);
				//获取订单状态
				String orderid = receive.getGrpBody().getMerRefundSn();
				IOrderModifySV iOrderModifySV = DubboConsumerFactory.getService(IOrderModifySV.class);
				if("01".equals(receive.getGrpBody().getRefundStatus())){
					request.setOrderId(Long.parseLong(orderid));
					request.setState(Constants.OrdOrder.State.REFUND_COMPLETE);
					iOrderModifySV.modify(request);
					return "SUCCESS";
				}else{
					return "FAILED";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
	}
}
