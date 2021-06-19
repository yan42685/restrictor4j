package org.skoal.restrictor.restrictor;

import lombok.Data;
import org.skoal.restrictor.algorithm.*;
import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.loader.FileConfigLoader;
import org.skoal.restrictor.rule.RuleMapFactory;
import org.skoal.restrictor.rule.definition.ApiRule;
import org.skoal.restrictor.rule.definition.ApiRuleMap;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class AbstractRestrictor {
    private RestrictorConfig config;
    private ApiRuleMap apiRuleMap;
    private final ConcurrentHashMap<String, LimitingAlgorithm> countersMap = new ConcurrentHashMap<>(256);

    public AbstractRestrictor() {
        loadConfig();
        loadRule();
        initCountersMap();
    }

    public boolean tryAcquire(String clientId, String api) {
        String counterKey = this.apiRuleMap.generateKey(clientId, api);
        if (!this.apiRuleMap.containsKey(counterKey)) {
            return true;
        }

        LimitingAlgorithm counter = countersMap.get(counterKey);
        return counter.tryAcquire();
    }

    private void loadConfig() {
        // 配置优先级：编程接口 > 配置文件 > 默认配置
        this.config = new FileConfigLoader().load();
    }

    private void loadRule() {
        this.apiRuleMap = RuleMapFactory.create(config.getRuleSourceType());
    }

    /**
     * 给限流规则中，每个client的每个api添加一个counter
     */
    private void initCountersMap() {
        this.apiRuleMap.getEntrySet().forEach(entry -> {
            this.countersMap.put(entry.getKey(), getLimitAlgorithm(entry.getValue()));
        });
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
