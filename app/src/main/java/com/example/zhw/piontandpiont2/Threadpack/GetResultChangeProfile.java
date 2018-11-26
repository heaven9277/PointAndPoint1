package com.example.zhw.piontandpiont2.Threadpack;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.ParseJson;

public class GetResultChangeProfile extends Thread {

    String data;
    Handler mHandler;
    public GetResultChangeProfile(String data, Handler mHandler){
        this.data =data;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        super.run();
        //向ChatActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = ParseJson.getJsonStatus(data);
        msg.what = 18;
        msg.obj = data;
        mHandler.sendMessage(msg);
    }

}
