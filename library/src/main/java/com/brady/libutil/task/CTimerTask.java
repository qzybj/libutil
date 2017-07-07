package com.brady.libutil.task;

import android.os.Handler;


/**
 * 定时任务
 */
public class CTimerTask implements Runnable {

    private long mIntervalTime;
    private Callback mCallBack;
    private boolean isBreak=false;

    private Handler handler = new Handler();

    /**
     *
     * @param intervalTime 触发onTick方法的间隔,这篇
     * @param callback 回调传递
     */
    public CTimerTask(long intervalTime, Callback callback) {
        this.mIntervalTime = intervalTime;
        this.mCallBack = callback;
    }


    @Override
    public void run() {
        onTick();
        handler.postDelayed(this, mIntervalTime);
        isBreak = true;
    }

    private void onTick() {
        if(mCallBack!=null&&isBreak){
            mCallBack.onTick();
        }
    }

    /**结束任务*/
    public void finish() {
        isBreak = false;
        if(handler!=null){
            handler.removeCallbacks(this);
        }
    }

    public interface Callback {
        /**
         * 间隔触发回调
         */
        void onTick();
    }
}