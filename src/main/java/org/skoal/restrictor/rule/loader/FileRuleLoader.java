package org.skoal.restrictor.rule.loader;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FileRuleLoader extends AbstractRuleLoader {
    private static final String DEFAULT_PATH = "/restrictor/restrictor-rule";

    @Override
    protected RuleInfo load() {
        for (String extension : SUPPORTED_EXTENSIONS) {
            // try-with-resource自动关闭流 (只有实现了java.lang.AutoCloseable接口的类，才可以被自动关闭)
            String fullPath = DEFAULT_PATH + "." + extension;
            try (InputStream in = this.getClass().getResourceAsStream(fullPath)) {
                if (in != null) {
                    log.info("读取规则文件: " + fullPath);
                    String ruleText = IoUtil.read(in, "utf-8");
                    return new RuleInfo(ruleText, extension);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.warn("未找到规则文件");
        return null;
    }
}
