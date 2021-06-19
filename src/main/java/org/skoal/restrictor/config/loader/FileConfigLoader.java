package org.skoal.restrictor.config.loader;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FileConfigLoader {
    protected static final String[] SUPPORTED_EXTENSIONS = {"json", "yaml", "yml"};
    private static final String DEFAULT_PATH = "/restrictor/restrictor-config";

    public RestrictorConfig load() {
        for (String extension : SUPPORTED_EXTENSIONS) {
            // try-with-resource自动关闭流 (只有实现了java.lang.AutoCloseable接口的类，才可以被自动关闭)
            String fullPath = DEFAULT_PATH + "." + extension;
            try (InputStream in = this.getClass().getResourceAsStream(fullPath)) {
                if (in != null) {
                    log.info("读取配置文件: " + fullPath);
                    String ruleText = IoUtil.read(in, "utf-8");
                    return parse(ruleText, extension);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("未找到配置文件");
        return new RestrictorConfig();

    }

    private RestrictorConfig parse(String text, String extension) {
        switch (extension) {
            case "json":
                return JSONUtil.toBean(text, RestrictorConfig.class, false);
            case "yaml":
            case "yml":
                return new Yaml().loadAs(text, RestrictorConfig.class);
            default:
                throw new RuntimeException("不支持的文件后缀名: " + extension);
        }
    }
}
