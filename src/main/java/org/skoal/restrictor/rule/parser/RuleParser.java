package org.skoal.restrictor.rule.parser;

import org.skoal.restrictor.rule.definition.RawRule;

import java.io.InputStream;

public abstract class RuleParser {
    protected abstract RawRule loadRule(InputStream in);

    // 模板方法模式
    public RawRule parse(InputStream in) {
        RawRule rawRule = loadRule(in);
        setPeriodAndUnitByDefaultValue(rawRule);
        return rawRule;
    }

    /**
     * 从上到下依次覆盖没有设置的 period 和 unit, 这样可以用一个设置管理一大片 api
     */
    private void setPeriodAndUnitByDefaultValue(RawRule rawRule) {
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
