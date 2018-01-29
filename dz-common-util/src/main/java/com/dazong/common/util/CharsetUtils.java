package com.dazong.common.util;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 编码工具类
 *
 * @author Sam
 */
public class CharsetUtils {

    private CharsetUtils() {

    }

    private static final String CODING_ISO_8859_1 = "ISO-8859-1";
    private static final String CODING_GBK = "GBK";
    private static final String CODING_GB2312 = "GB2312";
    private static final String CODING_UTF8 = "UTF-8";

    public static String defaultEncoding() {
        return Charset.defaultCharset().name();
    }

    public static String encode(CharSequence str, String encoding) {
        Assert.notEmpty(str, "the encode string can not be null!");
        if (StringUtils.isNotEmpty(str)) {
            try {
                return new String(str.toString().getBytes(), encoding);
            } catch (UnsupportedEncodingException e) {
                throw new PlatformException(e, CommonStatus.FAIL, "encode");
            }
        }
        return str.toString();
    }

    public static String utf8(CharSequence str) {
        return encode(str, CODING_UTF8);
    }

    public static String iso8859(CharSequence str) {
        return encode(str, CODING_ISO_8859_1);
    }

    public static String gbk(CharSequence str) {
        return encode(str, CODING_GBK);
    }

    public static String gb2312(CharSequence str) {
        return encode(str, CODING_GB2312);
    }

    public String get(String str) {
        if (Strings.isNotEmpty(str)) {
            try {
                return new String(str.getBytes(), this.toString());
            } catch (UnsupportedEncodingException e) {
                throw new PlatformException(e, CommonStatus.FAIL, "get");
            }
        }
        return str;
    }


}
