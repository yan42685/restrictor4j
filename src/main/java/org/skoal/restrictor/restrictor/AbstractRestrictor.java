package org.skoal.restrictor.restrictor;

import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.loader.FileConfigLoader;
import org.skoal.restrictor.counter.CounterRegistry;
import org.skoal.restrictor.counter.algorithm.LimitingCounter;

public class AbstractRestrictor implements Restrictor {
    private final RestrictorConfig config;
    private CounterRegistry counterRegistry;

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
        LimitingCounter counter = counterRegistry.getCounter(clientId, api);
        return counter == null || counter.tryAcquire();
    }

    private void initAccordingToConfig() {
        counterRegistry = new CounterRegistry(config.getAlgorithmType(), config.getRuleSourceType());
    }
}
