package org.skoal.restrictor.restrictor;

import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.loader.FileConfigLoader;
import org.skoal.restrictor.counter.CountersMap;
import org.skoal.restrictor.counter.algorithm.LimitingCounter;

public class AbstractRestrictor implements Restrictor {
    private final RestrictorConfig config;
    private CountersMap countersMap;

    /**
     * 通过文件配置
     */
    public AbstractRestrictor() {
        config = new FileConfigLoader().load();
        initAccordingToConfig();
    }

    /**
     * 通过编程配置
     */
    public AbstractRestrictor(RestrictorConfig customConfig) {
        config = customConfig;
        initAccordingToConfig();
    }

    @Override
    public boolean tryAcquire(String clientId, String api) {
        LimitingCounter counter = countersMap.getCounter(clientId, api);
        return counter == null || counter.tryAcquire();
    }

    private void initAccordingToConfig() {
        countersMap = new CountersMap(config.getAlgorithmType(), config.getRuleSourceType());
    }
}
