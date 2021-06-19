package org.skoal.restrictor.config.definition;

import lombok.Data;
import org.skoal.restrictor.config.enums.RuleSourceType;

@Data
public class RestrictorConfig {
    private RuleSourceType ruleSourceType = RuleSourceType.FILE;
    private RedisConfig redisConfig;
    private ZookeeperConfig zookeeperConfig;

    @Data
    public static class RedisConfig {
        private String address = null;
        private int port = 6379;
        private int maxWaitMillis = 10;
        private int maxTotal = 50;
        private int maxIdle = 50;
        private int minIdle = 20;
        private boolean testOnBorrow = true;
    }

    @Data
    public static class ZookeeperConfig {
        private String address = null;
        private String rulePath = "/restrictor/restrictor-rule";
    }
}
