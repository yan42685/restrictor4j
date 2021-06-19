package org.skoal.restrictor.algorithm;

import org.apache.commons.lang3.time.StopWatch;
import org.skoal.restrictor.rule.definition.ApiRule;
import org.skoal.restrictor.utils.TimeUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 固定时间算法
 */
public class FixedWindow implements LimitingAlgorithm {
    private static final int TRY_LOCK_TIMEOUT = 200;
    private final int limit;
    private final long millisecondsPeriod;
    private final StopWatch stopWatch = new StopWatch();
    private final Lock lock = new ReentrantLock();
    private final AtomicInteger currentCount = new AtomicInteger(0);

    public FixedWindow(ApiRule apiRule) {
        limit = apiRule.getLimit();
        millisecondsPeriod = TimeUtils.toMillis(apiRule.getPeriod(), apiRule.getUnit());
        stopWatch.start();
    }

    @Override
    public boolean tryAcquire() {
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }
        // 如果计数超过限制且此时已经超过一个计数周期的时间，那么就重置计数器
        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                stopWatch.split();
                if (stopWatch.getSplitTime() > millisecondsPeriod) {
                    stopWatch.reset();
                    currentCount.set(1);
                    return true;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


}