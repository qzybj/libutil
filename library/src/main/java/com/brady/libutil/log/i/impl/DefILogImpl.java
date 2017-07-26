package com.brady.libutil.log.i.impl;

import android.util.Log;

import com.brady.libutil.log.i.ILog;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 默认实现log
 */
public class DefILogImpl implements ILog {

    private static final String TAG = "Clog";

    public void log(Object obj) {
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

    public void d(String msg) {
        d(TAG, msg);
    }

    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public  void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public  void e(Exception e) {
        e(TAG, e);
    }

    public  void e(String tag, Exception e) {
        if (e != null) {
            Log.e(tag, e.getLocalizedMessage());
        }
    }

    public  void e(String msg) {
        e(TAG, msg);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean switchOn(boolean isOn) {
        return false;
    }

    @Override
    public boolean isShowMethod() {
        return true;
    }

    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }
}