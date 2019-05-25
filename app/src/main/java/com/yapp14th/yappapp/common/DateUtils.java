package com.yapp14th.yappapp.common;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date reduceHourAndMin(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        return cal.getTime();
    }

}
