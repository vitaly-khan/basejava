package com.vitalykhan.webapps;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class SomeTests {
    public static void main(String[] args) {

        Date date = new Date();
        System.out.println(date);
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = new SimpleTimeZone(3*3_600*1_000, "Moscow");
        LocalDate ld = LocalDate.of(2018, 5, 21);
        System.out.println(ld.getDayOfWeek());
    }
}

