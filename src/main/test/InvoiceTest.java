import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.InvoicePrintInfo;
import com.ai.ch.order.web.utils.WcfUtils;
import com.alibaba.fastjson.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:**/core-context.xml")
public class InvoiceTest {
	@Test
	public void test() {
		InvoicePrintInfo body = new InvoicePrintInfo();
		String date_now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		body.setCorporationCode("000003"); // 公司代码
		body.setInvoiceClass("0");// 发票类型 0 电子发票 1纸质发票
		body.setInvoiceKind("003"); // 发票种类 001增值税发票 002增值税普通电子发票 003普通发票
									// 004增值税普通发票 005增值税纸质发票 增值税电子发票
		body.setBuyerTaxpayerNumber("510798326942604"); // 购货方纳税人识别号
		body.setBuyerCode("");// 购货方代码
		body.setBuyerName("四川智易家网络科技有限公司"); // 购货方名称
		body.setBuyerAddress("四川绵阳高新区绵兴东路35号 "); // 购货方地址
		body.setBuyerTelephone("15802856879");// 购货方固定电话
		body.setBuyerMobiile("0816-2438114");// 购货方手机
		body.setBuyerEmail("519945018@qq.com");// 购货方邮箱
		body.setBuyerCompanyClass("03"); // 购货方类型 01:企业 02：机关事业单位 03：个人 04：其它
		
		body.setBuyerBankCode("10010");//购货方开户行代码
		body.setBuyerBankName("中国工商银行股份有限公司绵阳高新技术产业开发支行");//购货方开户行名称
		body.setBuyerBankAccount("2308414119100081746"); // 购货方银行账号
		

		body.setSalesOrderNo("0000012"); // 销售订单号
		body.setOrderItem("123456789"); // 项目号
		body.setOrderCreateTime(date_now); // （销售）订单创建日期

		body.setMaterialName("43U1"); // 物料代码
		body.setSpecification("55q2n");
		body.setMaterialCode("液晶电视"); // 物料名称
		body.setPrice("2213.67666666667");
		body.setQuantity("3"); // 数量
		body.setUnit("台");// 物料单位
		body.setDiscountAmount("0.00"); // 折扣金额
		body.setRate("0.17"); // 税率
		body.setTax("1128.97"); // 税金
		body.setAmount("2213.67666666667"); // (单价)金额
		body.setTaxAmount("7770"); // 含税金额
		body.setRemark("打印发票请求");//备注
		

		// 服务地址
		HttpPost httpPost = new HttpPost(Constants.INVOICE_PRINT_URL);
		CloseableHttpClient client = HttpClients.createDefault();
		//获取授权ID
		body.setId(WcfUtils.getID(httpPost, client));//设置授权ID
		JSONObject invoicePrintJson =JSONObject.parseObject(JSONObject.toJSONString(body)); 
		String retVal = WcfUtils.postWcf(httpPost, client, invoicePrintJson.toJSONString());
	}

}
