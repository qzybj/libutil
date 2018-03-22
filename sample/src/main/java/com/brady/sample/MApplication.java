package com.brady.sample;

import android.app.Application;

import com.brady.libutil.UtilsManager;

/**
 * Created by zyb
 *
 * @date 2017/11/8
 * @description
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UtilsManager.init(this);
    }
}
