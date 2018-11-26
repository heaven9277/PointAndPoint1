package com.example.zhw.piontandpiont2.NotifyActivity;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.ParseJson;

public class NotifyOutGroup {
    public NotifyOutGroup(String data, Handler mHandler){
        // this.data = data;
        //this.mHandler = mHandler;
        //向GroupInfoActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = ParseJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 20;
        }else{
            msg.what = 19;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}
