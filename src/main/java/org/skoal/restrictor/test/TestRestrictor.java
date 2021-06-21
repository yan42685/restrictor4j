package org.skoal.restrictor.test;

import org.skoal.restrictor.restrictor.MemoryRestictor;
import org.skoal.restrictor.utils.Lambda;

import java.util.concurrent.atomic.AtomicInteger;

public class TestRestrictor {
    public static void main(String[] args) {
        MemoryRestictor restictor = new MemoryRestictor();
        AtomicInteger passCount = new AtomicInteger(0);
        AtomicInteger refuseCount = new AtomicInteger(0);
        Lambda.repeat(220, () -> {
            if (restictor.tryAcquire("aaaaa", "/test/1")) {
                passCount.incrementAndGet();
            } else {
                refuseCount.incrementAndGet();
            }
        });
        System.out.println(passCount);
        System.out.println(refuseCount);
    }

}
