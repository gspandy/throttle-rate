package com.weihui.common.throttle.strategy;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 限流策略.
 * </p>
 *
 * @author Magic Joey
 * @version Throttle.java 1.0 @2015/12/28 20:34 $
 */
public interface ThrottleStrategy {

    /**
     * 请求被限流返回true 请求未被限流返回false
     * 
     * @return bool
     */
    boolean isThrottled();

    /**
     * 请求被限流返回true 请求未被限流返回false
     * 
     * @param num
     *            请求数
     * @return bool
     */
    boolean isThrottled(long num);

    /**
     * token最近可用时间
     * 
     * @param n
     * @param timeUnit
     * @return
     */
    long timeToRelease(long n, TimeUnit timeUnit);

    /**
     * 定义策略可处理最大token数量
     *
     * @return 容量
     */
    long getCapacity();

}
