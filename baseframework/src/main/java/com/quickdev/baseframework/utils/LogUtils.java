
package com.quickdev.baseframework.utils;

import android.util.Log;

import com.quickdev.baseframework.BuildConfig;

/**
 * 日志输入的工具类，可动态修改输出级别。
 */
public class LogUtils {

    /**
     * 日志输出时的TAG
     */
    private static final String DEFAULT_TAG = "ThisApplication";

    /**
     * 是否允许输出log
     */
    private static boolean mDebuggable = BuildConfig.DEBUG;

    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String tag, String msg) {
        if (mDebuggable) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag) {
        d(DEFAULT_TAG, tag);
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag, String msg) {
        if (mDebuggable) {
            Log.d(tag, msg);

        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String tag, String msg) {
        if (mDebuggable) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String tag, String msg) {
        if (mDebuggable) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public static void w(Throwable tr) {
        if (mDebuggable) {
            Log.w(DEFAULT_TAG, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String msg, Throwable tr) {
        if (mDebuggable && null != msg) {
            Log.w(DEFAULT_TAG, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String mTag, String msg) {
        e(mTag, msg);
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(Throwable tr) {
        e(DEFAULT_TAG, "", tr);
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String msg, Throwable tr) {
        e(DEFAULT_TAG, msg, tr);
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (mDebuggable && null != msg) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * 把Log存储到文件中
     *
     * @param log  需要存储的日志
     * @param path 存储路径
     */
    public static void log2File(String log, String path) {
        log2File(log, path, true);
    }

    public static void log2File(String log, String path, boolean append) {
        synchronized (mLogLock) {
            FileUtils.writeFile(log + "\r\n", path, append);
        }
    }

}
