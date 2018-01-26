package com.dazong.common.util;

import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import com.dazong.common.util.codec.RsaUtils;
import com.dazong.common.util.codec.SignatureUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class SignatureUtilsTest {

    @Test
    public void Test() {
        System.out.println(">>>>>>>>>>>>>>>>>>> 参数签名工具类 >>>>>>>>>>>>>>>>>>>");
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "test");
        String privateKey = "1";
        String publicKey = "1";
        String charset = "1";
        String sign = "1";

        String str = SignatureUtils.rsaSign(map, privateKey, charset);
        System.out.println("使用RSA算法加签: " + str);

        boolean flag = SignatureUtils.rsaVerify(map, sign, publicKey, charset);
        System.out.println("使用RSA算法验签: " + flag);

    }

}
