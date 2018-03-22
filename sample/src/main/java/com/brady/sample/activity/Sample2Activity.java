package com.brady.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.brady.libutil.view.TextViewUtil;
import com.brady.sample.R;

/**
 * Created by zyb
 *
 * @date 2017/11/8
 * @description
 */
public class Sample2Activity extends Activity implements View.OnClickListener {

    /**示例格式：2017/11/7 16:26:13*/
    public static String FORMAT_YMDHMS_SLASH = "yyyy/MM/dd HH:mm:ss";

    private TextView submitTv;
    private TextView consoleTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        initView();
    }

    private void initView() {
        submitTv = (TextView) findViewById(R.id.tv_submit);
        consoleTv = (TextView) findViewById(R.id.tv_console);
        submitTv.setOnClickListener(this);
    }

    private void setConsoleMsg(String msg){
        TextViewUtil.setValue(consoleTv,msg);
    }

    @Override
    public void onClick(View v) {
        if(submitTv == v){
            setConsoleMsg("Sample2Activity");
        }
    }
}