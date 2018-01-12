package com.dazong.common.util;

import com.dazong.common.util.codec.AesUtil;
import com.dazong.common.util.codec.DigestUtil;
import com.dazong.common.util.codec.EncoderUtil;
import org.junit.Test;

import java.security.MessageDigest;

import static com.dazong.common.util.codec.DigestUtil.HASH_INTERATIONS;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class AseUtilTest {

    @Test
    public void md5UtilTest(){
       // 生成salt
		byte[] salt = DigestUtil.generateSalt(16);

		//将salt转成16进制字符串
		String saltStr = EncoderUtil.hexEncode(salt);
		System.err.println("saltStr="+saltStr);

		//给salt加密
		String saltSec = AesUtil.encrypt(saltStr);
		System.err.println("saltSec="+saltSec);

		//对密码加密
		byte[] hashPassword = DigestUtil.sha1("123456".getBytes(), salt, HASH_INTERATIONS);
		System.out.println(EncoderUtil.hexEncode(hashPassword));
//		System.out.println(EncoderUtil.hexEncode());
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] md5  = md.digest("123456".getBytes());
			System.out.println(EncoderUtil.hexEncode(md5));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
