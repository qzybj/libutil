package com.brady.sample.view.i;

import android.view.View;
import android.widget.RelativeLayout;

import com.brady.sample.view.popupwindow.BasePopupWindow;


/**
 *
 * @author Cuckoo
 * @date 2017-03-21
 * @description
 *  定义popupwindow子view
 */

public interface IPopupSubView {
    /**
     * 传递PopupWindow给子view
     * @param popupWindow
     */
    void setPopupWindow(BasePopupWindow popupWindow);

    /**
     * 获取子view
     * @return
     */
    View getSubView();

    /**
     * 获取子view的LayoutParams
     * @return
     */
    RelativeLayout.LayoutParams getLayoutParams();
}
