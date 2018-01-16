package com.dazong.common.util.codec;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import com.dazong.common.util.StringUtil;
import com.dazong.common.util.reflect.ClassWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 提供基于AES加密、解密的算法工具类
 *
 * @author Sam
 * @version 1.0.0
 */
public class AesUtil {

    protected static Logger logger = LoggerFactory.getLogger(ClassWrapper.class);

    private static final String SECRET_KEY = "^&U2T$E200#A1C%E";
    private static final String CHAE_SET = "UTF-8";
    private static MessageDigest md;
    private static Lock md5Lock = new ReentrantLock();

    private AesUtil() {

    }

    /**
     * MD5 随机数
     *
     * @return
     */
    public static String md5RandomString() {

        String random = String.valueOf(System.currentTimeMillis()) + String.valueOf(new Random().nextLong());
        StringBuilder rs = new StringBuilder();
        try {
            byte[] input = random.getBytes(CHAE_SET);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            byte[] security = md.digest(input);
            for (byte b : security) {
                String sr = Integer.toHexString(b + 128).toUpperCase();
                rs.append(sr.length() == 2 ? sr : "0" + sr);
            }
        } catch (Exception e) {
            logger.info("MD5 随机数异常：{}", e);
        }
        return rs.toString();
    }

    /**
     * 生成32位md5码
     *
     * @param encryptStr
     * @return
     */
    public static String digest32(String encryptStr) {
        MessageDigest md5;
        String encrypt;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes(CHAE_SET));
            StringBuilder hexValue = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            encrypt = hexValue.toString();
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, "digest32");
        }
        return encrypt;
    }

    /**
     * 获取字符数据摘要，并以16进制字符串格式返回
     *
     * @param data 目标字符串
     * @param base 进制表示（默认输出16进制）
     * @return
     */
    public static String digest(final String data, final int... base) {
        // default 16
        int localBase = 16;
        if (data == null) {
            return null;
        }
        if (base != null && base.length > 0) {
            localBase = base[0];
        }
        try {
            byte[] digest;
            md5Lock.lock();
            try {
                if (md == null) {
                    try {
                        md = MessageDigest.getInstance("MD5");
                    } catch (Exception e) {
                        return null;
                    }
                }

                md.reset();
                md.update(data.getBytes("ISO_8859_1"));
                digest = md.digest();
            } finally {
                md5Lock.unlock();
            }
            return bytesToString(digest, localBase);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, "digest");
        }
    }

    private static String bytesToString(byte[] bytes, int base) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int bi = 0xff & bytes[i];
            int c = '0' + (bi / base) % base;
            if (c > '9') {
                c = 'a' + (c - '0' - 10);
            }
            buf.append((char) c);
            c = '0' + bi % base;
            if (c > '9') {
                c = 'a' + (c - '0' - 10);
            }
            buf.append((char) c);
        }
        return buf.toString();
    }

    /**
     * AES 对称加密
     *
     * @param inStr
     * @return
     */
    public static String encrypt(final String inStr) {
        StringBuilder outStr = new StringBuilder();
        byte[] secretkey = SECRET_KEY.getBytes();
        SecretKeySpec key = new SecretKeySpec(secretkey, "AES");
        byte[] secret = new byte[1024];
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            secret = cipher.doFinal(inStr.getBytes());
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, "AES 对称加密");
        }
        for (byte b : secret) {
            String sr = Integer.toHexString(b + 128).toUpperCase();
            outStr.append(sr.length() == 2 ? sr : "0" + sr);
        }
        return outStr.toString();
    }

    /**
     * AES 对称解密
     *
     * @param inStr
     * @return
     */
    public static String decrypt(final String inStr) {
        byte[] secretkey = SECRET_KEY.getBytes();
        SecretKeySpec key = new SecretKeySpec(secretkey, "AES");
        byte[] secret = new byte[1024];
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] b = new byte[inStr.length() / 2];
            for (int i = 0; i < inStr.length(); i += 2) {
                b[i / 2] = (byte) (Integer.valueOf(inStr.substring(i, i + 2), 16) - 128);
            }
            secret = cipher.doFinal(b);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, "AES 对称解密");
        }
        return new String(secret);
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     加密密码
     * @param md5Key  是否对key进行md5加密
     * @param iv      加密向量
     * @return 加密后的字符串
     */
    public static String encrypt(String content, String key, boolean md5Key, String iv) {
        try {
            byte[] tmpContent = content.getBytes(CHAE_SET);
            byte[] tmpKey = key.getBytes(CHAE_SET);
            byte[] tmpIv = iv.getBytes(CHAE_SET);

            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                tmpKey = md.digest(tmpKey);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(tmpKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); //"算法/模式/补码方式"
            IvParameterSpec ivps = new IvParameterSpec(tmpIv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            byte[] bytes = cipher.doFinal(tmpContent);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, "加密");
        }
    }

    /**
     * AesUtil 解密
     *
     * @param content 需要解密的内容
     * @param key     密码
     * @param md5Key  是否对key进行md5加密
     * @param iv      加密的向量
     * @return 解密后内容
     */
    public static String decrypt(String content, String key, boolean md5Key, String iv) {
        try {
            if (StringUtil.isBlank(content) || StringUtil.isBlank(key) || StringUtil.isBlank(iv)) {
                return "";
            }
            byte[] tmpContent = new BASE64Decoder().decodeBuffer(content);
            byte[] tmpKey = key.getBytes(CHAE_SET);
            byte[] tmpIv = iv.getBytes(CHAE_SET);

            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                tmpKey = md.digest(tmpKey);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(tmpKey, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivps = new IvParameterSpec(tmpIv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            byte[] bytes = cipher.doFinal(tmpContent);
            return new String(bytes, CHAE_SET);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, "decrypt");
        }
    }

}
