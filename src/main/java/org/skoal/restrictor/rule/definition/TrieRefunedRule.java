package org.skoal.restrictor.rule.definition;

/**
 * TODO: 实现前缀树
 */
public class TrieRefunedRule implements RefinedRule {

    @Override
    public ApiRule getApiRule(String clientId, String api) {
        /**
         * 仅为测试用, 以后需要修改这段代码
         */
        return new ApiRule();
    }

    public TrieRefunedRule(RawRule rawRule) {
    }
}
