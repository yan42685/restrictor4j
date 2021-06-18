package org.skoal.restrictor.rule.definition;

import org.skoal.restrictor.basic.Trie;

/**
 * 相比hashmap，占用空间较少，耗时较多（约2-5倍的差距）
 */
public class TrieRefunedRule implements RefinedRule {
    private final Trie<ApiRule> trie = new Trie<>();

    @Override
    public ApiRule getApiRule(String clientId, String api) {
        return trie.get(clientId + ":" + api);
    }

    public TrieRefunedRule(RawRule rawRule) {
        rawRule.getClientRules().forEach(clientRule -> {
            clientRule.getApiRules().forEach(apiRule -> {
                String key = clientRule.getClientId() + ":" + apiRule.getApi();
                trie.insert(key, apiRule);
            });
        });
    }
}
