package org.skoal.restrictor.rule.loader;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.skoal.restrictor.rule.definition.RawRule;
import org.yaml.snakeyaml.Yaml;

@Slf4j
public abstract class AbstractRuleLoader {
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
        try {
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
        } catch (Exception e) {
            log.error("限流规则解析失败");
            e.printStackTrace();
        }
        rawRule = rawRule == null ? new RawRule() : rawRule;
        fillDefaultValue(rawRule);
        return rawRule;
    }

    /**
     * 用上层默认属性覆盖下层未设置的属性, 这样可以用一个设置管理一群 api
     */
    private void fillDefaultValue(@NonNull RawRule rawRule) {
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
