package com.vitalykhan.webapps.utils;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
