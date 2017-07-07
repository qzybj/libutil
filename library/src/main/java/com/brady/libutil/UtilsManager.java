package com.brady.libutil;

import android.app.Application;


public class UtilsManager {
    private static Application instance;
    public static Application instance(){
        return instance;
    }

    /**
     * 使用前一定要初始化一下
     * @param application
     */
    public static void init(Application application) {
        instance = application;
    }
}
