package org.skoal.restrictor.restrictor;

import lombok.Data;
import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.loader.FileConfigLoader;
import org.skoal.restrictor.counter.CountersMap;
import org.skoal.restrictor.counter.algorithm.LimitingCounter;
import org.skoal.restrictor.rule.RawRuleFactory;
import org.skoal.restrictor.rule.definition.RawRule;

@Data
public class AbstractRestrictor {
    private RestrictorConfig config;
    private RawRule rawRule;
    private CountersMap countersMap;

    public AbstractRestrictor() {
        loadConfig();
        loadRule();
        initCountersMap();
    }

    public boolean tryAcquire(String clientId, String api) {
        String counterKey = this.countersMap.generateKey(clientId, api);
        LimitingCounter counter = this.countersMap.getCounter(counterKey);
        return counter == null || counter.tryAcquire();
    }

    private void loadConfig() {
        // 配置优先级：编程接口 > 配置文件 > 默认配置
        this.config = new FileConfigLoader().load();
    }

    private void loadRule() {
        this.rawRule = RawRuleFactory.create(this.config.getRuleSourceType());
    }

    private void initCountersMap() {
        this.countersMap = new CountersMap(this.config.getAlgorithmType(), rawRule);
    }

}
