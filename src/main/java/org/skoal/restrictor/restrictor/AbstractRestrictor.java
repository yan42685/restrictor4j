package org.skoal.restrictor.restrictor;

import lombok.Data;
import org.skoal.restrictor.algorithm.*;
import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.enums.RuleSourceType;
import org.skoal.restrictor.config.enums.RuleStructure;
import org.skoal.restrictor.config.loader.FileConfigLoader;
import org.skoal.restrictor.rule.definition.*;
import org.skoal.restrictor.rule.loader.FileRuleLoader;
import org.skoal.restrictor.rule.loader.ZookeeperRuleLoader;
import org.skoal.restrictor.utils.Asserts;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class AbstractRestrictor {
    private RestrictorConfig config;
    private final ConcurrentHashMap<String, LimitingAlgorithm> countersMap = new ConcurrentHashMap<>(256);
    private RefinedRule rules;

    public AbstractRestrictor() {
        loadConfig();
        loadRule();
    }

    public boolean isAvailable(String clientId, String api) {
        ApiRule apiRule = this.rules.getApiRule(clientId, api);
        if (apiRule == null) {
            return true;
        }

        // 如果存在该规则就检查计数器是否允许调用该api
        String counterKey = clientId + ":" + api;
        if (!countersMap.containsKey(counterKey)) {
            countersMap.put(counterKey, getLimitAlgorithm(apiRule));
        }

        LimitingAlgorithm counter = countersMap.get(counterKey);
        return counter.tryAcquire();
    }

    private void loadConfig() {
        // 配置优先级：编程接口 > 配置文件 > 默认配置
        this.config = new FileConfigLoader().load();
    }

    private void loadRule() {
        RawRule rawRule = null;
        // 按限流规则来源方式加载原始规则
        RuleSourceType ruleSourceType = this.config.getRuleSourceType();
        if (RuleSourceType.FILE.equals(ruleSourceType)) {
            rawRule = new FileRuleLoader().getRawRule();
        } else if (RuleSourceType.ZOOKEEPER.equals(ruleSourceType)) {
            rawRule = new ZookeeperRuleLoader().getRawRule();
        }

        Asserts.notNull(rawRule);
        System.out.println(rawRule);

        // 按优化结构类型选择优化方案
        RuleStructure ruleStructure = this.config.getRuleStructure();
        if (RuleStructure.HASH_MAP.equals(ruleStructure)) {
            this.rules = new HashMapRefinedRule(rawRule);
        } else if (RuleStructure.TRIE.equals(ruleStructure)) {
            this.rules = new TrieRefinedRule(rawRule);
        }
    }

    private LimitingAlgorithm getLimitAlgorithm(ApiRule apiRule) {
        LimitingAlgorithmType algorithmType = this.config.getAlgorithmType();
        switch (algorithmType) {
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
