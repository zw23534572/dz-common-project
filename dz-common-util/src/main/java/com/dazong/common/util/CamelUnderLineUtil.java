package com.dazong.common.util;

/**
 * 驼峰与下划线之间互转工具类
 *
 * @author zisong.wang
 * @date 2018/01/11
 */
public class CamelUnderLineUtil {

    private static String UNDER_LINE = "_";

    private CamelUnderLineUtil() {
    }

    /**
     * 下划线转驼峰法
     *
     * @param line  源字符串
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line) {
        StringBuilder result = new StringBuilder();
        if (line == null || line.isEmpty()) {
            return "";
        } else if (!line.contains(UNDER_LINE)) {
            // 不含下划线，仅将首字母小写
            return line.substring(0, 1).toLowerCase() + line.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = line.split(UNDER_LINE);
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        StringBuilder result = new StringBuilder();
        if (line != null && line.length() > 0) {
            // 将第一个字符处理成小写
            result.append(line.substring(0, 1).toLowerCase());
            // 循环处理其余字符
            for (int i = 1; i < line.length(); i++) {
                String s = line.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (Character.isUpperCase(s.charAt(0)) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接拼接
                result.append(s);
            }
        }
        return result.toString();
    }

}
