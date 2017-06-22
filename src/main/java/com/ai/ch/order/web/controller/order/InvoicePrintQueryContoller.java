package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.ch.order.web.model.order.InvoicePrintQueryVo;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.RequestParameterUtils;
import com.ai.ch.order.web.utils.SQLSafeUtils;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.alibaba.fastjson.JSON;

@RestController
public class InvoicePrintQueryContoller {
	private static final Logger log = LoggerFactory.getLogger(InvoicePrintQueryContoller.class);
	
	//发票打印列表查看
	@RequestMapping(value="/invoiceController/queryList",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseData<PageInfo<InvoicePrintQueryVo>> queryList(HttpServletRequest request){
		//
		String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
		//
		InvoicePrintRequest requestVo = RequestParameterUtils.request2Bean(request, InvoicePrintRequest.class);
		requestVo.setInvoiceTitle(SQLSafeUtils.safe(requestVo.getInvoiceTitle()));
		//
		//
		requestVo.setPageNo(Integer.parseInt(strPageNo));
		requestVo.setPageSize(Integer.parseInt(strPageSize));
		
		InvoicePrintResponse response = DubboConsumerFactory.getService(IInvoicePrintSV.class).queryList(requestVo);
		
		List<InvoicePrintQueryVo> queryList=new ArrayList<InvoicePrintQueryVo>();
		PageInfo<InvoicePrintQueryVo> pageQuery=new PageInfo<InvoicePrintQueryVo>();
		PageInfo<InvoicePrintVo> info = response.getPageInfo();
		List<InvoicePrintVo> result = info.getResult();
		BeanUtils.copyProperties(pageQuery, info);
		for (InvoicePrintVo invoicePrintVo : result) {
			InvoicePrintQueryVo invoiceQuery=new InvoicePrintQueryVo();
			BeanUtils.copyProperties(invoiceQuery, invoicePrintVo);
			invoiceQuery.setInvoiceMoney(AmountUtil.LiToYuan(invoicePrintVo.getInvoiceAmount()));
			invoiceQuery.setTaxMoney(AmountUtil.LiToYuan(invoicePrintVo.getTaxAmount()));
			queryList.add(invoiceQuery);
		}
		pageQuery.setResult(queryList);
		ResponseData<PageInfo<InvoicePrintQueryVo>> responseData = new ResponseData<PageInfo<InvoicePrintQueryVo>>(ResponseData.AJAX_STATUS_SUCCESS,"success",pageQuery);
		//
		log.info("responseData:"+JSON.toJSONString(responseData));
		return responseData;
	}
}
