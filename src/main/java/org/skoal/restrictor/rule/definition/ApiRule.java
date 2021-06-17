package org.skoal.restrictor.rule.definition;

import lombok.Data;

import java.util.concurrent.TimeUnit;

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

