package org.skoal.restrictor.config.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RedisConfig {
    private String address = null;
    private int port = 6379;
    private int maxWaitMillis = 10;
    private int maxTotal = 50;
    private int maxIdle = 50;
    private int minIdle = 20;
    private boolean testOnBorrow = true;
}
