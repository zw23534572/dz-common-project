package com.dazong.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.split;

/**
 * String工具类
 * 提供：字符串判断、转换
 *
 * @author: zisong.wang
 * @date: 2018/1/10
 */
public class StringUtil {

    /**
     * 逗号
     */
    public static final String SEP_COMMA = ",";

    private StringUtil() {

    }

    /**
     * 首字母变小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 首字母变大写, 利用ASCII编码进行转换
     *
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        char[] charArray = str.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * 判断是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * 判断是否空白
     *
     * @param str
     * @return
     */
    public static boolean isBlank(final String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否不是空白
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 判断多个字符串全部是否为空
     *
     * @param strings
     * @return
     */
    public static boolean isAllEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串其中任意一个是否为空
     *
     * @param strings
     * @return
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkValue为 null 或者为 "" 时返回 defaultValue
     *
     * @param checkValue
     * @param defaultValue
     * @return
     */
    public static String isEmpty(String checkValue, String defaultValue) {
        return isEmpty(checkValue) ? defaultValue : checkValue;
    }

    /**
     * 字符串不为 null 而且不为 "" 并且等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEmptyAndEquelsOther(String str, String other) {
        if (isEmpty(str)) {
            return false;
        }
        return str.equals(other);
    }

    /**
     * 字符串不为 null 而且不为 "" 并且不等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEmptyAndNotEquelsOther(String str, String... other) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < other.length; i++) {
            if (str.equals(other[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串不等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEquelsOther(String str, String... other) {
        for (int i = 0; i < other.length; i++) {
            if (other[i].equals(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不为空
     *
     * @param strings
     * @return
     */
    public static boolean isNotEmpty(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || "".equals(str.trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较字符相等
     *
     * @param value
     * @param equals
     * @return
     */
    public static boolean equals(String value, String equals) {
        if (isAllEmpty(value, equals)) {
            return true;
        }
        return value.equals(equals);
    }

    /**
     * 比较字符串不相等
     *
     * @param value
     * @param equals
     * @return
     */
    public static boolean isNotEquals(String value, String equals) {
        return !equals(value, equals);
    }


    /**
     * 根据一个正则式，将字符串拆分成数组，空元素将被忽略
     *
     * @param s     字符串
     * @param regex 正则式
     * @return 字符串数组
     */
    public static String[] splitIgnoreBlank(String s, String regex) {
        return isEmpty(s) ?
                null : CollectionUtil.removeIfEmpty(split(s, regex));
    }

    public static String[] splitIgnoreBlank(String s) {
        return splitIgnoreBlank(s, StringUtil.SEP_COMMA);
    }

    /**
     * 将一个数组的部分元素转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param offset 开始元素的下标
     * @param len    元素数量
     * @param c      分隔符
     * @param objs   数组
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(int offset,
                                           int len,
                                           Object c,
                                           T[] objs) {
        StringBuilder sb = new StringBuilder();
        if (null == objs || len < 0 || 0 == objs.length) {
            return sb;
        }

        if (offset < objs.length) {
            sb.append(objs[offset]);
            for (int i = 1; i < len && i + offset < objs.length; i++) {
                sb.append(c).append(objs[i + offset]);
            }
        }
        return sb;
    }

    /**
     * 将一个集合转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c    分隔符
     * @param coll 集合
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, Collection<T> coll) {
        StringBuilder sb = new StringBuilder();
        if (null == coll || coll.isEmpty()) {
            return sb;
        }
        return concat(c, coll.iterator());
    }

    /**
     * 将一个迭代器转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c  分隔符
     * @param it 集合
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, Iterator<T> it) {
        StringBuilder sb = new StringBuilder();
        if (it == null || !it.hasNext()) {
            return sb;
        }
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(c).append(it.next());
        }
        return sb;
    }

    /**
     * 分割字符串为int集合
     *
     * @param str 要分割的字符串
     * @param sep 连接符
     * @return
     */
    public static List<Integer> splitToIntList(String str, String sep) {
        return splitToNumberList(str, sep, Integer.class);
    }

    /**
     * 分割字符串为数字集合
     *
     * @param str      要分割的字符串
     * @param sep      分割符
     * @param numClass 数字类型，目前支持:Integer,Long,Double,BigDecimal,Short,Byte
     * @return
     */
    public static <T extends Number> List<T> splitToNumberList(String str, String sep, Class<T> numClass) {
        String[] strs = splitIgnoreBlank(str, sep);
        if (strs != null) {
            List<T> nums = CollectionUtil.arrayList();
            for (String s : strs) {
                if (numClass == Integer.class) {
                    nums.add((T) Integer.valueOf(s));
                } else if (numClass == Long.class) {
                    nums.add((T) Long.valueOf(s));
                } else if (numClass == Double.class) {
                    nums.add((T) Double.valueOf(s));
                } else if (numClass == Short.class) {
                    nums.add((T) Short.valueOf(s));
                } else if (numClass == Byte.class) {
                    nums.add((T) Byte.valueOf(s));
                } else if (numClass == BigDecimal.class) {
                    nums.add((T) new BigDecimal(s));
                }
            }
            return nums;
        }
        return new ArrayList<>();
    }


}
