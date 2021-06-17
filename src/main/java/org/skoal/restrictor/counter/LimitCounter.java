package org.skoal.restrictor.counter;

public interface LimitCounter {
    boolean tryAcquire();
}
