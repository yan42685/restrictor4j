package org.skoal.restrictor.counter;

import lombok.NonNull;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.counter.algorithm.*;
import org.skoal.restrictor.rule.definition.ApiRule;
import org.skoal.restrictor.rule.definition.RawRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将原始结构的配置转化为能高效查询的Hash表
 */
public class CountersMap {
    private final LimitingAlgorithmType algorithmType;
    private final Map<String, LimitingCounter> map = new HashMap<>(256);

    public CountersMap(@NonNull RawRule rawRule, LimitingAlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
        fillMap(rawRule);
    }

    public String generateKey(String clientId, String api) {
        return clientId + ":" + api;
    }

    public LimitingCounter getCounter(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Set<Map.Entry<String, LimitingCounter>> getEntrySet() {
        return map.entrySet();
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

    private LimitingCounter getCounter(ApiRule apiRule) {
        switch (this.algorithmType) {
            case FIxED_WINDOW:
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
