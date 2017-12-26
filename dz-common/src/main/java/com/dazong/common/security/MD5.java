package com.dazong.common.security;

import java.security.MessageDigest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5 {
    

    private static Logger logger = LoggerFactory.getLogger(MD5.class);
    private static MessageDigest __md;
    private static Lock __md5Lock = new ReentrantLock();
    
    /**
     * 获取字符数据摘要，并以16进制字符串格式返回
     * @param data 目标字符串
     * @param base 进制表示（默认输出16进制）
     * @return
     */
    public static String digest(final String data, final int ... base)
    {
        int localBase = 16; // default 16
        if( data == null ) return null;
        if( base!=null && base.length > 0){
            localBase = base[0];
        }
        try
        {
            byte[] digest;
            __md5Lock.lock();
            try{
                if (__md==null)
                {
                    try{__md = MessageDigest.getInstance("MD5");}
                    catch (Exception e ) {logger.warn("Get MD5 instance fail.",e);return null;}
                }
                
                __md.reset();
                __md.update(data.getBytes("ISO_8859_1"));
                digest=__md.digest();
            } finally {
                __md5Lock.unlock();
            }
            return bytesToString(digest,localBase);
        }
        catch (Exception e)
        {
            logger.warn("Error occurred during Gen MD5.",e);
            return null;
        }
    }
    
    private static String bytesToString(byte[] bytes, int base)
    {
        StringBuffer buf = new StringBuffer();
        for (int i=0;i<bytes.length;i++)
        {
            int bi=0xff&bytes[i];
            int c='0'+(bi/base)%base;
            if (c>'9')
                c= 'a'+(c-'0'-10);
            buf.append((char)c);
            c='0'+bi%base;
            if (c>'9')
                c= 'a'+(c-'0'-10);
            buf.append((char)c);
        }
        return buf.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(MD5.digest("ss"));
    }
    
}

