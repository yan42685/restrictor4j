package org.skoal.restrictor.utils;

import lombok.SneakyThrows;

public class ThreadUtils {
    @SneakyThrows
    public static void sleepSeconds(long seconds) {
        Thread.sleep(1000 * seconds);
    }

    @SneakyThrows
    public static void sleepMillis(long millis) {
        Thread.sleep(millis);
    }
}
