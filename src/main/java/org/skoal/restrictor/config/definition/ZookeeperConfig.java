package org.skoal.restrictor.config.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ZookeeperConfig {
    @Builder.Default
    private String address = null;
    @Builder.Default
    private String rulePath = "/restrictor/restrictor-rule";
}
