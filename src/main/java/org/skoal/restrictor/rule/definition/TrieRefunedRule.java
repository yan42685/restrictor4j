package org.skoal.restrictor.rule.definition;

import java.util.concurrent.TimeUnit;

/**
 * TODO: 实现前缀树
 */
public class TrieRefunedRule implements RefinedRule {

    @Override
    public ApiRule getApiRule(String clientId, String api) {
        /**
         * 仅为测试用, 以后需要修改这段代码
         */
        return new ApiRule("/test/1", 100, 60, TimeUnit.SECONDS);
    }

    public TrieRefunedRule(RawRule rawRule) {
    }
}
