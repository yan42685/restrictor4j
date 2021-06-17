package org.skoal.restrictor.rule.definition;

import lombok.Data;

import java.util.List;

@Data
public class ClientRule {
    private String clientId;
    private List<ApiRule> apiRules;
}
