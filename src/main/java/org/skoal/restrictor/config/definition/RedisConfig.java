package org.skoal.restrictor.config.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RedisConfig {
    @Builder.Default
    private String address = null;
    @Builder.Default
    private int port = 6379;
    @Builder.Default
    private int maxWaitMillis = 10;
    @Builder.Default
    private int maxTotal = 50;
    @Builder.Default
    private int maxIdle = 50;
    @Builder.Default
    private int minIdle = 20;
    @Builder.Default
    private boolean testOnBorrow = true;
}
