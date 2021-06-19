package org.skoal.restrictor.rule.definition;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class HashMapRefinedRule implements RefinedRule {
    private final Map<String, ApiRule> map = new HashMap<>();

    @Override
    public ApiRule getApiRule(String clientId, String api) {
        return map.get(clientId + ":" + api);
    }

    public HashMapRefinedRule(@NonNull RawRule rawRule) {
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
