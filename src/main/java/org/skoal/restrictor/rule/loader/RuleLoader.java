package org.skoal.restrictor.rule.loader;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.skoal.restrictor.rule.definition.RawRule;
import org.yaml.snakeyaml.Yaml;

public abstract class RuleLoader {
    protected static final String[] SUPPORTED_EXTENSIONS = {"json", "yaml", "yml"};

    @AllArgsConstructor
    @Data
    public static class RuleInfo {
        String text;
        String extension;
    }

    public RawRule getRawRule() {
        return parse(load());
    }

    protected abstract RuleInfo load();

    private RawRule parse(RuleInfo ruleInfo) {
        String extension = ruleInfo.getExtension();
        String text = ruleInfo.getText();
        RawRule rawRule = null;
        switch (extension) {
            case "yaml":
            case "yml":
                rawRule = new Yaml().loadAs(text, RawRule.class);
                break;
            case "json":
                rawRule = JSONUtil.toBean(text, RawRule.class, false);
                break;
            default:
                throw new RuntimeException("不支持该规则文件的后缀名: " + extension);
        }
        fillDefaultValue(rawRule);
        return rawRule;
    }

    /**
     * 用上层默认属性覆盖下层未设置的属性, 这样可以用一个设置管理一群 api
     */
    private void fillDefaultValue(RawRule rawRule) {
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
