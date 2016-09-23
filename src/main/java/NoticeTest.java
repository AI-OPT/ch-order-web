

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.ch.order.web.vo.Key;
import com.ai.ch.order.web.vo.KeyType;
import com.changhong.upp.business.entity.upp_599_001_01.RespInfo;
import com.changhong.upp.business.entity.upp_801_001_01.GrpBody;
import com.changhong.upp.business.entity.upp_801_001_01.GrpHdr;
import com.changhong.upp.business.entity.upp_801_001_01.ReqsInfo;
import com.changhong.upp.business.handler.BusinessHandler;
import com.changhong.upp.business.handler.factory.BusinessHandlerFactory;
import com.changhong.upp.business.type.TranType;
import com.changhong.upp.exception.UppException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:**/core-context.xml")
public class NoticeTest {
	
	
	@Autowired
	private BusinessHandlerFactory businessHandlerFactory;
	@Resource(name="key")
	private Key key;
	
	private String requestUrl = "http://111.9.116.138:7001/upp-route/entry.html";
	
	@Test
	public void doRefund(){
		
		GrpHdr hdr = new GrpHdr();
		hdr.setMerNo("CO20160900000009");
		hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		hdr.setTranType(TranType.REFUND_APPLY.getValue());
		
		GrpBody body = new GrpBody();
		body.setPayTranSn("T20160922100005328");
		body.setMerSeqId("M004");
		body.setRefundAmt("10");
		body.setMerRefundSn("M003");
		body.setSonMerNo("CO20160900000010");
		body.setRefundDate("20120908");
		body.setNotifyUrl("http://www.baidu.com");
		body.setResv("test");
		
		ReqsInfo info = new ReqsInfo();
		info.setGrpHdr(hdr);
		info.setGrpBody(body);
		
		BusinessHandler handler = businessHandlerFactory.getInstance(TranType.REFUND_APPLY);
		try {
			RespInfo rp = (RespInfo) handler.process(requestUrl, info, key.getKey(KeyType.PRIVATE_KEY), key.getKey(KeyType.PUBLIC_KEY));
			if(!"90000".equals(rp.getGrpBody().getStsRsn().getRespCode())){
            	System.out.println("error");
            }else{
            	System.out.println("success");
            }
		} catch (UppException e) {
			//ddAttribute("erro", e.getMessage());
			System.out.println("error");
		}
	}
	
}
