package org.skoal.restrictor.utils;

import org.skoal.restrictor.basic.KnownException;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
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
                throw new KnownException("不支持该时间单位: " + timeUnit);
        }
    }
}
