package org.skoal.restrictor.rule.parser;

import org.skoal.restrictor.rule.definition.RawRule;

import java.io.InputStream;

public interface RuleParser {
    RawRule parse(String ruleText);

    RawRule parse(InputStream in);
}
