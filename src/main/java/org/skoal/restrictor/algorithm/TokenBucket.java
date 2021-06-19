package org.skoal.restrictor.algorithm;

import org.skoal.restrictor.rule.definition.ApiRule;

public class TokenBucket implements LimitingAlgorithm {
    public TokenBucket(ApiRule apiRule) {

    }

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
