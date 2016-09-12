package com.ai.ch.order.web.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.ai.opt.sdk.util.StringUtil;

public class AmountUtil {
  
    
    /** 
     * 将厘为单位的转换为元 （除1000）  
     * 
     */
    public static String LiToYuan(Long amount){
        if(amount == null || amount == 0){
            return "0.00";
        }
        BigDecimal balance = BigDecimal.valueOf(amount).divide(new BigDecimal(1000L),2,BigDecimal.ROUND_HALF_UP);
        return new DecimalFormat(",###,##0.00").format(balance);
    }
    /** 
     * 将元为单位的转换为里 （乘1000）  
     * 
     */
    public static Long YuanToLi(Long amount){
        if(amount == null || amount == 0){
            return 0L;
        }
        BigDecimal balance = BigDecimal.valueOf(amount).multiply(new BigDecimal(1000L));
        return balance.longValue();
    }
    
    public static Long YToLi(String amount){
        if(StringUtil.isBlank(amount)){
            return 0L;
        }
        BigDecimal money=new BigDecimal(amount);
        BigDecimal balance = money.multiply(new BigDecimal(1000L));
        return balance.longValue();
    }
    
}
