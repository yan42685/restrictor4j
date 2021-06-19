package org.skoal.restrictor.rule.definition;

import lombok.NonNull;
import org.skoal.restrictor.basic.datastructure.Trie;

public class TrieRefinedRule implements RefinedRule {
    private final Trie<ApiRule> trie = new Trie<>();

    @Override
    public ApiRule getApiRule(String clientId, String api) {
        return trie.get(clientId + ":" + api);
    }

    public TrieRefinedRule(@NonNull RawRule rawRule) {
        if (rawRule.getClientRules() == null) {
            return;
        }
        rawRule.getClientRules().forEach(clientRule -> {
            clientRule.getApiRules().forEach(apiRule -> {
                String key = clientRule.getClientId() + ":" + apiRule.getApi();
                trie.insert(key, apiRule);
            });
        });
    }
}
