package org.skoal.restrictor.rule.datasource;

import lombok.extern.slf4j.Slf4j;
import org.skoal.restrictor.rule.definition.RawRule;
import org.skoal.restrictor.rule.parser.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FileRuleSource implements RuleSource {
    private static final String DEFAULT_FILENAME = "restrictor-rule";
    private static final String[] SUPPORTED_EXTENSIONS = {"json", "yaml", "yml", "properties", "xml"};
    private static final Map<String, RuleParser> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put(SUPPORTED_EXTENSIONS[0], new JsonRuleParser());
        PARSER_MAP.put(SUPPORTED_EXTENSIONS[1], new YamlRuleParser());
        PARSER_MAP.put(SUPPORTED_EXTENSIONS[2], new YamlRuleParser());
        PARSER_MAP.put(SUPPORTED_EXTENSIONS[3], new PropertiesRuleParser());
        PARSER_MAP.put(SUPPORTED_EXTENSIONS[4], new XmlRuleParser());
    }

    @Override
    public RawRule getRawRule() {
        for (String extension : SUPPORTED_EXTENSIONS) {
            InputStream in = null;
            try {
                in = this.getClass().getResourceAsStream("/" + DEFAULT_FILENAME + "." + extension);
                if (in != null) {
                    RuleParser parser = PARSER_MAP.get(extension);
                    return parser.parse(in);
                }
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        log.error("close file error: {0}", e);
                    }
                }
            }
        }
        return null;
    }
}
