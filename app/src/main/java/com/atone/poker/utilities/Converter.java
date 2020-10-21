package com.atone.poker.utilities;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Converter {

    public static String toFormattedTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.JAPAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yy/dd/MM HH:mm", cal).toString();
        return date;
    }
}
