package org.skoal.restrictor.config.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.enums.RuleSourceType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RestrictorConfig {
    /**
     * 限流规则来源：FILE or ZOOKEEPER
     */
    @Builder.Default
    private RuleSourceType ruleSourceType = RuleSourceType.FILE;
    // TODO: 改成令牌桶
    @Builder.Default
    private LimitingAlgorithmType algorithmType = LimitingAlgorithmType.FIxED_WINDOW;
    @Builder.Default
    private RedisConfig redisConfig = new RedisConfig();
    @Builder.Default
    private ZookeeperConfig zookeeperConfig = new ZookeeperConfig();

}
