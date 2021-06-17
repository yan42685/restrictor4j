package org.skoal.restrictor.rule.definition;

import lombok.Data;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 从配置源读取的原始结构规则
 */
@Data
public class RawRule {
    private int period;
    private TimeUnit unit;
    private List<ClientRule> clientRules;

}
