package com.ai.ch.order.web.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
    
}
