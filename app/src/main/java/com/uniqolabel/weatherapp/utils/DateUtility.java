package com.uniqolabel.weatherapp.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtility {


    private DateUtility(){
        throw new AssertionError();
    }

    public static String getDateFromTimestamp(Long timeStamp) {
        Timestamp stamp = new Timestamp(timeStamp);
        Date date = new Date(stamp.getTime());
        String formatRequired = "dd MMM, yyyy";
        SimpleDateFormat requiredDateFormat = new SimpleDateFormat(formatRequired, Locale.ENGLISH);
        return requiredDateFormat.format(date);
    }
    public static String getTimeFromTimestamp(Long timeStamp) {
        Timestamp stamp = new Timestamp(timeStamp);
        Date date = new Date(stamp.getTime());
        String formatRequired = "hh:mm";
        SimpleDateFormat requiredDateFormat = new SimpleDateFormat(formatRequired, Locale.ENGLISH);
        return requiredDateFormat.format(date);
    }

}
