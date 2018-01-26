package com.dazong.common.util;

import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import com.dazong.common.util.codec.RsaUtils;
import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class RsaUtilTest {

    @Test
    public void Test() {

        System.out.println(">>>>>>>>>>>>>>>>>>> RSA算法的加签、验签、加密、解密  >>>>>>>>>>>>>>>>>>>");
        // 生成salt
        byte[] salt = DigestUtils.generateSalt(16);
        System.err.println("Byte数组，salt=" + salt.toString());
        EncoderUtils.hexEncode(salt);

        String pk = "123456";
        String signStr = RsaUtils.sign(salt, pk);
        System.err.println("私钥签名： " + signStr);


        salt = RsaUtils.encryptByPrivateKey(salt, pk);
        System.err.println("RSA私钥加密： ");

        salt = RsaUtils.decryptByPrivateKey(salt, pk);
        signStr = RsaUtils.sign(salt, pk);
        System.err.println("RSA私钥解密： ");


        RsaUtils.verify(salt, pk, signStr);


    }

}
