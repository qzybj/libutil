package com.qzybj.libraryutil.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qzybj.libraryutil.data.StringUtil;


public class TextViewUtil {

	public void setTextViewTextColor(Context con, TextView tv, int start, int end) {
		SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
		//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
		//con.getResources().getColor(R.color.color_000000);
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
		builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(builder);
	}
	

	public static void setTextViewValueAndVisiable(TextView tv, String text){
		if (tv!=null) {
			if (StringUtil.isEmpty(text)) {
				tv.setVisibility(View.GONE);
			}else {
				tv.setVisibility(View.VISIBLE);
				tv.setText(text);
			}
		}
	}
	public static void setTextViewValue(TextView tv, String text){
		if (tv!=null) {
			if (StringUtil.isNotEmpty(text)) {
				tv.setText(text);
			}
		}
	}
	
	public static String getTextViewValue(TextView tv){
		return getTextViewValue(tv,"");
	}
	
	public static String getTextViewValue(TextView tv, String text){
		if (tv!=null) {
			String returnStr  = tv.getText().toString().trim();
			if (StringUtil.isNotEmpty(returnStr)) {
				return returnStr;
			}
		}
		return text;
	}
	
	public static String getEditTextValue(EditText et){
		if (et!=null) {
			String returnStr  = et.getText().toString().trim();
			if (StringUtil.isNotEmpty(returnStr)) {
				return returnStr;
			}
		}
		return "";
	}
	/**设置图片(默认为左边)*/
	public static void setTextViewDrawable(TextView tv, Drawable drawable, int padding) {
		if (tv!=null||tv.isShown()){
			if (drawable != null) {
				tv.setCompoundDrawablePadding(padding);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 这一步必须要做,否则不会显示.
				tv.setCompoundDrawables(drawable,null,null,null);
			}else{
				tv.setPadding(padding,0,0,0);
				tv.setCompoundDrawables(null,null,null,null);
			}
		}
	}
	public static String getText(TextView tv) {
		if(tv!=null){
			return tv.getText().toString().trim();
		}
		return null;
	}

}