package com.calm.sqlrunner.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类.
 *
 * @author mixue on 2019/9/25 15:23.
 */
@Slf4j
@UtilityClass
public class DateKit {

    /**
     * 获取当前时间的字符串.
     *
     * @return 字符串
     */
    public static String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取指定时间与当前时间的时间差.
     *
     * @param date 指定时间
     * @return long
     */
    public static long timeInterval(Date date) {
        return date == null ? 0 : Math.abs(date.getTime() - System.currentTimeMillis());
    }

    /**
     * 获取某日期任意时间， time="00:00:00"表示当前日期的零点零时零分.
     *
     * @param date 日期
     * @param time 时分秒
     * @return 某日期任意时间
     */
    public static Date getRandomDate(Date date, String time) {
        String today = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(date) + " " + time;
        try {
            return DateUtils.parseDate(today, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            log.warn("日期 【{}】 转换错误.", today, e);
            return new Date();
        }
    }

}
