package com.brady.libutil.animation;


import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * 动画工具类
 */
public final class AnimationUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private AnimationUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 默认动画持续时间
     */
    public static final long DEFAULT_ANIMATION_DURATION = 400;


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long durationMillis, AnimationListener animationListener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, durationMillis,animationListener);
    }
    /**
     * 获取一个旋转动画
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点X轴坐标相对类型
     * @param pivotXValue       旋转中心点X轴坐标
     * @param pivotYType        旋转中心点Y轴坐标相对类型
     * @param pivotYValue       旋转中心点Y轴坐标
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long durationMillis, AnimationListener animationListener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees,toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }

    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度 ,0.0f
     * @param toAlpha           结束时的透明度都,1.0f
     * @param durationMillis    持续时间 ,DEFAULT_ANIMATION_DURATION
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis, AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            alphaAnimation.setAnimationListener(animationListener);
        }
        return alphaAnimation;
    }

    /**
     *
     * 获取一个放大动画
     * @param durationMillis   时间
     * @param animationListener  监听
     * @param isZoomOut true 缩小 false 放大
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getScaleAnimation(long durationMillis, AnimationListener animationListener, boolean isZoomOut) {
        ScaleAnimation scaleAnimation;
        if(isZoomOut){
            scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,0.0f, ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        }else{
            scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,1.0f, ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        }
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }
}
