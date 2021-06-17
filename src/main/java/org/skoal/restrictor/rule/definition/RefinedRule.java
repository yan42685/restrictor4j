package org.skoal.restrictor.rule.definition;

/**
 * 将原始结构的配置转化为能高效查询的数据结构，比如前缀树
 */
public interface RefinedRule {
    ApiRule getApiRule(String clientId, String api);
}
