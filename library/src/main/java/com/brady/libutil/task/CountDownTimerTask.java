package com.brady.libutil.task;

import android.os.CountDownTimer;


/**
 * 倒计时任务
 */
public class CountDownTimerTask extends CountDownTimer {
    private Callback mCallBack;

    /**
     *
     * @param millisInFuture 倒计时总计
     * @param countDownInterval 触发onTick方法的间隔
     * @param callback 回调传递
     */
    public CountDownTimerTask(long millisInFuture, long countDownInterval, Callback callback) {
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
    public interface Callback {
        /**
         * 间隔触发回调
         */
        void onTick(long millisUntilFinished);

        /**
         * 定时任务结束回调
         */
        void onFinish();
    }

}