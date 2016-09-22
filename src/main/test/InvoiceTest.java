import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.utils.InvoiceUtils;
import com.upp.docking.covn.MsgString;
import com.upp.docking.enums.TranType;
import com.ylink.upp.base.oxm.util.MsgUtils;
import com.ylink.upp.base.oxm.util.OxmHandler;
import com.ylink.upp.oxm.entity.upp_600_001_01.GrpBody;
import com.ylink.upp.oxm.entity.upp_600_001_01.GrpHdr;
import com.ylink.upp.oxm.entity.upp_600_001_01.RespInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:**/core-context.xml")
public class InvoiceTest {
	@Autowired
	private OxmHandler oxmHandler;
	@Test
	public void test() {
		String merNo = "";
		String date_now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		GrpHdr hdr = new GrpHdr();
		hdr.setMerNo(merNo);
		hdr.setCreDtTm(date_now);
		hdr.setTranType(TranType.INVOICE_PRINT.getValue());

		GrpBody body = new GrpBody();
		body.setCorporationCode("000003"); // 公司代码
		body.setInvoiceClass("0");// 发票类型 0 电子发票 1纸质发票
		body.setInvoiceKind("003"); // 发票种类 001增值税发票 002增值税普通电子发票 003普通发票
									// 004增值税普通发票 005增值税纸质发票 增值税电子发票
		body.setBuyerTaxpayerNumber("510798326942604"); // 购货方纳税人识别号
		body.setBuyerCode("");// 购货方代码
		body.setBuyerBankName("");// 购货方开户行
		body.setBuyerName("四川智易家网络科技有限公司"); // 购货方名称
		body.setBuyerAddress("四川绵阳高新区绵兴东路35号 "); // 购货方地址
		body.setBuyerProvince("四川"); // 购货方省份
		body.setBuyerTelephone("15802856879");// 购货方固定电话
		body.setBuyerMobiile("0816-2438114");// 购货方手机
		body.setBuyerEmail("519945018@qq.com");// 购货方邮箱
		body.setBuyerCompanyClass("03"); // 购货方类型 01:企业 02：机关事业单位 03：个人 04：其它
		body.setBuyerBankAccount("中国工商银行股份有限公司绵阳高新技术产业开发支行2308414119100081746"); // 购货方银行账号

		body.setSalesOrderNo("0000012"); // 销售订单号
		body.setOrderItem("123456789"); // 项目号
		body.setOrderCreateTime(date_now); // （销售）订单创建日期
		body.setDeliveryOrderNo("122323"); // 交货单号

		body.setMaterialName("43U1"); // 物料代码
		body.setMaterialCode("液晶电视"); // 物料名称
		body.setAmount("2213.67666666667"); // (单价)金额
		body.setDiscountAmount("0.00"); // 折扣金额
		body.setQuantity("3"); // 数量
		body.setProjectUnit("台");// 物料单位
		body.setRate("0.17"); // 税率
		body.setTax("1128.97"); // 税金
		body.setTaxAmount("7770"); // 含税金额

		body.setSettledQuantity("3"); // 已结算数量
		body.setSettledAmount("7770");// 已结算金额
		body.setUnSettledQuantity("0"); // 未结算数量
		body.setUnSettledAmount("0"); // 未结算金额
		body.setVoucherNumber("00001"); // 系统凭证号
		body.setVoucherData(date_now); // 系统凭证号日期
		body.setProducteGroup("电视机"); // 物料名称

		RespInfo respInfo = new RespInfo();
		respInfo.setGrpHdr(hdr);
		respInfo.setGrpBody(body);
		// 发送消息
		String xmlMsg = null;
		try {
			xmlMsg = oxmHandler.marshal(respInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> param = new TreeMap<String, String>();
		// 加签
		String sign = null;
		try {
			sign = InvoiceUtils.sign(xmlMsg);
			param.put("signMsg", sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拼装报文头
		String msgHeader = InvoiceUtils.initMsgHeader(merNo, TranType.INVOICE_PRINT.getValue());
		param.put("msgHeader", msgHeader);
		param.put("xmlBody", xmlMsg);
		String result = null;
		try {
			result = InvoiceUtils.sendHttpPost(Constants.INVOICE_PRINT_URL,param, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MsgString msgString = MsgUtils.patch(result);
		String rh = msgString.getHeaderMsg();
		String rb = msgString.getXmlBody();
		String rs = msgString.getDigitalSign();

		com.ylink.upp.oxm.entity.upp_600_001_01.RespInfo receive = (com.ylink.upp.oxm.entity.upp_600_001_01.RespInfo) InvoiceUtils
				.receiveMsg(rh, rb, rs);

	}

}
