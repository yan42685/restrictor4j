package org.skoal.restrictor.rule.loader;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        return rawRule == null ? new RawRule() : rawRule;
    }

}
