package org.skoal.restrictor.restrictor;

import lombok.Data;
import org.skoal.restrictor.algorithm.*;
import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.loader.FileConfigLoader;
import org.skoal.restrictor.rule.RuleFactory;
import org.skoal.restrictor.rule.definition.ApiRule;
import org.skoal.restrictor.rule.definition.RefinedRule;

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
        this.rules = RuleFactory.create(config.getRuleSourceType(), config.getRuleStructureType());
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
