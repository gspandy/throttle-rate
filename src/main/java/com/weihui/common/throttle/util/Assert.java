package com.weihui.common.throttle.util;

/**
 * <p>
 * 和junit的一样.
 * </p>
 *
 * @author Magic Joey
 * @version Assert.java 1.0 @2015/12/28 20:42 $
 */
public class Assert {
    public static void isTrue(boolean bool, String message) {
        if (!bool)
            throw new IllegalArgumentException(message);
         }
}
