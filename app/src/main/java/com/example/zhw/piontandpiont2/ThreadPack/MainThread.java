package com.example.zhw.piontandpiont2.ThreadPack;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.PareJson;


public class MainThread extends Thread {
    public String text;
    Handler mhandler;
    public MainThread(String text,Handler handler){
        this.text = text;
        this.mhandler = handler;
    }
    @Override
    public void run() {
        super.run();
        //向MianActivity发送数据
        Message msg = mhandler.obtainMessage();
        String status = PareJson.getJsonStatus(text);
        if (status.equals("fail")){
            msg.what = 5;
        }else{
            msg.what = 6;
        }
        msg.obj = text;
        mhandler.sendMessage(msg);
        System.out.println("发送消息给MainActivity");
    }

}