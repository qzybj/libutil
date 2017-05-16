package com.qzybj.libutil.view;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.qzybj.libutil.UtilsManager;

/**
 * 获取一些常用的View控件
 */
public class ViewBuilder {

	/**
	 * 生成一个LinearLayout布局，宽为LayoutParams.MATCH_PARENT,高为LayoutParams.WRAP_CONTENT
	 * @return
	 */
	public static LinearLayout buildLinearLayout() {
		LinearLayout layout = new LinearLayout(UtilsManager.instance());
		LayoutParams lParams =new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(lParams);
		layout.setBackgroundColor(
				UtilsManager.instance().getResources().getColor(android.R.color.transparent));
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}

	/**
	 * 用来放置项部的动态模板，实例化线性布局并设置垂直方向
	 * @return LinearLayout
	 */
	public static LinearLayout buildVLayoutByWeight(int weight,boolean isVertical) {
		LinearLayout layout = new LinearLayout(UtilsManager.instance());
		LayoutParams params;
		if(isVertical){
			params = new LayoutParams(LayoutParams.MATCH_PARENT, 0,weight);
		}else{
			params = new LayoutParams(0, LayoutParams.MATCH_PARENT,weight);
		}
		layout.setLayoutParams(params);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}

	/**
	 * 生成ImageView,weight为1
	 * @return ImageView
	 */
	public static ImageView buildImageViewByWeight() {
		return buildImageViewByWeight(1);
	}

	/**
	 *
	 * 生成ImageView,weight为1
	 * @param weight
	 * @return
	 */
	public static ImageView buildImageViewByWeight(int weight) {
		ImageView imageview = new ImageView(UtilsManager.instance());
		LayoutParams imgParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,weight);
		imageview.setLayoutParams(imgParams);
		imageview.setAdjustViewBounds(true);
		imageview.setScaleType(ScaleType.FIT_CENTER);
		imageview.setBackgroundColor(
				UtilsManager.instance().getResources().getColor(android.R.color.transparent));
		return imageview;
	}

	/**
	 *
	 * 图片间的分隔线
	 * @param padding 分隔线宽度(小于等于0设为默认宽度)
	 * @param isHorizontal true 横线  false竖线
	 * @return 分隔线View
	 */
	public static View buildDivideView( boolean isHorizontal, int padding) {
		View divide = new View(UtilsManager.instance());
		if (padding<0) {
			padding =0;
		}
		LayoutParams divideParams;
		if (isHorizontal) {
			divideParams = new LayoutParams(LayoutParams.MATCH_PARENT,padding);
		}else {
			divideParams = new LayoutParams(padding, LayoutParams.MATCH_PARENT);
		}
		divide.setBackgroundResource(android.R.color.transparent);
		divide.setLayoutParams(divideParams);
		return divide;
	}

}