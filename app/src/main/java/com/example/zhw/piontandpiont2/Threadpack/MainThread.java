package com.example.zhw.piontandpiont2.Threadpack;


import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.ParseJson;

//通知首页的UI的线程
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
        String status = ParseJson.getJsonStatus(text);
        System.out.println("请求状态："+status +"??"+status.equals("fail"));
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
