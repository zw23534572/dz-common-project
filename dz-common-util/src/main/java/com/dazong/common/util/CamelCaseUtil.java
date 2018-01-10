package com.dazong.common.util;

/**
 * 驼峰与下划线之间互转工具类
 *
 * @author zhouwei
 * @date 2016/12/12 0012 下午 16:37
 */
public class CamelCaseUtil {

    private static final char SEPARATOR = '_';

    /**
     * 驼峰转下划线
     *
     * @param s
     * @return
     */
    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 下划线转驼峰，首字母小写
     *
     * @param s
     * @return
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰，首字母大写
     *
     * @param s
     * @return
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static void main(String[] args) {
        System.out.println("abc_c_ddd：" + CamelCaseUtil.toUnderlineName("abc_c_ddd"));
        System.out.println("abc_c_ddd：" + CamelCaseUtil.toCamelCase("abc_c_ddd"));
        System.out.println("abc_c_ddd：" + CamelCaseUtil.toCapitalizeCamelCase("abc_c_ddd"));

        System.out.println("abcCDdd：" + CamelCaseUtil.toUnderlineName("abcCDdd"));
        System.out.println("abcCDdd：" + CamelCaseUtil.toCamelCase("abcCDdd"));
        System.out.println("abcCDdd：" + CamelCaseUtil.toCapitalizeCamelCase("abcCDdd"));
    }

}
