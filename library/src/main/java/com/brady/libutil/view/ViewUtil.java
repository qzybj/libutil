package com.brady.libutil.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.brady.libutil.CLog;


/**
 * View操作的辅助类
 */
public class ViewUtil {

    /**
     * 加载xml
     * @param context
     * @param resID
     * @return
     */
    public static <T> T loadView(Context context, int resID){
        try{
            return (T) LayoutInflater.from(context).inflate(resID, null);
        }catch (Exception e){
            CLog.e(e);
            return null;
        }
    }

    /**
     * 采用泛型的方式查找View，在使用的时候不用强转
     * @param id
     * @param rootView
     * @return
     */
    public static <T> T getView(View rootView,int id){
        try{
            return (T)rootView.findViewById(id);
        }catch (Exception e){
            CLog.e(e);
            return null;
        }

    }

    /**
     * 采用泛型的方式查找View，在使用的时候不用强转
     * @param tag
     * @param rootView
     * @return
     */
    public static <T> T getViewByTag(View rootView,Object tag){
        try{
            return (T)rootView.findViewWithTag(tag);
        }catch (Exception e){
            CLog.e(e);
            return null;
        }
    }

}