package com.finastra.codescannerapp;

import java.util.Calendar;
import java.util.Date;

public class TypeConverter {


    @androidx.room.TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @androidx.room.TypeConverter
    public Date calendarToDate(Calendar calendar) {
        if (calendar == null) {
            return null;
        } else {
            return calendar.getTime();
        }
    }


    @androidx.room.TypeConverter
    public  Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }


}
