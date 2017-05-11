package com.qzybj.libraryutil.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 获取一些常用的View控件
 */
public class ViewBuilder {

	public static LinearLayout getLinearLayout(Context con) {
		LinearLayout layout = new LinearLayout(con);
		LayoutParams imgParams =new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(imgParams);
		layout.setBackgroundColor(con.getResources().getColor(android.R.color.transparent));
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}
	/**
	 * weight为1的ImageView
	 * @return ImageView
	 */
	public static ImageView getViewByWeight(Context con) {
		ImageView imageview = new ImageView(con);
		LayoutParams imgParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1);
		imageview.setLayoutParams(imgParams);
		imageview.setAdjustViewBounds(true);
		imageview.setScaleType(ScaleType.FIT_CENTER);//CENTER_CROP
		imageview.setBackgroundColor(con.getResources().getColor(android.R.color.transparent));
		return imageview;
	}

	/**
	 *
	 * 图片间的分隔线
	 * @param padding 分隔线宽度(小于等于0设为默认宽度)
	 * @param isHorizontal true 横线  false竖线
	 * @return 分隔线View
	 */
	public static View getDivideView(Context con, boolean isHorizontal, int padding) {
		View divide = new View(con);
		if (padding<0) {
			padding =0;
		}
		LayoutParams divideParams;
		if (isHorizontal) {
			divideParams = new LayoutParams(LayoutParams.MATCH_PARENT,padding);
		}else {
			divideParams = new LayoutParams(padding, LayoutParams.MATCH_PARENT);
		}
		divide.setBackgroundResource(android.R.color.transparent);//transparent color_F1F1F1
		divide.setLayoutParams(divideParams);
		return divide;
	}


	/**
	 * 用来放置项部的动态模板，实例化线性布局并设置垂直方向
	 * @return LinearLayout
	 */
	public static LinearLayout getVLayoutByWeight(Context con, int weight) {
		LinearLayout layout = new LinearLayout(con);
		LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT,weight);
		layout.setLayoutParams(params);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}
	/**
	 * 用来包裹图片的布局，加外围线
	 * @return LinearLayout
	 */
	public static LinearLayout getBorderLayout(Context con) {
		LinearLayout layout = new LinearLayout(con);
		LayoutParams imgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(imgParams);
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}
}