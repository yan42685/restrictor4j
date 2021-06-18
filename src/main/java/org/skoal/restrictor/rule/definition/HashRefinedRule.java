package org.skoal.restrictor.rule.definition;

import java.util.HashMap;
import java.util.Map;

public class HashRefinedRule implements RefinedRule {
    private final Map<String, ApiRule> map = new HashMap<>();

    @Override
    public ApiRule getApiRule(String clientId, String api) {
        return map.get(clientId + ":" + api);
    }

    public HashRefinedRule(RawRule rawRule) {
        rawRule.getClientRules().forEach(clientRule -> {
            clientRule.getApiRules().forEach(apiRule -> {
                String key = clientRule.getClientId() + ":" + apiRule.getApi();
                map.put(key, apiRule);
            });
        });
    }
}
