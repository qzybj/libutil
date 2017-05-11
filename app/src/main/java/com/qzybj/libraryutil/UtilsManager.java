package com.qzybj.libraryutil;

import android.app.Application;


public class UtilsManager {
    private static Application instance;
    public static Application instance(){
        return instance;
    }

    public static void init(Application application) {
        instance = application;
    }
}
