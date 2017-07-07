package com.brady.libutil;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 自定义log
 */
public class CLog {

    private static final String TAG = "CLog";

    /**
     * 开发模式才显示日志
     */
    private static boolean isDebug = true;

    /**
     * 设置日志开关
     * @param isShow
     */
    public static void showSwitch(boolean isShow) {
        isDebug = isShow;
    }

    public static void log(Object obj) {
        String msg;
        if (obj instanceof Throwable) {
            StringWriter sw = new StringWriter();
            ((Throwable) obj).printStackTrace(new PrintWriter(sw));
            msg = sw.toString();
        } else {
            msg = String.valueOf(obj);
        }
        String callclassname = new Exception().getStackTrace()[1].getClassName();
        String callmethodname = new Exception().getStackTrace()[1].getMethodName();

        msg = callclassname + " -> " + callmethodname + ": " + msg;
        d(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void e(Exception e) {
        e(TAG, e);
    }

    public static void e(String tag, Exception e) {
        if (isDebug) {
            if (e != null) {
                Log.e(tag, e.getLocalizedMessage());
            }
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }
}