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
    private static final String DEFAULT_PATH = "/restrictor/restrictor-rule";
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
            // try-with-resource自动关闭流 (只有实现了java.lang.AutoCloseable接口的类，才可以被自动关闭)
            String fullPath = DEFAULT_PATH + "." + extension;
            try (InputStream in = this.getClass().getResourceAsStream(fullPath)) {
                if (in != null) {
                    RuleParser parser = PARSER_MAP.get(extension);
                    return parser.parse(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
