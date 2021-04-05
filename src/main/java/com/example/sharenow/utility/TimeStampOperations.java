package com.example.sharenow.utility;

import java.sql.Timestamp;

public class TimeStampOperations {
    public static Timestamp calculateTimeStamp(Timestamp before,int noOfHours) {
        Timestamp toBeDeletedTime = new Timestamp(before.getTime() + 1000*60*60*noOfHours);
        return toBeDeletedTime;
    }

    // this function return true if current time is less than given time, i.e. the user has more time available
    public static boolean compareWithCurrentTimeStamp(Timestamp t1) {
        if( (String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()).getTime()))
                .compareTo(String.valueOf(t1.getTime())) < 0 )
            return true;
        return false;
    }
}
