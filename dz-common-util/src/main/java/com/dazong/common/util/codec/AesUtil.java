package com.dazong.common.util.codec;

import com.dazong.common.util.StringUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Random;

/**
 * 提供基于AES加密、解密的算法工具类
 * @author Sam
 * @version 1.0.0
 */
public class AES {
    /**
     * MD5 随机数
     *
     * @return
     */
    public static String md5RandomString(){

        String random = String.valueOf(System.currentTimeMillis())+ String.valueOf(new Random().nextLong());
        StringBuffer rs = new StringBuffer();
        try{
            byte[] input = random.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            byte[] security = md.digest(input);
            for(byte b:security){
                String sr = Integer.toHexString(b+128).toUpperCase();
                rs.append(sr.length()==2 ? sr : "0"+sr);
            }
        } catch (Exception e){ }
        return rs.toString();
    }

    /**
     * AES 对称加密
     * @param inStr
     * @return
     */
    public static String encrypt(final String inStr){
        StringBuffer outStr = new StringBuffer();
        byte[] secretkey = SECRET_KEY.getBytes();
        SecretKeySpec key = new SecretKeySpec(secretkey,"AES");
        byte[] secret = new byte[1024];
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            secret = cipher.doFinal(inStr.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
        for(byte b:secret){
            String sr = Integer.toHexString(b+128).toUpperCase();
            outStr.append(sr.length()==2 ? sr : "0"+sr);
        }
        return outStr.toString();
    }

    /**
     * AES 对称解密
     * @param inStr
     * @return
     */
    public static String decrypt(final String inStr){
        byte[] secretkey = SECRET_KEY.getBytes();
        SecretKeySpec key = new SecretKeySpec(secretkey,"AES");
        byte[] secret = new byte[1024];
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] b = new byte[inStr.length()/2];
            for(int i=0; i<inStr.length(); i+=2){
                b[i/2] = (byte)(Integer.valueOf(inStr.substring(i, i+2),16)-128);
            }
            secret = cipher.doFinal(b);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new String(secret);
    }

    private static final String charSet = "UTF-8";

    /**
     * 加密
     *
     * @param _content 需要加密的内容
     * @param _key 加密密码
     * @param md5Key 是否对key进行md5加密
     * @param _iv 加密向量
     * @return 加密后的字符串
     */
    public static String encrypt(String _content, String _key, boolean md5Key, String _iv) {
        try {
            byte[] content = _content.getBytes(charSet);
            byte[] key = _key.getBytes(charSet);
            byte[] iv = _iv.getBytes(charSet);

            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); //"算法/模式/补码方式"
            IvParameterSpec ivps = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            byte [] bytes = cipher.doFinal(content);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * AES 解密
     * @param _content 需要解密的内容
     * @param _key 密码
     * @param md5Key 是否对key进行md5加密
     * @param _iv 加密的向量
     * @return 解密后内容
     */
    public static String decrypt(String _content, String _key, boolean md5Key, String _iv) {
        try {
            if(StringUtil.isBlank(_content) || StringUtil.isBlank(_key) || StringUtil.isBlank(_iv)){
                return "";
            }
            byte[] content = new BASE64Decoder().decodeBuffer(_content);
            byte[] key = _key.getBytes(charSet);
            byte[] iv = _iv.getBytes(charSet);

            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); //"算法/模式/补码方式"
            IvParameterSpec ivps = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            byte [] bytes = cipher.doFinal(content);
            return new String(bytes, charSet);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static final String SECRET_KEY = "^&U2T$E200#A1C%E";

    public static void main(String[] args) {

        //生成salt
//		byte[] salt = Digests.generateSalt(16);
//
//		//将salt转成16进制字符串
//		String saltStr = Encoders.hexEncode(salt);
//		System.err.println("saltStr="+saltStr);
//
//		//给salt加密
//		String saltSec = encrypt(saltStr);
//		System.err.println("saltSec="+saltSec);
//
//		//对密码加密
//		byte[] hashPassword = Digests.sha1("123456".getBytes(), salt, HASH_INTERATIONS);
//		System.out.println(Encoders.hexEncode(hashPassword));
////		System.out.println(Encoders.hexEncode())
//		MessageDigest md = null;
//		try {
//			md = MessageDigest.getInstance("MD5");
//			byte[] md5  = md.digest("123456".getBytes());
//			System.out.println(Encoders.hexEncode(md5));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


    }
}
