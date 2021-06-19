package org.skoal.restrictor.restrictor;

import lombok.Data;
import org.skoal.restrictor.config.definition.RestrictorConfig;
import org.skoal.restrictor.config.loader.FileConfigLoader;

@Data
public class AbstractRestrictor {
    private RestrictorConfig config;

    public AbstractRestrictor() {
        // 配置优先级：编程接口 > 配置文件 > 默认配置
        this.config = new FileConfigLoader().load();
    }

}
