package com.qzybj.libraryutil.task;

import android.os.CountDownTimer;

/**
 * 倒计时类 - 用于验证码计时
 */
public class CountDownTimerTask extends CountDownTimer {
    private CountDownTimerCallback mCallBack;

    /**
     *
     * @param millisInFuture 倒计时总计
     * @param countDownInterval 触发onTick方法的间隔
     * @param callback 回调传递
     */
    public CountDownTimerTask(long millisInFuture, long countDownInterval, CountDownTimerCallback callback) {
        super(millisInFuture, countDownInterval);
        this.mCallBack = callback;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(mCallBack!=null){
            mCallBack.onTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if(mCallBack!=null){
            mCallBack.onFinish();
        }
    }

    public interface CountDownTimerCallback{
        void onTick(long millisUntilFinished);
        void onFinish();
    }
}