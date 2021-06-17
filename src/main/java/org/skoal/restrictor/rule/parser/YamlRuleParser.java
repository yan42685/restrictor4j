package org.skoal.restrictor.rule.parser;

import org.skoal.restrictor.rule.definition.RawRule;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class YamlRuleParser extends RuleParser {
    @Override
    protected RawRule loadRule(InputStream in) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(in, RawRule.class);
    }
}
