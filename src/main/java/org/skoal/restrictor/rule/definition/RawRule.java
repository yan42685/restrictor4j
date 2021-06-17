package org.skoal.restrictor.rule.definition;

import lombok.Data;

import java.util.List;

/**
 * 从配置源读取的原始结构规则
 */
@Data
public class RawRule {
    /**
     * 默认周期60，默认单位秒
     */
    private List<ClientRule> clientRules;

}
