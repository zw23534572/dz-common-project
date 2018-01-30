package com.dazong.common.util.codec;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import com.dazong.common.util.StringsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 参数签名工具类
 * @author Sam
 * @version 1.0.0
 */
public abstract class SignatureUtils {


    /**
     * 签名前要对参数进行排序
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringsUtils.isNotBlank(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * 使用RSA算法加签
     * @param params 加签的参数
     * @param privateKey 私钥
     * @param charset 字符集编码
     * @return
     */
    public static String rsaSign(Map<String, String> params, String privateKey, String charset)  {
        String signContent  = getSignContent(params);
        try {
            byte[] signData = StringsUtils.isBlank(charset)?signContent.getBytes():signContent.getBytes(charset);
            return RsaUtils.sign(signData,privateKey);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL,"使用RSA算法加签rsaSign");
        }
    }

    /**
     * 使用RSA算法验签
     * @param params 要验签的参数
     * @param sign 签名
     * @param publicKey 公钥
     * @param charset
     * @return
     */
    public static boolean rsaVerify(Map<String,String> params, String sign, String publicKey, String charset) {
        String signContent  = getSignContent(params);
        try {
            byte[] signData = StringsUtils.isBlank(charset)?signContent.getBytes():signContent.getBytes(charset);
            return RsaUtils.verify(signData,publicKey,sign);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL,"使用RSA算法验签rsaVerify");
        }
    }

}
