package com.weihui.common.throttle.strategy.bucket;

import java.util.concurrent.TimeUnit;

import com.weihui.common.throttle.strategy.ThrottleStrategy;
import com.weihui.common.throttle.util.Assert;

/**
 * <p>token限流策略-抽象类.</p>
 *
 *
 * @author Magic Joey
 * @version Throttle.java 1.0 @2015/12/28 20:34 $
 */
public abstract class TokenBucketStrategy implements ThrottleStrategy {

    protected final long bucketTokenCapacity;
	protected final long refillInterval;

	// number of tokens in the bucket
	protected long tokens = 0;
	protected long nextRefillTime = 0;

    protected TokenBucketStrategy(long bucketTokenCapacity, long refillInterval, TimeUnit refillIntervalTimeUnit) {
        Assert.isTrue(bucketTokenCapacity >= 0, "bucket token必须大于0");
		Assert.isTrue(refillInterval >= 0, "refill值必须大于0");

        this.bucketTokenCapacity = bucketTokenCapacity;
		this.refillInterval = refillIntervalTimeUnit.toMillis(refillInterval);
    }

    public  boolean isThrottled() {
        //默认为使用一次
        return isThrottled(1);
    }

    public boolean isThrottled(long n) {
        // preconditions
        Assert.isTrue(n >= 0, "参数必须大于0");

        synchronized(this){
            //　检查本次请求是否允许
            if(getCurrentTokenCount() < n) return true;
            tokens -= n;
        }

        return false;
    }

    public long getCapacity() {
        return bucketTokenCapacity;
    }

    public long getCurrentTokenCount() {
        //获取当前容量
        synchronized(this) {
            updateTokens();
            return tokens;
        }
    }

	public  long timeToRelease(long n, TimeUnit timeUnit){
		// pre check
		Assert.isTrue(n >= 0, "参数必须大于0");
		Assert.isTrue(timeUnit != null, "timeUnit不可为空");

		// token
		if(getCurrentTokenCount() >= n){
			return 0L;
		} else{
            synchronized(this) {
                long timeToIntervalEnd = nextRefillTime - System.currentTimeMillis();
                // 处理因系统慢导致的情况
                if (timeToIntervalEnd < 0) {
                    return timeToRelease(n, timeUnit);
                }else{
                    return timeUnit.convert(timeToIntervalEnd, TimeUnit.MILLISECONDS);
                }
            }
		}
	}

    protected abstract void updateTokens();
}
