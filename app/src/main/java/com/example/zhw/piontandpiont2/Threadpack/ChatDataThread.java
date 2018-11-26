package com.example.zhw.piontandpiont2.Threadpack;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.ParseJson;

public class ChatDataThread extends Thread {
    String data;
    Handler mHandler;
    public ChatDataThread(String data,Handler mHandler){
        this.data =data;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        super.run();
        //向ChatActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = ParseJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 12;
        }else{
            msg.what = 11;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}
