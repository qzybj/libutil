package com.brady.libutil.data;

import com.brady.libutil.CLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Cuckoo
 * @date 2017-03-08
 * @description
 *      和时间相关的工具类
 */

public class DateUtil {

    /** 格式   12:01*/
    public static String FORMAT_HM = "HH:mm";

    /** 格式  12:01:23 */
    public static String FORMAT_HMS = "HH:mm:ss";

    /** 格式  12:01:23 */
    public static String FORMAT_HMSZ = "hhmmsszzz";

    /** 格式  20101201 */
    public static String FORMAT_YYYYMMDD = "yyyyMMdd";

    /** 格式  2010-12-01 */
    public static String FORMAT_YMD = "yyyy-MM-dd";
    /** 格式  2010年12月01日 */
    public static String FORMAT_YMD_CHINA = "yyyy年MM月dd日";

    /** 格式  2010-12-01 23:15:06*/
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**  格式  20101201231506 */
    public static String FORMAT_YMDHMS_ALLNUMBER="yyyyMMddHHmmss";

    /**1秒的毫秒数*/
    public static final long SECOND = 1000L;
    /**1分钟的毫秒数*/
    public static final long MINUTE = 60*SECOND;

    /**一天的毫秒数*/
    private static final long DAY_MS = 24*60*MINUTE;




    /**
     * 获取当前时间，格式为12:01:23
     * @return
     */
    public static String getCurrentTime(){
        return getCurrentTime(FORMAT_HMS);
    }

    /**
     * 获取当前日期，格式为YYYY-MM-DD
     * @return
     */
    public static String getCurrentDate(){
        return getCurrentTime(FORMAT_YMD);
    }

    /**
     * 获取当前时间，格式为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDateTime(){
        return getCurrentTime(FORMAT_YMDHMS);
    }

    /**
     * 获取当前时间，格式为可选
     * @param pattern 指定输出格式，示例：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String pattern){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    /**
     * 转换日期字符串为日期
     * @param strDate 日期字符串 示例:2017-01-01
     * @param pattern 日期格式 示例:yyyy-MM-dd
     * @return  提取字符串日期
     */
    public static Date str2Date(String strDate, String pattern) {
        if (StringUtil.isEmpty(strDate)) {
            return null;
        }
        if (StringUtil.isEmpty(pattern)) {
            pattern = FORMAT_YMDHMS;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.getDefault());
            date = sdf.parse(strDate);
        } catch (Exception e) {
            CLog.e(e);
        }
        return date;
    }


    /**
     * 转换Date日期为指定格式的字符串
     * @param d
     * @param format
     * @return
     */
    public static String date2Str(Date d, String format) {
        if (d == null) {
            return null;
        }
        if (StringUtil.isEmpty(format)) {
            format = FORMAT_YMDHMS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.getDefault());
        String s = sdf.format(d);
        return s;
    }

    /**
     * 转换字符串日期为Calendar
     * @param str
     * @param format
     * @return
     */
    public static Calendar str2Calendar(String str, String format) {
        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

}