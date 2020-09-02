package com.hpw.test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeTest {
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(System.currentTimeMillis());
        System.out.println(zonedDateTime.plusDays(29).toInstant().toEpochMilli());
        Instant instant = zonedDateTime.toInstant();

        ZonedDateTime zonedDateTime1 = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime1);
    }
}
