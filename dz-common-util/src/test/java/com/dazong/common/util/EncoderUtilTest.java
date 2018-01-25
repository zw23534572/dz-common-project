package com.dazong.common.util;

import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import org.junit.Test;

import static com.dazong.common.util.codec.DigestUtils.HASH_INTERATIONS;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class EncoderUtilTest {

    @Test
    public void Test() {

        System.out.println(">>>>>>>>>>>>>>>>>>> 编码加码  >>>>>>>>>>>>>>>>>>>");
        // 生成salt
        byte[] salt = DigestUtils.generateSalt(16);
        System.err.println("Byte数组，salt=" + salt.toString());

        //将salt转成16进制字符串
        String saltStr = EncoderUtils.hexEncode(salt);
        System.err.println("Hex编码： saltStr=" + saltStr);

        // 将字符串解码
        salt = EncoderUtils.hexDecode(saltStr);
        saltStr = EncoderUtils.hexEncode(salt);
        System.err.println("Hex解码： saltStr=" + saltStr);

    }

}
