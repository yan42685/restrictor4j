package org.skoal.restrictor.counter.algorithm;

import org.skoal.restrictor.rule.definition.ApiRule;

public class LeakyBucket implements LimitingCounter {
    public LeakyBucket(ApiRule apiRule) {

    }

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
