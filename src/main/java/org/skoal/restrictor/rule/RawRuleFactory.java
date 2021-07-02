package org.skoal.restrictor.rule;

import lombok.NonNull;
import org.skoal.restrictor.config.enums.RuleSourceType;
import org.skoal.restrictor.rule.definition.RawRule;
import org.skoal.restrictor.rule.loader.FileRuleLoader;
import org.skoal.restrictor.rule.loader.ZookeeperRuleLoader;
import org.skoal.restrictor.utils.Asserts;

public class RawRuleFactory {
    public static RawRule create(RuleSourceType sourceType) {
        RawRule rawRule = loadRawRule(sourceType);
        fillDefaultValue(rawRule);
        return rawRule;
    }

    private static RawRule loadRawRule(RuleSourceType sourceType) {
        RawRule rawRule = null;
        // 按限流规则来源方式加载原始规则
        if (RuleSourceType.FILE.equals(sourceType)) {
            rawRule = new FileRuleLoader().getRawRule();
        } else if (RuleSourceType.ZOO_KEEPER.equals(sourceType)) {
            rawRule = new ZookeeperRuleLoader().getRawRule();
        }
        Asserts.notNull(rawRule, "不支持的sourceType: " + sourceType);
        return rawRule;
    }

    /**
     * 用上层默认属性覆盖下层未设置的属性, 这样可以用一个设置管理一群 api
     */
    private static void fillDefaultValue(@NonNull RawRule rawRule) {
        if (rawRule.getClientRules() == null) {
            return;
        }
        rawRule.getClientRules().forEach(clientRule -> {
            if (clientRule.getPeriod() == 0) {
                clientRule.setPeriod(rawRule.getPeriod());
            }
            if (clientRule.getUnit() == null) {
                clientRule.setUnit(rawRule.getUnit());
            }

            clientRule.getApiRules().forEach(apiRule -> {
                if (apiRule.getPeriod() == 0) {
                    apiRule.setPeriod(clientRule.getPeriod());
                }
                if (apiRule.getUnit() == null) {
                    apiRule.setUnit(clientRule.getUnit());
                }
            });
        });
    }
}
