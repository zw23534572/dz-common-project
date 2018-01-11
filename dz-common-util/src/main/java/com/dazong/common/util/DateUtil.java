package com.dazong.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 *
 * @author zisong.wang
 * @date 2018/01/09
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String FORMAT_DATE_TIME_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_MONTH = "yyyy-MM";
    public static final String FORMAT_TIME = "HH:mm:ss";

    /**
     * 获取当月，yyyy-MM
     *
     * @return
     */
    public static String getCurrentMonthAsString() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat(FORMAT_MONTH);
        return monthFormat.format(c.getTime());
    }

    /**
     * 获取当天，yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDayAsString() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取当前时间  yyyy-MM-dd HH:mm:ss.SSS
     *
     * @return
     */
    public static String getCurrentMilliAsString() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateTimeFormatMilli = new SimpleDateFormat(FORMAT_DATE_TIME_MILLI);
        return dateTimeFormatMilli.format(c.getTime());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentDay() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * 格式化时间,Date转String
     *
     * @param date
     * @param format
     * @return String
     */
    public static String dateToStr(Date date, String format) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat(format);
        return myFormatter.format(date);
    }

    /**
     * 格式化字符串，String转Date
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date strToDate(String date, String format) {
        DateFormat myFormatter = new SimpleDateFormat(format);
        try {
            return myFormatter.parse(date);
        } catch (ParseException e) {
            logger.error("格式化字符串，String转Date出错，{}", e);
        }
        return null;
    }

    /**
     * 格式化时间 yyyy-MM-dd
     *
     * @param date
     * @return String
     */
    public static String formatYYYYMMDD(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return dateFormat.format(date);
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return String
     */
    public static String formatYYYYMMDDHHMMSS(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        return dateTimeFormat.format(date);
    }

    /**
     * 根据日期获取当前计算机时间
     *
     * @param dateFormatString
     * @return
     */
    public static Long getLongDate(String dateFormatString) {
        Date resultDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        try {
            if (dateFormatString != null && !"".equals(dateFormatString)) {
                resultDate = dateFormat.parse(dateFormatString);
            }
            return resultDate.getTime();
        } catch (ParseException e) {
            logger.error("根据日期获取当前计算机时间出错, {}", e);
        }
        return null;
    }


    /**
     * 得到本月的第一天
     *
     * @return
     */
    public static String getMonthFirstDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取两个时间的时间查 如30
     */
    public static int getDatePoor(Date beginDate, Date endDate) {
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - beginDate.getTime();
        int lastMin = (int) (diff / 1000 / 60);
        return lastMin;
    }

    /**
     * 获取当前日期所属周的年份
     *
     * @param d1
     * @return
     */
    public static int getYearOfWeek(Date d1) {
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        return c.getWeekYear();
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDaySub(Date beginDate, Date endDate) {
        long day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
            String sdate = format.format(Calendar.getInstance().getTime());

            if (beginDate == null) {
                beginDate = format.parse(sdate);
            }
            if (endDate == null) {
                endDate = format.parse(sdate);
            }
            day = (endDate.getTime() - beginDate.getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            logger.info("计算两个日期相差的天数出错, {}" + e);
            return -1;
        }
        return Integer.parseInt(String.valueOf(day));
    }

    /**
     * 判断字符串的日期是否合法
     */
    public static boolean checkStrForFormat(String dateStr, String formatStr) {
        Format f = new SimpleDateFormat(formatStr);
        Date d = null;
        try {
            d = (Date) f.parseObject(dateStr);
        } catch (ParseException e) {
            logger.error("", e);
        }
        String tmp = f.format(d);
        return tmp.equals(dateStr);
    }

    /**
     * 时间比较
     *
     * @param d1
     * @param times
     * @return
     */
    public static boolean compareTime(Date d1, String times) {
        boolean flag = false;
        String str1 = formatYYYYMMDDHHMMSS(d1).split(" ")[1];
        int compare = str1.compareTo(times.trim());
        if (compare <= 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据日期，返回是当前年的第几周
     * 格式：yyyy-ww
     *
     * @param date
     * @return
     */
    public static String getYearWeekNoByDate(Date date) {
        String dimWeek = "";
        int weekNo = getWeekOfYear(date);
        int year = getYearOfWeek(date);
        if (weekNo < 10) {
            dimWeek = String.valueOf(year) + "0" + String.valueOf(weekNo);
        } else {
            dimWeek = String.valueOf(year) + String.valueOf(weekNo);
        }
        return dimWeek;
    }

    /**
     * 获取当前时间所在年的周数
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前时间所在年的最大周数
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    /**
     * 获取某年的第几周的开始日期
     *
     * @param year
     * @param week
     * @return
     */
    public static String getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年的第几周的结束日期
     *
     * @param year
     * @param week
     * @return
     */
    public static String getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 获取当前时间所在周的开始日期
     *
     * @param date
     * @return
     */
    public static String getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        // Monday
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取当前时间所在周的结束日期
     *
     * @param date
     * @return
     */
    public static String getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        // Sunday
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取当前时间所在年的月数
     *
     * @param date
     * @return
     */
    public static int getMonthOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return dateFormat.format(cal.getTime());
    }

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        System.out.println(DateUtil.getCurrentDayAsString());
    }

}
