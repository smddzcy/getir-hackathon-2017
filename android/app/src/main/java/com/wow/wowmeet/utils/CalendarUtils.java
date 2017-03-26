package com.wow.wowmeet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class CalendarUtils {
    final static String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    final static String PRETTY_FORMAT = "dd.MM.yyyy HH:mm";

    public static String calendarToDateString(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.ENGLISH);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public static String dateToPrettyDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(PRETTY_FORMAT, Locale.ENGLISH);
        return sdf.format(date);
    }

    public static Date stringToDate(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.ENGLISH);
        return sdf.parse(time);
    }

    public static String getStartEndDateString(String startTime, String endTime) throws ParseException {
        Date dateStart = CalendarUtils.stringToDate(startTime);
        Date dateEnd = CalendarUtils.stringToDate(endTime);
        String startString = CalendarUtils.dateToPrettyDateString(dateStart);
        String endString = CalendarUtils.dateToPrettyDateString(dateEnd);

        return startString + " - " + endString;
    }

}
