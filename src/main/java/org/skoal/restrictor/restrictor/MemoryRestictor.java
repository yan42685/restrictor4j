package org.skoal.restrictor.restrictor;

import org.skoal.restrictor.counter.FixTimeCounter;
import org.skoal.restrictor.counter.LimitCounter;
import org.skoal.restrictor.rule.definition.ApiRule;
import org.skoal.restrictor.rule.definition.HashRefinedRule;
import org.skoal.restrictor.rule.definition.RawRule;
import org.skoal.restrictor.rule.definition.RefinedRule;
import org.skoal.restrictor.rule.loader.FileRuleLoader;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryRestictor {
    private final ConcurrentHashMap<String, LimitCounter> countersMap = new ConcurrentHashMap<>();
    private final RefinedRule rules;

    public MemoryRestictor() {
        // TODO: 改成可配置的规则来源方式和优化的结构
        RawRule rawRule = new FileRuleLoader().getRawRule();
        System.out.println(rawRule);
        this.rules = new HashRefinedRule(rawRule);
    }

    public boolean isAvailable(String clientId, String api) {
        ApiRule apiRule = this.rules.getApiRule(clientId, api);
        if (apiRule == null) {
            return true;
        }

        // 如果存在该规则就检查计数器是否允许调用该api
        String counterKey = clientId + ":" + api;
        if (!countersMap.containsKey(counterKey)) {
            // TODO: 修改成Counter类型可配置
            countersMap.put(counterKey, new FixTimeCounter(apiRule));
        }

        LimitCounter counter = countersMap.get(counterKey);
        return counter.tryAcquire();
    }

}
