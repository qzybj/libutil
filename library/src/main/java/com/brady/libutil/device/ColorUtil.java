package com.brady.libutil.device;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.brady.libutil.UtilsManager;


public class ColorUtil {

    public static final int NONE = -1;

    public static int getColorIntValue(String colorStr){
        try {
            return Color.parseColor(colorStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**获取指定的颜色值的16进制表现形式。0xAARRGGBB*/
    public static int getResourceColor( int colorId) {
        if (colorId > 0) {
            try {
                return UtilsManager.instance().getResources().getColor(colorId);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        return NONE;
    }

    /**
     * 代码生成选择器
     * @param idNormal 默认图片id
     * @param idPressed 触摸时图片id
     * @param idFocused 获得焦点时图片id
     * @param idUnable 没有选中时图片id
     * @return
     */
    public static StateListDrawable newSelector( int idNormal, int idPressed, int idFocused, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == NONE ? null : UtilsManager.instance().getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == NONE ? null : UtilsManager.instance().getResources().getDrawable(idPressed);
        Drawable focused = idFocused == NONE ? null : UtilsManager.instance().getResources().getDrawable(idFocused);
        Drawable unable = idUnable ==NONE ? null : UtilsManager.instance().getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

    /**
     * 控件选择器
     *
     * @param idNormal 默认图片id
     * @param idFocused 按压时图片id
     * @return
     */
    public static StateListDrawable newSelector(int idNormal, int idFocused){
        StateListDrawable bg=new StateListDrawable();
        Drawable normal = idNormal == NONE ? null : UtilsManager.instance().getResources().getDrawable(idNormal);
        Drawable focused = idFocused == NONE ? null : UtilsManager.instance().getResources().getDrawable(idFocused);
        bg.addState(new int[] { android.R.attr.state_focused, android.R.attr.state_enabled }, focused);
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        bg.addState(new int[] {}, normal);
        return bg;
    }

    /** 对TextView设置不同状态时其文字颜色。 */
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        try {
            int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
            int[][] states = new int[6][];
            states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
            states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
            states[2] = new int[] { android.R.attr.state_enabled };
            states[3] = new int[] { android.R.attr.state_focused };
            states[4] = new int[] { android.R.attr.state_window_focused };
            states[5] = new int[] {};
            ColorStateList colorList = new ColorStateList(states, colors);
            return colorList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}