package org.skoal.restrictor.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TimeUtils {
    /**
     * @param timeUnit 仅支持 HOURS,MINUTES,SECONDS,MILLISECONDS
     */
    public static long toMillis(long period, TimeUnit timeUnit) {
        switch (timeUnit) {
            case HOURS:
                return period * 3600000;
            case MINUTES:
                return period * 60000;
            case SECONDS:
                return period * 1000;
            case MILLISECONDS:
                return period;
            default:
                throw new RuntimeException("不支持该时间单位: " + timeUnit);
        }
    }
}
