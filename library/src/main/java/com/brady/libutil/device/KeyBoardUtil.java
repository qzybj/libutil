package com.brady.libutil.device;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 键盘快捷键 操作
 */
public class KeyBoardUtil {

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
