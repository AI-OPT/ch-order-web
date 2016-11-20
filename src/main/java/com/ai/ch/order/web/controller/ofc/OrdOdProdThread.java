package com.ai.ch.order.web.controller.ofc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.ch.order.web.utils.PropertiesUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.ofc.interfaces.IOfcSV;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.alibaba.fastjson.JSON;

public class OrdOdProdThread extends Thread {

	private static final Log LOG = LogFactory.getLog(OrdOdProdThread.class);

	private IOfcSV ofcSV;

	private BlockingQueue<String[]> ordOdProdQueue;

	public OrdOdProdThread(BlockingQueue<String[]> ordOdProdQueue, IOfcSV ofcSV) {
		this.ordOdProdQueue = ordOdProdQueue;
		this.ofcSV = ofcSV;
	}

	public void run() {
		while (true) {
			try {
				String[] queue = ordOdProdQueue.poll(150, TimeUnit.SECONDS);
				if (null == queue) {
					break;
				}
				synchronized (queue) {
					/**
					 * 订单-商品表
					 */
					// 订单id
					OrdOdProdVo ordOdProd = new OrdOdProdVo();
					// 租户Id
					ordOdProd.setTenantId(PropertiesUtil.getStringByKey("ofc.ordOrder.tenantId"));
					// 订单id
					ordOdProd.setOrderId(Long.valueOf(queue[1]));
					// 物流号->商品编码
					ordOdProd.setProdCode(queue[2]);
					// 商品类型,默认'1',必传
					ordOdProd.setProdType(PropertiesUtil.getStringByKey("ofc.ordOdFeeTotal.prodType"));
					// 销售品Id,必传
					ordOdProd.setProdId(PropertiesUtil.getStringByKey("ofc.ordOdFeeTotal.prodId"));
					// 销售商品名,必传
					ordOdProd.setProdName(PropertiesUtil.getStringByKey("ofc.ordOdFeeTotal.prodName"));
					// SkuId,必传
					ordOdProd.setSkuId(PropertiesUtil.getStringByKey("ofc.ordOdFeeTotal.skuId"));
					// 生效时间,必传
					ordOdProd.setValidTime(DateUtil.getSysDate());
					// 状态,必传
					ordOdProd.setState(PropertiesUtil.getStringByKey("ofc.ordOdFeeTotal.state"));
					// 更新时间,必传
					ordOdProd.setUpdateTime(DateUtil.getSysDate());
					// 销售单价
					ordOdProd.setSalePrice(new Double(queue[5]).longValue());
					// 数量
					ordOdProd.setBuySum(Long.valueOf(queue[6]));
					LOG.info("保存订单商品信息开始,时间:" + DateUtil.getSysDate());
					LOG.info(JSON.toJSONString(ordOdProd));
					ofcSV.insertOrdOdProd(ordOdProd);
					LOG.info("保存订单商品信息结束,时间" + DateUtil.getSysDate());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
