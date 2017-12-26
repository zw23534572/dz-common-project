package com.dazong.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 日期解析
 * 
 * 
 */
public class DateFormatUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(DateFormatUtils.class);
    
    public static final String DATE_FORMAT ="yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT ="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_MILLIS_FORMAT ="yyyy-MM-dd HH:mm:ss.SSS";
    private static final String[] DATE_FORMATS = {DATE_TIME_MILLIS_FORMAT,DATE_TIME_FORMAT,DATE_FORMAT};
    
    //相互
    public static final Date parse(String strDate, String format) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(format);
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            logger.warn("解析时间串{}到{}失败！",strDate,format);
        }

        return date;
    }
    public static final Date parse(String strDate) {
        Date date = null;
        for(String format : DATE_FORMATS){
            date = parse(strDate,format);
            if(null != date){
                break;
            }
        }
        return date;
    }
    
    public static final Date parse2Date(String strDate) {
        return parse(strDate,DATE_FORMAT);
    }
    
    public static final Date parse2DateTime(String strDate) {
        return parse(strDate,DATE_TIME_FORMAT);
    }
    public static final Date parse2DateTimeMillis(String strDate) {
        return parse(strDate,DATE_TIME_MILLIS_FORMAT);
    }
    
    // 转换日期及时间至字符串
    public static final String format2Date(Date date) {        
        return format(date,DATE_FORMAT);
    }
    public static final String format2DateTime(Date date) {        
        return format(date,DATE_TIME_FORMAT);
    }
    public static final String format2DateTimeMillis(Date date) {        
        return format(date,DATE_TIME_MILLIS_FORMAT);
    }
    
    public static final String format(Date date,String format ) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    
    
    //时间转换为各种格式
    public static Date convert2Date(Date date){
        return convert(date,DATE_FORMAT);
    }
    
    public static Date convert2DateTime(Date date){
        return convert(date,DATE_TIME_FORMAT);
    }
    public static Date convert2DateTimeMillis(Date date){
        return convert(date,DATE_TIME_MILLIS_FORMAT);
    }
    
    public static Date convert(Date date,String format) {
        SimpleDateFormat inDf = new SimpleDateFormat(format);
        SimpleDateFormat outDf = new SimpleDateFormat(format);
        String reDate = "";
        try {
            reDate = inDf.format(date);
            return outDf.parse(reDate);
        } catch (Exception e) {
            logger.warn("转换时间{}到时间串失败！",date,format);
        }
        return date;
    }


}
