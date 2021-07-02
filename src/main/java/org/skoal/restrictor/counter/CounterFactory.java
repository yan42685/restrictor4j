package org.skoal.restrictor.counter;

import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.counter.algorithm.*;
import org.skoal.restrictor.rule.definition.ApiRule;

public class CounterFactory {
    public static LimitingCounter create(LimitingAlgorithmType algorithmType, ApiRule apiRule) {
        switch (algorithmType) {
            case FIXED_WINDOW:
                return new FixedWindow(apiRule);
            case SLIDING_WINDOW:
                return new SlidingWindow(apiRule);
            case LEAKY_BUCKET:
                return new LeakyBucket(apiRule);
            case TOKEN_BUCKET:
                return new TokenBucket(apiRule);
            default:
                throw new RuntimeException("不支持的限流算法: " + algorithmType);
        }
    }
}
