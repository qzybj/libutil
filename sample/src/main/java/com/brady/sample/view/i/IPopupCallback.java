

package com.brady.sample.view.i;
/**
 * 
 * @author Cuckoo
 * @date 2017-03-06
 * @description
 * 		回调
 *
 */
public interface IPopupCallback {
	/*dismiss dialog或者popupwindow*/
	static final int TYPE_AUTO_DISMISS = 10000 ;
	/*长按时的回调*/
	static final int TYPE_LONG_CLICK = 20000 ;
	/*点击时回调*/
	static final int TYPE_CLICK = 30000 ;
	/*点击listview的footerview*/
	static final int TYPE_CLICK_FOOTER = 40000 ;
	/*输入完成*/
	static final int TYPE_INPUT_COMPLETE = 50000 ;
	/*点击单选项*/
	static final int TYPE_CLICK_CHOOSE_ITEM = 60000; 
	static final int TYPE_AUTO = 80000 ;
	//点击占位区域关闭PopupWindow
	int TYPE_CLICK_TAKEPLACE = 90000 ;
	//点击取消关闭PopupWindow
	int TYPE_CLICK_CANCEL = 90001 ;

	/**
	 * 
	 * @param callbackType
	 * @param params
	 */
	boolean onCallback(int callbackType, Object... params);

}
