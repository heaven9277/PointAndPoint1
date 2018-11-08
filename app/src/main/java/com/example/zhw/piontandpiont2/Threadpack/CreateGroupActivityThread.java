package com.example.zhw.piontandpiont2.Threadpack;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.PareJson;

/*
通知创建群信息的线程
 */
public class CreateGroupActivityThread extends Thread {
    String data;
    Handler mHandler;
    public CreateGroupActivityThread(String data,Handler mHandler){
        this.data =data;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        super.run();
        //向CreateActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = PareJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 10;
        }else{
            msg.what = 9;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}
