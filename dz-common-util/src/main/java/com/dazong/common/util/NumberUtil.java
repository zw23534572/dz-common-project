package com.dazong.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Number数字类型工具类
 * 提供：精度加减乘除
 *
 * @author zisong.wang
 * @date 2018/01/09
 */
public class NumberUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 错误提醒
     */
    private static String scaleStr = "小数位数必须为大于等于0的正整数";

    private NumberUtil(){

    }

    /**
     * 格式化double类型
     *
     * @param d
     * @return
     */
    public static String format(double d) {
        DecimalFormat defaultFormat = new DecimalFormat("0.00");
        return defaultFormat.format(d);
    }

    /**
     * 格式化long类型
     *
     * @param i
     * @return
     */
    public static String format(long i) {
        DecimalFormat defaultFormat = new DecimalFormat("0.00");
        return defaultFormat.format(i);
    }

    /**
     * 格式化double类型，自定义格式，如：3.1415926 "00.000" = 03.142
     *
     * @param d
     * @param format
     * @return
     */
    public static String format(double d, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(d);
    }

    /**
     * 格式化long类型，自定义格式，如：3.1415926 "#.##%" = 314.16%
     *
     * @param l
     * @param format
     * @return
     */
    public static String format(long l, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(l);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(scaleStr);
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(scaleStr);
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 对eval对象进行精度和小数位数控制
     *
     * @param object object
     * @param scale  scale
     * @return double
     */
    public static double roundForEval(Object object, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(scaleStr);
        }
        return round(Double.valueOf(object.toString()), scale);
    }

};