package com.brady.libutil.view;

import android.widget.Toast;

import com.brady.libutil.UtilsManager;
import com.brady.libutil.data.StringUtil;


public class ToastUtil {

	public static void showToast(int strResId){
		showToast(UtilsManager.instance().getString(strResId),
				false);
	}

	public static void showToast(String msg){
		showToast(msg,false);
	}

	public static void showToast(String msg, boolean isLongTime){
		if(StringUtil.isNotEmpty(msg)){
			Toast.makeText(
					UtilsManager.instance(),
					msg,
					isLongTime? Toast.LENGTH_LONG: Toast.LENGTH_SHORT)
					.show();
		}
	}
}
