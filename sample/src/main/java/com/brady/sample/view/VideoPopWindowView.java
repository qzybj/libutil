package com.brady.sample.view;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.brady.libutil.log.CLog;
import com.brady.libutil.view.ViewUtil;
import com.brady.sample.R;
import com.brady.sample.view.i.IPopupCallback;
import com.brady.sample.view.i.IPopupSubView;
import com.brady.sample.view.popupwindow.BasePopupWindow;


/**
 * Created by Clair
 *
 * @date 2017-03-28
 * @description 支付方式弹窗,播放视频
 */
public class VideoPopWindowView implements IPopupSubView, View.OnClickListener,
        MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {
    private LinearLayout rootView = null;
    private Activity parent;
    private BasePopupWindow popupWindow = null;
    private int callbackCode;
    private IPopupCallback mCallback = null;
    //图片加载
    private VideoView animationVideoView = null;
    private String animationPath ="";
    private MediaController mc;

    public VideoPopWindowView(Activity parent) {
        this.parent = parent;
        initUI();
    }

    public VideoPopWindowView(Activity parent, int callbackCode, IPopupCallback callback) {
        this.parent = parent;
        this.mCallback = callback;
        this.callbackCode = callbackCode;
        initUI();
    }

    private void initUI() {
        rootView = new LinearLayout(parent);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        View adapterView = ViewUtil.loadView(parent, R.layout.animationview_video_memberopen);

        animationVideoView = ViewUtil.getView(adapterView,R.id.vv_animation);
        mc = new MediaController(parent);
        animationVideoView.setMediaController(mc);
        mc.setMediaPlayer(animationVideoView);
        animationVideoView.setOnPreparedListener(this);
        animationVideoView.setOnCompletionListener(this);
        if (!TextUtils.isEmpty(animationPath)) {
            loadImage(animationPath);
        }
        //animationIV.setOnClickListener(this);
        rootView.addView(adapterView);
        rootView.setOnClickListener(this);
    }

    @Override
    public void setPopupWindow(BasePopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    @Override
    public View getSubView() {
        return rootView;
    }

    public void setVideoPath(String path){
        this.animationPath = path;
    }

    /**
     * 加载gif动画处理
     * @param uri
     */
    public void loadImage(Uri uri) {
        if(uri!=null){
            animationVideoView.setVideoURI(uri);
//            animationVideoView.start();
//            animationVideoView.requestFocus();
        }
    }

    /**
     * 加载gif动画处理
     * @param gifUrl
     */
    public void loadImage(String gifUrl) {
        if (!TextUtils.isEmpty(gifUrl)) {
            animationVideoView.setVideoPath(gifUrl);
//            animationVideoView.start();
//            animationVideoView.requestFocus();
        }
    }

    @Override
    public RelativeLayout.LayoutParams getLayoutParams() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        return lp;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        CLog.d("animationtime","video end");
        closeTimeTask(300);
    }

    private void closeTimeTask(int delayMillis){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationFinish();
            }
        }, delayMillis);
    }

    private void animationFinish(){
        popupWindow.dismiss();
        if(mCallback!=null){
            mCallback.onCallback(callbackCode);
        }
    }
}