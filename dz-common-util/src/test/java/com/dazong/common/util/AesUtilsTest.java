package com.dazong.common.util;

import com.dazong.common.util.codec.AesUtils;
import com.dazong.common.util.codec.DigestUtils;
import com.dazong.common.util.codec.EncoderUtils;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class AesUtilsTest {

    @Test
    public void Test() {
        System.out.println(">>>>>>>>>>>>>>>>>>> MD5加密  >>>>>>>>>>>>>>>>>>>");
        String str = AesUtils.md5RandomString();
        System.out.println("MD5 随机数：" + str);
        System.out.println("--------------- 字母+数字  ------------");
        System.out.println("生成md5码：" + AesUtils.digest32(str));
        System.out.println("生成md5码：" + AesUtils.digest(str));

        assertThat(AesUtils.digest32(str)).isEqualTo(AesUtils.digest(str));

        System.out.println("--------------- 字段+数据+中文字符 （字段集UTF8、ISO-8859-1不致导致生成不同加密结果） ------------");
        System.out.println("生成md5码：" + AesUtils.digest32(str + "中文"));
        System.out.println("生成md5码：" + AesUtils.digest(str + "中文"));

        assertThat(AesUtils.digest32(AesUtils.digest32(str + "中文"))).isNotEqualTo(AesUtils.digest(str + "中文"));


        System.out.println("\n\n>>>>>>>>>>>>>>>>>>> 加密 - 解密（自选加密规则）  >>>>>>>>>>>>>>>>>>>");
        String content = AesUtils.md5RandomString();
        String key = "123456";
        boolean md5Key = true;
        String iv = "1234567891234567";
        String encrypt = AesUtils.encrypt(content, key, md5Key, iv);
        String decrypt = AesUtils.decrypt(encrypt, key, md5Key, iv);

        System.out.println(" content: " + content + "\n encrypt: " + encrypt + "\n decrypt: " + decrypt);

        //assertThat(encrypt).isEqualTo(decrypt);


    }

}
