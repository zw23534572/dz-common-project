package com.dazong.common.util;

import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import com.dazong.common.util.codec.RsaUtils;
import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class RsaUtilsTest {

    @Test
    public void Test() {

        System.out.println(">>>>>>>>>>>>>>>>>>> RSA算法的加签、验签、加密、解密  >>>>>>>>>>>>>>>>>>>");
        // 生成salt
        byte[] salt = DigestUtils.generateSalt(16);
        System.err.println("Byte数组，salt=" + salt.toString());
        String character = EncoderUtils.hexEncode(salt);
        String key = "123456ABD%%";
        System.out.println("随机字符串: " + character);

        //RSA私钥加密
        byte[] bytes1 =  RsaUtils.encryptByPrivateKey(salt,key);
        //RSA私钥解密
        byte[] bytes2 = RsaUtils.decryptByPrivateKey(bytes1,key);
        character = EncoderUtils.hexEncode(bytes2);
        System.out.println("私钥加密解密后字符串: " + character);



//        String pk = "123456";
//        String signStr = RsaUtils.sign(salt, pk);
//        System.err.println("私钥签名： " + signStr);
//
//
//        salt = RsaUtils.encryptByPrivateKey(salt, pk);
//        System.err.println("RSA私钥加密： ");
//
//        salt = RsaUtils.decryptByPrivateKey(salt, pk);
//        signStr = RsaUtils.sign(salt, pk);
//        System.err.println("RSA私钥解密： ");


//        RsaUtils.verify(salt, pk, signStr);


    }

}
