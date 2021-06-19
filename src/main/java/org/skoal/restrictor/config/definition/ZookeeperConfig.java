package org.skoal.restrictor.config.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ZookeeperConfig {
    private String address = null;
    private String rulePath = "/restrictor/restrictor-rule";
}
