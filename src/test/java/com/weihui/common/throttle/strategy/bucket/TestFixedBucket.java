package com.weihui.common.throttle.strategy.bucket;

import com.weihui.common.throttle.Throttle;
import com.weihui.common.throttle.strategy.ThrottleStrategy;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version TestBUcket.java 1.0 @2015/12/28 16:14 $
 */
public class TestFixedBucket {

    ThrottleStrategy strategy = new FixedTokenBucketStrategy(100, 1, TimeUnit.MINUTES);
    Throttle throttle = new Throttle(strategy);

    @Test
    public void build(){

    }

    @Test
    public void test() throws InterruptedException {
        // construct strategy

// provide the strategy to the throttler


// throttle :)
        for(int i=0;i<500;i++) {
//            Callable bucketCallable = new BucketCallable(i);
//            FutureTask futureTask = new FutureTask(bucketCallable);
//            Thread pAccountThread = new Thread(futureTask);
//            pAccountThread.start();

            final int num = i;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    boolean status = throttle.canProceed();
                    System.out.println(num + ":" + status + ":" + Thread.currentThread().getName());
                }
            });

            thread.start();
            Thread.sleep(500);

//            92:true:Thread-92
//            93:true:Thread-93
//            94:true:Thread-94
//            95:true:Thread-95
//            96:true:Thread-96
//            97:true:Thread-97
//            98:true:Thread-98
//            99:true:Thread-99
//            100:false:Thread-100
//            101:false:Thread-101
//            102:false:Thread-102

        }
    }

    class BucketCallable implements Callable {
        int num;

        public BucketCallable(int num) {
            this.num = num;
        }

        public Object call() throws Exception {
            boolean isThrottled = throttle.canProceed();
            Thread.sleep(500);
            System.out.println(num+":"+isThrottled);
            return isThrottled;
        }

    }

}



