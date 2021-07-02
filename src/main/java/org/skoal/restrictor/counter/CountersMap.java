package org.skoal.restrictor.counter;

import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.enums.RuleSourceType;
import org.skoal.restrictor.counter.algorithm.*;
import org.skoal.restrictor.rule.RawRuleFactory;
import org.skoal.restrictor.rule.definition.ApiRule;
import org.skoal.restrictor.rule.definition.RawRule;

import java.util.HashMap;
import java.util.Map;

/**
 * 将原始结构的限流规则转化为能高效查询的Hash表
 */
public class CountersMap {
    private final LimitingAlgorithmType algorithmType;
    private final Map<String, LimitingCounter> map = new HashMap<>(256);

    public CountersMap(LimitingAlgorithmType algorithmType, RuleSourceType sourceType) {
        this.algorithmType = algorithmType;
        RawRule rawRule = RawRuleFactory.create(sourceType);
        fillMap(rawRule);
    }

    public LimitingCounter getCounter(String clientId, String api) {
        String key = generateKey(clientId, api);
        return map.get(key);
    }

    private void fillMap(RawRule rawRule) {
        if (rawRule.getClientRules() == null) {
            return;
        }
        rawRule.getClientRules().forEach(clientRule -> {
            clientRule.getApiRules().forEach(apiRule -> {
                String key = generateKey(clientRule.getClientId(), apiRule.getApi());
                map.put(key, getCounter(apiRule));
            });
        });
    }

    private String generateKey(String clientId, String api) {
        return clientId + ":" + api;
    }

    private LimitingCounter getCounter(ApiRule apiRule) {
        switch (this.algorithmType) {
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
