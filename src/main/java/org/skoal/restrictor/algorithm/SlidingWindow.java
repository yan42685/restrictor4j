package org.skoal.restrictor.algorithm;

import org.skoal.restrictor.rule.definition.ApiRule;

public class SlidingWindow implements LimitingAlgorithm {
    public SlidingWindow(ApiRule apiRule) {

    }

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
