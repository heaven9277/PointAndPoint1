package com.example.zhw.piontandpiont2.Threadpack;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.PareJson;


/*
通知首页信息的线程
 */
public class FirstActiviityThread  extends Thread{
    String data;
    Handler mHandler;
    public FirstActiviityThread(String data,Handler mHandler){
        this.data = data;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        super.run();
        //向HomeActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = PareJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 8;
        }else{
            msg.what = 7;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}
