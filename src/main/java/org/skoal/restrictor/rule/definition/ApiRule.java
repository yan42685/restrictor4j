package org.skoal.restrictor.rule.definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * 由于 Yaml解析器会调用无参构造函数和setter方法，所以加了@AllargsConstructor之后必须加上@NoArgsConstructor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiRule {
    private String api;
    /**
     * 限制次数
     */
    private int limit;
    /**
     * 统计周期
     */
    private int period;

    /**
     * 时间单位, 默认为秒
     */
    private TimeUnit unit;
}

