package org.skoal.restrictor.config.definition;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ZookeeperConfig {
    @Builder.Default
    private String address = null;
    @Builder.Default
    private String rulePath = "/restrictor/restrictor-rule";
}
