package com.brady.libutil.device;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.brady.libutil.log.CLog;

/**
 * 键盘快捷键 操作
 */
public class KeyBoardUtil {

    /**
     * 显示软键盘
     * @param view
     */
    public static void showInputMethod(View view) {
        if(view!=null){
            InputMethodManager imm =
                    (InputMethodManager) view.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    /**
     * 显示软键盘
     * @param context
     */
    public static void showInputMethod(Context context) {
        if(context!=null){
            InputMethodManager imm =
                    (InputMethodManager) context
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 强制隐藏软键盘
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity != null&&!activity.isFinishing()) {
            try {
                View v = activity.getWindow().peekDecorView();
                if (v != null && v.getWindowToken() != null) {
                    InputMethodManager imm = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            } catch (Exception e) {
                CLog.e(e.toString());
            }
        }
    }
    /** 多少时间后显示软键盘 */
    public static void showInputMethod(final View view, long delayMillis) {
        // 显示输入法
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        showInputMethod(view);
                    }
                }, delayMillis);
    }

    /**
     * 显示完成
     * @param et
     * @param callback
     */
    public static void done(final EditText et, ShortcutKeyCallback callback){
        to(et, EditorInfo.IME_ACTION_DONE,callback);
    }

    /**
     * 显示下一个
     * @param et
     * @param callback
     */
    public static void next(final EditText et, ShortcutKeyCallback callback){
        to(et, EditorInfo.IME_ACTION_NEXT,callback);
    }

    /**
     * 显示下一个
     * @param et
     */
    public static void next(final EditText et){
        next(et, null);
    }

    /**
     * 显示搜索
     * @param et
     * @param callback
     */
    public static void search(final EditText et, ShortcutKeyCallback callback){
        to(et, EditorInfo.IME_ACTION_SEARCH,callback);
    }

    /**
     * 显示下一个
     * @param et
     */
    public static void search(final EditText et){
        search(et, null);
    }

    /**
     * 显示指定样式
     * @param et
     * @param callback
     *  点击回调
     */
    private static void to(final EditText et, final int imeOptions,
                           final ShortcutKeyCallback callback){
        if( et == null ){
            return ;
        }
        et.setSingleLine();
        et.setImeOptions(imeOptions);
        if( callback != null ){
            et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == imeOptions ||
                            (event!= null && event.getAction() == KeyEvent.KEYCODE_ENTER)) {
                        callback.doAction(et);
                        return true ;
                    }
                    return false;
                }
            });
        }
    }

    public interface ShortcutKeyCallback{
        void doAction(EditText target);
    }
}
