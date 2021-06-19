package org.skoal.restrictor.algorithm;

import org.skoal.restrictor.rule.definition.ApiRule;

public class LeakyBucket implements LimitingAlgorithm {
    public LeakyBucket(ApiRule apiRule) {

    }

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
