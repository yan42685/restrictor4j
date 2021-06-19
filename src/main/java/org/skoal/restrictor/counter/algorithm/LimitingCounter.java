package org.skoal.restrictor.counter.algorithm;

public interface LimitingCounter {
    boolean tryAcquire();
}
