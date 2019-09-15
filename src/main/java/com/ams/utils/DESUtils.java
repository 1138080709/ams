package com.ams.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * DES加密解密工具类
 * @author LiuYu
 *
 */
public class DESUtils {
	
	private static Key key;
	
	public static void main(String[] args) {
		System.out.println(DESUtils.getEncryptString("root"));
		System.out.println(DESUtils.getEncryptString("123456"));
	}
		
	private static String KEY_STR = "9ryVC4PKSfZhFaYVpYkUypfcJpb6xeE6yek32uY6";

	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY_STR.getBytes());
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对字符串进行加密，返回BASE64的加密字符串 
	 * <功能详细描述>
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getEncryptString(String str) {
		Encoder encoder = Base64.getEncoder();
		try {
			byte[] strBytes = str.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return encoder.encodeToString(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 对BASE64加密字符串进行解密 
	 * <功能详细描述>
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getDecryptString(String str) {
		Decoder decoder = Base64.getDecoder();
		try {
			byte[] strBytes = decoder.decode(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return new String(encryptStrBytes, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
