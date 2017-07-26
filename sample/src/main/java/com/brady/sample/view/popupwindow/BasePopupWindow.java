package com.brady.sample.view.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;

import com.brady.sample.R;
import com.brady.sample.view.i.IClosePopupWindow;
import com.brady.sample.view.i.IPopupCallback;
import com.brady.sample.view.i.IPopupSubView;


public class BasePopupWindow implements OnDismissListener, OnClickListener, IClosePopupWindow {
	//透明度
	public static final float ALPHA_POPUPWINDOW = 0.6f;

	public PopupWindow popupWindow = null ;
	public Activity activity ;
	public IPopupCallback callback = null ;
	private RelativeLayout rootView = null ;
	//占位view
	private View takePlaceView = null ;
	//关闭View
	private View xView = null ;
	private OnDismissListener mOnDismissListener;

	public BasePopupWindow(Activity context, IPopupCallback callback) {
        this.activity = context ;
        this.callback = callback ;
        initial();
    }

    public BasePopupWindow(Activity context) {
        this(context,null);
    }
	
	private void initial(){
		popupWindow = new PopupWindow(activity);
		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setOnDismissListener(this);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}
	
	public void setBackground(int bg){
		popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(bg));
	}
	
	/**
	 * 初始化UI
	 */
	public View initUI(){
        return null ;
    }
	
	/**
	 * 获取popupwindow的最大高度，小于0时不限制
	 * @return
	 */
	public int getMaxHeight(){
		return -1;
	}
	
	/**
	 * 获取popupwindow的最大宽度，小于0时不限制
	 * @return
	 */
	public int getMaxWidth(){
		return -1;
	}
	
	private boolean checkMax(int max){
		if(max > 0 || max == RelativeLayout.LayoutParams.FILL_PARENT ||
				max == RelativeLayout.LayoutParams.WRAP_CONTENT){
			return true ;
		}
		return false ;
	}

	/**
	 * 添加子view
	 * @param subViewInfo
	 */
	public void setContentView(IPopupSubView subViewInfo){
		if( subViewInfo != null ){
			setContentView(subViewInfo.getSubView(),subViewInfo.getLayoutParams());
			subViewInfo.setPopupWindow(this);
		}
	}

	/**
	 * 是否显示关闭x
	 * @param isShow
	 */
	public void showXBtn(boolean isShow){
		if( isShow ){
			xView.setVisibility(View.VISIBLE);
		}else {
			xView.setVisibility(View.GONE);
		}
	}

	public void setContentView(View view){
		setContentView(view,null);
	}

	public void setContentView(View view, RelativeLayout.LayoutParams lp){
		if( popupWindow != null ){
			rootView = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.base_popupwindow, null);
			takePlaceView = rootView.findViewById(R.id.takeplace);
			xView = rootView.findViewById(R.id.popup_x);
			if( lp == null ){
				int width =  RelativeLayout.LayoutParams.MATCH_PARENT;
				int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
				if( checkMax(getMaxHeight())){
					height = getMaxHeight();
				}
				if( checkMax(getMaxWidth())){
					width = getMaxWidth();
				}
				lp = new RelativeLayout.LayoutParams(width,height);
			}
			rootView.addView(view,lp);
			rootView.setFocusable(true);//comment by danielinbiti,设置view能够接听事件，标注1
			rootView.setFocusableInTouchMode(true); //comment by danielinbiti,设置view能够接听事件 标注2
			rootView.setOnKeyListener(
					new View.OnKeyListener(){
						public boolean onKey(View v, int keyCode, KeyEvent event){
							if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
								return respKeyBack();
							}
							return false;
						}
					});
			takePlaceView.setClickable(true);
			takePlaceView.setOnClickListener(this);
			xView.setOnClickListener(this);
			setBgAlpha(ALPHA_POPUPWINDOW);
			popupWindow.setContentView(rootView);
		}
	}

	/**
	 * 设置背景透明度
	 * @param alpha
	 */
	public void setBgAlpha(float alpha){
		if( takePlaceView != null ){
			takePlaceView.setAlpha(alpha);
		}
	}

	/**
	 * 设置周边半透明区域是否可点击
	 * @param isClickable
	 */
	public void setOutsideClickable(boolean isClickable){
		takePlaceView.setEnabled(isClickable);
	}

	@Override
	public void onClick(View v) {
		if( v == xView ){
			closePopupAndCallback();
		}else if( v == takePlaceView &&
				!xView.isShown()){
			closePopupAndCallback();
		}
	}

	/**
	 * 关闭并通知关闭结果
	 */
	public void closePopupAndCallback(){
		if( callback != null ){
			if(!callback.onCallback(IPopupCallback.TYPE_CLICK_TAKEPLACE)){
				dismiss();
			}
		}else{
			dismiss();
		}
	}

	/**
	 * 响应点击物理返回键。
	 * 监听物理返回键，PopupWindow的background需要设置为null
	 * @return
     */
	public boolean respKeyBack(){
		return true ;
	}

	/**
	 * 关闭
	 */
	public void dismiss(){
		if( activity!= null && !activity.isFinishing() && popupWindow != null ){
			popupWindow.dismiss();
		}
	}
	
	public boolean isShowing(){
		return popupWindow.isShowing();
	}
	
	/**
	 * 显示dialog
	 * @param parent
	 */
	public void show(View parent){
		popupWindow.showAsDropDown(parent);
	}

	public void showAsDropDown(View parent){
		popupWindow.showAsDropDown(parent);
	}

	/**
	 * 显示dialog
	 * @param parent
	 */
	public void showFullScreen(View parent){
		popupWindow.showAtLocation(parent, Gravity.FILL,0,0);
	}

	/**
	 * 全屏显示dialog
	 * @param parent
	 */
	public void AllShow(View parent){
		popupWindow.showAtLocation(parent.getRootView(), Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 关闭时，通知parent响应
	 */
	public void onDismiss() {
		if( callback != null ){
			callback.onCallback(IPopupCallback.TYPE_AUTO);
		}
		if(mOnDismissListener!=null){
			mOnDismissListener.onDismiss();
		}
	}

	@Override
	public void closePopupWindow() {
		dismiss();
	}

	public void setDismissListener(OnDismissListener listener){
		this.mOnDismissListener =listener;
	}
}