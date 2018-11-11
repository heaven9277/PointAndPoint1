package com.example.zhw.piontandpiont2.Threadpack;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.PareJson;

public class GroupInfoThread extends Thread {
    String data;
    Handler mHandler;
    public GroupInfoThread(String data, Handler mHandler){
        this.data =data;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        super.run();
        //向ChatActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = PareJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 16;
        }else{
            msg.what = 15;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
        System.out.println("开始返回群资料信息");
    }
}
