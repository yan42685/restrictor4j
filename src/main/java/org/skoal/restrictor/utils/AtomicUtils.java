package org.skoal.restrictor.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicUtils {
    public static void addInt(AtomicInteger atomicInteger, int delta) {
        Lambda.repeat(delta, atomicInteger::incrementAndGet);
    }

    public static void reduceInt(AtomicInteger atomicInteger, int delta) {
        Lambda.repeat(delta, atomicInteger::decrementAndGet);
    }

    public static void addLong(AtomicLong atomicLong, long delta) {
        Lambda.repeat(delta, atomicLong::incrementAndGet);
    }

    public static void reduceLong(AtomicLong atomicLong, long delta) {
        Lambda.repeat(delta, atomicLong::decrementAndGet);
    }
}
