package org.skoal.restrictor.config.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;
import org.skoal.restrictor.config.enums.RuleSourceType;
import org.skoal.restrictor.config.enums.RuleStructure;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RestrictorConfig {
    /**
     * 限流规则来源：FILE or ZOOKEEPER
     */
    private RuleSourceType ruleSourceType = RuleSourceType.FILE;
    // TODO: 改成令牌桶
    private LimitingAlgorithmType algorithmType = LimitingAlgorithmType.FIxED_WINDOW;
    private RuleStructure ruleStructure = RuleStructure.HASH_MAP;
    private RedisConfig redisConfig;
    private ZookeeperConfig zookeeperConfig;

}
