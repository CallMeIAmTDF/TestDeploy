package com.huce.edu.utils;

import java.util.Date;
public class DateUtil {
    public static Date getCurrentDay(){
        Date currentDate = new Date();
        return new Date(currentDate.getTime());
    }
}
