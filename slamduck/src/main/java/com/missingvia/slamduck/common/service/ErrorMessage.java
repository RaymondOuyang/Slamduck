package com.missingvia.slamduck.common.service;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;


/**
 * 从com/ideal/common/service/ErrorMessage.xml文件中读取错误信息。
 * 
 *
 */
public class ErrorMessage {
	
	private static ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
	
	static {
		rrbms.setBasename("classpath:com/ideal/common/service/ErrorMessage");
		rrbms.setDefaultEncoding("UTF-8");
	}
	
	/**
	 * 根据错误代码获取对应的错误信息，并注入变量值。
	 * @param errorCode 错误代码。
	 * @param args 将要注入到错误信息中的变量，变量值取代错误信息中的{0},{1},{2}...占位符。如果没有变量则设为null。 
	 * @return 填入变量值的错误信息。
	 */
	public static String getMessage(ErrorCode errorCode, Object... args) {
		return rrbms.getMessage("mdmp.error." + errorCode.getCode(), args, Locale.SIMPLIFIED_CHINESE);
	}
	
	/**
	 * 根据错误代码获取对应的错误信息，并注入变量值，如果不存在错误信息则以默认信息代替。
	 * @param errorCode 错误代码。
	 * @param defaultMessage 默认信息。
	 * @param args 将要注入到错误信息中的变量，变量值取代错误信息中的{0},{1},{2}...占位符。如果没有变量则设为null。 
	 * @return 填入变量值的错误信息。
	 */
	public static String getMessage(ErrorCode errorCode, String defaultMessage,Object... args) {
		return rrbms.getMessage("mdmp.error." + errorCode.getCode(), args, defaultMessage,Locale.SIMPLIFIED_CHINESE);
	}
	
	/**
	 * 根据错误代码获取对应的错误信息。
	 * @param errorCode 错误代码。 
	 * @return 错误信息。
	 */
	public static String getMessage(ErrorCode errorCode) {
		return rrbms.getMessage("mdmp.error." + errorCode.getCode(), null, Locale.SIMPLIFIED_CHINESE);
	}
	
	/**
	 * 判断是否存在制定错误代码的错误信息。
	 * @param errorCode 错误代码。
	 * @return 如果xml文件中存在对应的错误信息则返回true，否则返回false。
	 */
	public static boolean exist(ErrorCode errorCode) {
		String msg = rrbms.getMessage("mdmp.error." + errorCode.getCode(), null, null,Locale.SIMPLIFIED_CHINESE);
		if(msg != null) {
			return true;
		}
		return false;
	}
	

	

	public static void main(String[] args) {
		String msg = ErrorMessage.getMessage(ErrorCode.SYSTEM_USER_NAME_REPEAT, "张三");
		System.out.println(ErrorMessage.exist(ErrorCode.SYSTEM_USER_NAME_REPEAT));
		
		System.out.println(msg);
	}

}
