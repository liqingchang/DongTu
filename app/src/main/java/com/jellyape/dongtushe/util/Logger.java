package com.jellyape.dongtushe.util;

import android.util.Log;

/**
 * Logger. Same as android.util.Log.
 * 
 * Note that each methods decides whether it actually writes a log or not based on the result of
 * {@link Log#isLoggable(String, int)} so you can omit following idiom. {@code if
 * (MozcLog.isLoggable(Log.INFO)) MozcLog.i("foobar"); } } But for such a log entry which is very
 * frequently created and is very heavy to create, you can use (and are recommended to use) the
 * above idiom.
 * 
 * As described in Log's JavaDoc, the default threshold is INFO. You can change the threshold by
 * following command (on host side). {@code adb shell setprop log.tag.Mozc VERVOSE}
 * 
 * Q : "setprop" is tedious. Why do we have to depend on this mechanism? A : By this way we can get
 * detailed log even if we run release version. This is important when we are measuring the
 * performance.
 * 
 * Log level criteria: VERVOSE - This level can be very heavy. DEBUG - Detailed information but
 * cannot be very heavy because this will be used to measure performance. INFO - Usually the log
 * will be show. WARNING - For non-fatal error. ERROR - For fatal (will exit abnormally) error.
 * 
 */
public class Logger {

    public static final String LOGTAG = "JellyApe";

    private Logger() {}
    
    public static boolean debug(){
    	return true;
    }
    
    public static boolean isLoggable(int logLevel) {
        return Log.isLoggable(LOGTAG, logLevel);
    }

    public static void v(String msg) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(LOGTAG, msg);
        }
    }

    public static void v(String msg, Throwable e) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(LOGTAG, msg, e);
        }
    }
    
    public static void v(String tag, String msg) {
    	if (isLoggable(Log.VERBOSE)) {
    		Log.v(tag, msg);
    	}
    }

    public static void d(String msg) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(LOGTAG, msg);
        }
    }

    public static void d(String msg, Throwable e) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(LOGTAG, msg, e);
        }
    }
    
    public static void d(String tag, String msg) {
    	if (isLoggable(Log.DEBUG)) {
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        if (isLoggable(Log.INFO)) {
            Log.i(LOGTAG, msg);
        }
    }

    public static void i(String msg, Throwable e) {
        if (isLoggable(Log.INFO)) {
            Log.i(LOGTAG, msg, e);
        }
    }
    
    public static void i(String tag, String msg) {
    	if (isLoggable(Log.DEBUG)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        if (isLoggable(Log.WARN)) {
            Log.w(LOGTAG, msg);
        }
    }

    public static void w(String msg, Throwable e) {
        if (isLoggable(Log.WARN)) {
            Log.w(LOGTAG, msg, e);
        }
    }

    public static void e(String msg) {
        if (isLoggable(Log.ERROR)) {
            Log.e(LOGTAG, msg);
        }
    }

    public static void e(Throwable e) {
        if (isLoggable(Log.ERROR)) {
            Log.e(LOGTAG, e.toString(), e);
        }
    }

    public static void e(String msg, Throwable e) {
        if (isLoggable(Log.ERROR)) {
            Log.e(LOGTAG, msg, e);
        }
    }
    
    public static void e(String tag, String msg) {
    	if (isLoggable(Log.DEBUG)) {
            Log.e(tag, msg);
        }
    }
}
