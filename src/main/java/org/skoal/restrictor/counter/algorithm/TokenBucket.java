package org.skoal.restrictor.counter.algorithm;

import org.skoal.restrictor.rule.definition.ApiRule;

public class TokenBucket implements LimitingCounter {
    public TokenBucket(ApiRule apiRule) {

    }

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
