package org.skoal.restrictor.rule.definition;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将原始结构的配置转化为能高效查询的Hash表
 */
public class ApiRuleMap {
    private final Map<String, ApiRule> map = new HashMap<>();

    public String generateKey(String clientId, String api) {
        return clientId + ":" + api;
    }

    public ApiRule getApiRule(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Set<Map.Entry<String, ApiRule>> getEntrySet() {
        return map.entrySet();
    }

    public ApiRuleMap(@NonNull RawRule rawRule) {
        if (rawRule.getClientRules() == null) {
            return;
        }
        rawRule.getClientRules().forEach(clientRule -> {
            clientRule.getApiRules().forEach(apiRule -> {
                String key = clientRule.getClientId() + ":" + apiRule.getApi();
                map.put(key, apiRule);
            });
        });
    }
}
