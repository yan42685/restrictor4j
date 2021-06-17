package org.skoal.restrictor.rule.definition;

import lombok.Data;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
public class ClientRule {
    private String clientId;
    private int period;
    private TimeUnit unit;
    private List<ApiRule> apiRules;
}
