package com.dazong.common.util;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MD5加密工具类
 *
 * @author zisong.wang
 * @date 2018/01/09
 */
public class Md5Util {

    protected static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    private static MessageDigest md;
    private static Lock md5Lock = new ReentrantLock();

    private Md5Util(){

    }

    public static String digest32(String encryptStr) {
        MessageDigest md5;
        String encrypt;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes("utf-8"));
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
            throw new PlatformException(e, CommonStatus.FAIL,"digest32");
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
                        logger.warn("Get Md5Util instance fail.", e);
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
            logger.warn("Error occurred during Gen Md5Util.", e);
            return null;
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

}

