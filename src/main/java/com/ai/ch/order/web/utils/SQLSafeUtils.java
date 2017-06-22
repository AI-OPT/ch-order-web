package com.ai.ch.order.web.utils;

import com.ai.opt.sdk.util.StringUtil;

/**
 * SQL安全处理
 * 
 * @date 2017年6月21日
 * @author caofz
 */
public class SQLSafeUtils {
	public static String[] words = { "select", "insert", "delete", "count(", "drop table", "update", "truncate", "asc(",
			"mid(", "char(", "xp_cmdshell", "exec", "master", "net", "and", "or", "where" };

	/**
	 * SQL安全关键字过滤
	 * 
	 * @param filed
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode
	 * @RestRelativeURL
	 */
	public static String safe(String field) {
		if (!StringUtil.isBlank(field)) {
			field = field.replaceAll("'", "＇");
			field = field.replaceAll(";", "；");
			field = field.replaceAll("--", "－－");
			field = field.replaceAll("/\\*\\*/", "／＊＊／");
		}
		return field;
	}

	/**
	 * SQL安全关键字过滤
	 * 
	 * @param field
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public static String safeStrict(String field) {
		if (field != null) {
			field = safe(field);
			field = field.toLowerCase();
			for(String w : words){
				field = field.replaceAll(w, "");
			}
		}
		return field;
	}

}
