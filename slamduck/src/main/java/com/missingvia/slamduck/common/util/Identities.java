package com.missingvia.slamduck.common.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 提供生成各种唯一标识的方法。
 *
 */
public class Identities {
	
	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuidNoLine() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	/**
	 * 通过时间戳和随机数生成ID，
	 * 由当前时间yyyyMMddHHmmss + 4位随机数(base62)组成。 
	 * @return ID号，例：20140717113649PXQA
	 */
	public static String generateIdByTimeAndRandom() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date()) + randomBase62(4);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(uuid());
		System.out.println(randomBase62(10));
		System.out.println(generateIdByTimeAndRandom());

	}

}
