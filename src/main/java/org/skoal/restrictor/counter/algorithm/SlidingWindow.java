package org.skoal.restrictor.counter.algorithm;

import org.skoal.restrictor.rule.definition.ApiRule;

public class SlidingWindow implements LimitingCounter {
    public SlidingWindow(ApiRule apiRule) {

    }

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
