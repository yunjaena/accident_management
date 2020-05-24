package com.yunjaena.accident_management.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd_HH:mm:ss");
        Date time = new Date();
        return format.format(time);
    }
}
