package com.example.zhw.piontandpiont2.NotifyActivity;

import android.os.Handler;
import android.os.Message;

import com.example.zhw.piontandpiont2.Util.PareJson;

public class NotifyEditGroup {
    //String data;
   // Handler mHandler;
    public NotifyEditGroup(String data, Handler mHandler){
       // this.data = data;
        //this.mHandler = mHandler;
        //向EidtGroupActivity发送数据
        Message msg = mHandler.obtainMessage();
        String status = PareJson.getJsonStatus(data);
        if (status.equals("fail")){
            msg.what = 18;
        }else{
            msg.what = 17;
        }
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}
