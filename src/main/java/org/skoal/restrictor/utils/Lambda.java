package org.skoal.restrictor.utils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 提供函数式工具
 */
public class Lambda {
    public static void repeat(int times, Runnable method) {
        for (int i = 0; i < times; i++) {
            method.run();
        }
    }

    public static void repeat(long times, Runnable method) {
        for (int i = 0; i < times; i++) {
            method.run();
        }
    }

    public static <T> Optional<T> findFirst(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).findFirst();
    }
}
