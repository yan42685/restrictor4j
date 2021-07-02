package org.skoal.restrictor.config.enums;

/**
 * 计数器固定窗口算法实现简单，容易理解。和漏斗算法相比，新来的请求也能够被马上处理到。
 * 但是流量曲线可能不够平滑，有“突刺现象”，在窗口切换时可能会产生两倍于阈值流量的请求。
 * 而计数器滑动窗口算法作为计数器固定窗口算法的一种改进，有效解决了窗口切换时可能会产生两倍于阈值流量请求的问题。
 * 漏斗算法能够对流量起到整流的作用，让随机不稳定的流量以固定的速率流出，但是不能解决流量突发的问题。
 * 令牌桶算法作为漏斗算法的一种改进，除了能够起到平滑流量的作用，还允许一定程度的流量突发。
 * 以上四种限流算法都有自身的特点，具体使用时还是要结合自身的场景进行选取，没有最好的算法，只有最合适的算法。
 * 比如令牌桶算法一般用于保护自身的系统，对调用者进行限流，保护自身的系统不被突发的流量打垮。
 * 如果自身的系统实际的处理能力强于配置的流量限制时，可以允许一定程度的流量突发，使得实际的处理速率高于配置的速率，充分利用系统资源。
 * 而漏斗算法一般用于保护第三方的系统，比如自身的系统需要调用第三方的接口，
 * 为了保护第三方的系统不被自身的调用打垮，便可以通过漏斗算法进行限流，保证自身的流量平稳的打到第三方的接口上。
 */
public enum LimitingAlgorithmType {
    FIXED_WINDOW, SLIDING_WINDOW, LEAKY_BUCKET, TOKEN_BUCKET
}
