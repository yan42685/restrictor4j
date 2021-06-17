package org.skoal.restrictor.rule.parser;

import org.skoal.restrictor.rule.definition.RawRule;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class YamlRuleParser implements RuleParser {
    @Override
    public RawRule parse(String ruleText) {
        return null;
    }

    @Override
    public RawRule parse(InputStream in) {
        Yaml yaml = new Yaml();
        RawRule rawRule = yaml.loadAs(in, RawRule.class);
        return rawRule;
    }
}
