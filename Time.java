package com.hasbrain.howfastareyou;

import android.provider.BaseColumns;

/**
 * Created by sinhhx on 10/6/16.
 */
public class Time {
public Time(){}
    public static abstract  class time implements BaseColumns{
        public static final String TIMESCORE = "time_score";
        public static final String DATABASE_NAME= "time_record";
        public static final String TABLE_NAME = "time_stored";
        public static final String TAPSCORE ="tap_score";

    }
}