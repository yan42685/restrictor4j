package org.skoal.restrictor.counter;

import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.enums.RuleSourceType;
import org.skoal.restrictor.counter.algorithm.LimitingCounter;
import org.skoal.restrictor.rule.RawRuleFactory;
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
                LimitingCounter limitingCounter = CounterFactory.create(this.algorithmType, apiRule);
                map.put(key, limitingCounter);
            });
        });
    }

    private String generateKey(String clientId, String api) {
        return clientId + ":" + api;
    }
}
