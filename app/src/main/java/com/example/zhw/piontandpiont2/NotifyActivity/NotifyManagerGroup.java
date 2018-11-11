package com.example.zhw.piontandpiont2.NotifyActivity;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.PareJson;

public class NotifyManagerGroup {
    public NotifyManagerGroup(String data, Handler mHandler){
        // this.data = data;
        //this.mHandler = mHandler;
        //向ManageGroupActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = PareJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 2;
        }else{
            msg.what = 1;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}
