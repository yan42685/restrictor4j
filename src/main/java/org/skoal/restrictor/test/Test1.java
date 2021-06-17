package org.skoal.restrictor.test;

import org.skoal.restrictor.restrictor.Restictor;
import org.skoal.restrictor.utils.Lambda;

import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {
    public static void main(String[] args) {
        Restictor restictor = new Restictor();
        AtomicInteger passCount = new AtomicInteger(0);
        AtomicInteger refuseCount = new AtomicInteger(0);
        Lambda.repeat(220, () -> {
            if (restictor.isAvailable("aaaaa", "/test/1")) {
                passCount.incrementAndGet();
            } else {
                refuseCount.incrementAndGet();
            }
        });
        System.out.println(passCount);
        System.out.println(refuseCount);
    }

}
