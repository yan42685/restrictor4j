package org.skoal.restrictor.rule;

import lombok.NonNull;
import org.skoal.restrictor.config.enums.RuleSourceType;
import org.skoal.restrictor.config.enums.RuleStructureType;
import org.skoal.restrictor.rule.definition.HashMapRefinedRule;
import org.skoal.restrictor.rule.definition.RawRule;
import org.skoal.restrictor.rule.definition.RefinedRule;
import org.skoal.restrictor.rule.definition.TrieRefinedRule;
import org.skoal.restrictor.rule.loader.FileRuleLoader;
import org.skoal.restrictor.rule.loader.ZookeeperRuleLoader;
import org.skoal.restrictor.utils.Asserts;

public class RuleFactory {
    public static RefinedRule create(RuleSourceType sourceType, RuleStructureType structureType) {
        RawRule rawRule = loadRawRule(sourceType);
        fillDefaultValue(rawRule);
        return refineStructure(rawRule, structureType);
    }

    private static RawRule loadRawRule(RuleSourceType sourceType) {
        RawRule rawRule = null;
        // 按限流规则来源方式加载原始规则
        if (RuleSourceType.FILE.equals(sourceType)) {
            rawRule = new FileRuleLoader().getRawRule();
        } else if (RuleSourceType.ZOOKEEPER.equals(sourceType)) {
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

    /**
     * 优化查询结构
     */
    private static RefinedRule refineStructure(RawRule rawRule, RuleStructureType structureType) {
        if (RuleStructureType.HASH_MAP.equals(structureType)) {
            return new HashMapRefinedRule(rawRule);
        } else if (RuleStructureType.TRIE.equals(structureType)) {
            return new TrieRefinedRule(rawRule);
        } else {
            throw new RuntimeException("不支持的规则结构类型: " + structureType);
        }
    }

}
