package com.weihui.common.throttle;

import com.weihui.common.throttle.strategy.ThrottleStrategy;

import java.util.concurrent.TimeUnit;

/**
 * <p>限流.</p>
 *
 * @author Magic Joey
 * @version Throttle.java 1.0 @2015/12/28 20:34 $
 */
public class Throttle {

    /**
     * 限流策略
     */
    private ThrottleStrategy strategy;

    public Throttle() {
    }

    public Throttle(ThrottleStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 取决于strategy的选择，阈值内返回true，超出阈值时返回false
     * @return
     */
    public boolean canProceed(){
        return !strategy.isThrottled();
    }


    /**
     * 如果服务可用则返回等待时间为0，否则返回一个大于0的值
     * @param timeUnit 等待时间
     * @return >=0的数字
     */
    public long waitTime(TimeUnit timeUnit){
        return strategy.timeToRelease(1,timeUnit);
    }


    public void setStrategy(ThrottleStrategy strategy) {
        this.strategy = strategy;
    }
}
