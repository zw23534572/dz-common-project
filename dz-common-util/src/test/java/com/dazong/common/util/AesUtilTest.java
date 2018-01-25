package com.dazong.common.util;

import com.dazong.common.util.codec.AesUtils;
import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import org.junit.Test;



/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class AesUtilTest {

    @Test
    public void Test() {
        System.out.println(">>>>>>>>>>>>>>>>>>> MD5加密  >>>>>>>>>>>>>>>>>>>");
        String str = AesUtils.md5RandomString();
        System.out.println("MD5 随机数：" + str);
        System.out.println("--------------- 字母+数字  ------------");
        System.out.println("生成md5码：" + AesUtils.digest32(str));
        System.out.println("生成md5码：" + AesUtils.digest(str));

        System.out.println("--------------- 字段+数据+中文字符 （字段集UTF8、ISO-8859-1不致导致生成不同加密结果） ------------");
        System.out.println("生成md5码：" + AesUtils.digest32(str + "中文"));
        System.out.println("生成md5码：" + AesUtils.digest(str + "中文"));


        System.out.println("\n\n>>>>>>>>>>>>>>>>>>> AES对称加/解密  >>>>>>>>>>>>>>>>>>>");
        // 生成salt
        byte[] salt = DigestUtils.generateSalt(16);

        //将salt转成16进制字符串
        String saltStr = EncoderUtils.hexEncode(salt);
        System.err.println("生成字符串 saltStr=" + saltStr);

        //给salt加密
        String saltSec = AesUtils.encrypt(saltStr);
        System.err.println("给salt加密 saltSec=" + saltSec);

        String deSaltSec = AesUtils.decrypt(saltSec);
        System.err.println("给salt解密 saltSec=" + deSaltSec);

    }

}
