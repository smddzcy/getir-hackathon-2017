package com.wow.wowmeet.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class CalendarUtils {
    final static String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static String calendarToDateString(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.ENGLISH);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

}
