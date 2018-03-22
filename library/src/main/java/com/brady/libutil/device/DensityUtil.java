package com.brady.libutil.device;

import android.util.TypedValue;
import com.brady.libutil.UtilsManager;

public class DensityUtil {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px( float dpValue) {
		final float scale = UtilsManager.instance().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(float pxValue) {
		final float scale = UtilsManager.instance().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static float px2sp( float pxValue) {
		return (pxValue / UtilsManager.instance().getResources().getDisplayMetrics().scaledDensity);
	}

	/**
	 * dp转px
	 * @param dpVal
	 * @return
	 */
	public static int dp2px(float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
				UtilsManager.instance().getResources().getDisplayMetrics());
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param spValue （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px( float spValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				UtilsManager.instance().getResources().getDisplayMetrics());
	}
}