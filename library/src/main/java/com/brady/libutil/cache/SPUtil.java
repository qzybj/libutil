package com.brady.libutil.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.brady.libutil.UtilsManager;
import com.brady.libutil.data.StringUtil;


/**
 * 当前类注释:当前为SharedPerferences进行封装基本的方法,
 * SharedPerferences已经封装成单例模式
 */
public class SPUtil {
    public static String SHARED_PATH = "frame_shared";
    private static SPUtil instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private SPUtil() {
        sp = UtilsManager.instance().
                getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 调用该方法前，先要设置存储名称
     * @param name
     */
    public static void renameStoreName(String name) {
        SHARED_PATH = name;
    }

    public static SPUtil instance() {
        if (instance == null ) {
            instance = new SPUtil();
        }
        return instance;
    }

    private SharedPreferences getSp() {
        if(sp==null){
            sp = UtilsManager.instance().getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
        }
        return sp;
    }
    private SharedPreferences.Editor getEditor() {
        if (editor==null){
            editor = getSp().edit();
        }
        return editor;
    }

    public long getLong(String key) {
        if (checkKey(key)) {
            return getSp().getLong(key, 0);
        }
        return 0;
    }
    public void setLong(String key, long value) {
        if (checkKey(key)) {
            getEditor().putLong(key, value).commit();
        }
    }

    public String getString(String key) {
        if (checkKey(key)) {
            return getSp().getString(key, null);
        }
        return null;
    }
    public void setString(String key, String value) {
        if (checkKey(key)) {
            getEditor().putString(key, value).commit();
        }
    }

    public int getInt(String key) {
        if (checkKey(key)) {
            return getSp().getInt(key, 0);
        }
        return 0;
    }
    public void setInt(String key, int value) {
        if (checkKey(key)) {
            getEditor().putInt(key, value).commit();
        }
    }

    public boolean getBoolean(String key) {
        if (checkKey(key)) {
            return getSp().getBoolean(key, false);
        }
        return true;
    }
    public void setBoolean(String key, boolean value) {
        if (checkKey(key)) {
            getEditor().putBoolean(key, value).commit();
        }
    }

    public float getFloat(String key) {
        if (checkKey(key)) {
            return getSp().getFloat(key, 0);
        }
        return 0;
    }
    public void setFloat(String key, Float value) {
        if (checkKey(key)) {
            getEditor().putFloat(key, value).commit();
        }
    }

    public boolean checkKey(String key) {
        return StringUtil.isNotEmpty(key);
    }

    public void clear() {
        getEditor().clear().commit();
    }
}
