package org.skoal.restrictor.config.definition;

import lombok.Builder;
import lombok.Data;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.enums.RuleSourceType;

@Builder
@Data
public class RestrictorConfig {
    /**
     * 限流规则来源：FILE or ZOOKEEPER
     */
    @Builder.Default
    private RuleSourceType ruleSourceType = RuleSourceType.FILE;
    @Builder.Default
    // TODO: 改成令牌桶
    private LimitingAlgorithmType algorithmType = LimitingAlgorithmType.FIxED_WINDOW;
    private RedisConfig redisConfig;
    private ZookeeperConfig zookeeperConfig;

}
