package com.dazong.common.util;

import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import org.junit.Test;

import java.security.MessageDigest;

import static com.dazong.common.util.codec.DigestUtils.HASH_INTERATIONS;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class DigestUtilTest {

    @Test
    public void Test() {

        System.out.println(">>>>>>>>>>>>>>>>>>> SHA-1/MD5加密  >>>>>>>>>>>>>>>>>>>");
        // 生成salt
        byte[] salt = DigestUtils.generateSalt(16);


        //对密码加密
        byte[] hashPassword = DigestUtils.sha1("123456".getBytes(), salt, HASH_INTERATIONS);
        System.out.println("Hex编码加密: " + EncoderUtils.hexEncode(hashPassword));

//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("MD5");
//            byte[] md5 = md.digest("123456".getBytes());
//            System.out.println("对密码加密: " +EncoderUtils.hexEncode(md5));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
