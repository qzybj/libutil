package com.brady.libutil.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.brady.libutil.data.StringUtil;


public class TextViewUtil {

	/**
	 * 用于TextView设置文字颜色
	 * @param cs
	 * @param start
	 * @param end
	 * @param color
	 */
	public SpannableStringBuilder getSpanBuilder(CharSequence cs, int start, int end,@ColorInt int color) {
		if(cs!=null&&cs.length()>0){
			SpannableStringBuilder builder = new SpannableStringBuilder(cs);
			//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
			ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
			builder.setSpan(redSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			return builder;
		}
		return null;
	}

	/**
	 * 设置TextView的值，
	 * @param tv
	 * @param s
	 */
	public static void setValue(TextView tv, String s){
		setValue(tv,s,false);
	}

	/**
	 * 设置TextView的值，
	 * @param tv
	 * @param s
	 * @param isGone 为true时，如果text为空则隐藏控件
	 */
	public static void setValue(TextView tv, String s,boolean isGone){
		if (tv!=null) {
			if (StringUtil.isNotEmpty(s)) {
				tv.setVisibility(View.VISIBLE);
				tv.setText(s);
			}else{
				if(isGone){
					tv.setVisibility(View.GONE);
				}
			}
		}
	}

	/**
	 * 获取控件值
	 * @param tv
	 * @return
	 */
	public static String getValue(TextView tv){
		return getValue(tv,"");
	}

	/**
	 * 获取控件值
	 * @param tv
	 * @param text 控件值为空时的默认值
	 * @return
	 */
	public static String getValue(TextView tv, String text){
		if (tv!=null) {
			String s  = tv.getText().toString().trim();
			if (StringUtil.isNotEmpty(s)) {
				return s;
			}
		}
		return text;
	}

	/**
	 * 设置图片(默认为左边)
	 * @param tv
	 * @param d 为null时，将重置Drawable
	 * @param padding 边距
	 * @param o   方向  left 0,top 1,right 2 bottom 3
	 */
	public static void setDrawable(TextView tv, Drawable d, int padding,int o) {
		if (tv!=null){
			if (d != null) {
				tv.setCompoundDrawablePadding(padding);
				d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());// 这一步必须要做,否则不会显示.
				if(o==0){
					tv.setCompoundDrawables(d,null,null,null);
				}else if(o==1){
					tv.setCompoundDrawables(null,d,null,null);
				}else if(o==2){
					tv.setCompoundDrawables(null,null,d,null);
				}else if(o==3){
					tv.setCompoundDrawables(null,null,null,d);
				}
			}else{
				tv.setPadding(padding,0,0,0);
				tv.setCompoundDrawables(null,null,null,null);
			}
		}
	}
}