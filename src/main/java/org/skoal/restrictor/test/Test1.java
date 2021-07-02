package org.skoal.restrictor.test;

import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.enums.LimitingAlgorithmType;

public class Test1 {
    public static void main(String[] args) {
        RestrictorConfig config = RestrictorConfig.builder().algorithmType(LimitingAlgorithmType.FIXED_WINDOW).build();
        System.out.println(config.getRedisConfig());


    }
}
