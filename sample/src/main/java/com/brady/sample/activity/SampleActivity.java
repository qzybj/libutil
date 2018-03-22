package com.brady.sample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.brady.libutil.data.ListUtil;
import com.brady.libutil.data.StringUtil;
import com.brady.libutil.device.NetUtil;
import com.brady.libutil.io.FileUtil;
import com.brady.libutil.view.TextViewUtil;
import com.brady.sample.R;
import com.brady.sample.model.Person;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyb
 *
 * @date 2017/11/8
 * @description
 */
public class SampleActivity extends Activity implements View.OnClickListener {

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
            NetUtil.getCurrentNetworkState(this);
            NetUtil.getCurrentNetworkType(this);
            NetUtil.getNetConnectSubType(this);
            NetUtil.getNetConnectType(this);
            NetUtil.isMobileByType(this);
            NetUtil.isNetConnected(this);
            NetUtil.isWifiConnected(this);
            FileUtil.getExternalCacheDir(this);
        }
    }

    private void testMap(){
        ArrayList<Person> list = new ArrayList<Person>();
        list.add(new Person("001","aaa",2));
        list.add(new Person("001","aaa",2));
        list.add(new Person("001","aaa",2));
        list.add(new Person("002","bbb",2));
        list.add(new Person("002","bbb",5));
        list.add(new Person("003","ccc",2));
        list.add(new Person("003","ccc",6));
        List<Person> list1 = mergeSameDataBySkuId(list);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list1.size(); i++) {
            sb.append(list1.get(i).toString());
        }
        setConsoleMsg(sb.toString());
    }

    private void testTime(){
        String content = "this is a test msg!!!";
        content += getCurrentTime(FORMAT_YMDHMS_SLASH);
        setConsoleMsg(content);
    }

    /**
     * 获取当前时间，格式为可选
     * @return
     */
    public String getCurrentTime(String pattern){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }


    /**
     * 合并相同SkuId的数据为一条
     * @param list
     * @return
     */
    public static List<Person> mergeSameDataBySkuId(List<Person> list) {
        ArrayList<Person> returnList = new ArrayList<Person>();
        Map<String,Person> map = mergeSameSkuId(list);
        Collection<Person> valueCollection = map.values();
        returnList = new ArrayList<Person>(valueCollection);
        return returnList;
    }

    /**
     * 转换List数据为Map(SkuId:Person)
     * @param list
     * @return
     */
    public static Map<String,Person> mergeSameSkuId(List<Person> list) {
        Map<String, Person> map = new HashMap<String, Person>();
        if (ListUtil.isNotEmpty(list)) {
            for (Person item : list) {
                if (item != null && StringUtil.isNotEmpty(item.getId())
                        && item.getBoxNum() > 0) {
                    if (map.containsKey(item.getId())) {
                        Person sourceBean = map.get(item.getId());
                        int num = sourceBean.getBoxNum() + item.getBoxNum();
                        sourceBean.setBoxNum(num);
                        map.put(item.getId(), sourceBean);
                    } else {
                        map.put(item.getId(), item);
                    }
                }
            }
        }
        return map;
    }
}