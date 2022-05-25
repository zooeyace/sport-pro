package com.zyy.sport.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    static String PATTERN_DATE = "yyyy-MM-dd";
    static String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";


    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
        return sdf.format(new Date());
    }

    public static String getDateWithTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_TIME);
        return sdf.format(new Date());
    }

    public static Date StringToTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
}
