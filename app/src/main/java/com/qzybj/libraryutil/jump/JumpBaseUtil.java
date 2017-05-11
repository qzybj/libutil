package com.qzybj.libraryutil.jump;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qzybj.libraryutil.data.IntentUtil;


public class JumpBaseUtil {

    public static void go(Context con, IJumpInfo bean){
        if(con!=null&&bean!=null){
            Intent intent = new Intent();
            intent.setClassName(con,bean.getTarget().getName());
            if(IntentUtil.isNotEmpty(bean.getArgs())){
                intent.putExtras(bean.getArgs());
            }
            con.startActivity(intent);
        }
    }

    public interface IJumpInfo {
        Class getTarget();
        String getTitle();
        Bundle getArgs();
    }
}