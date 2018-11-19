package com.example.zhw.piontandpiont2.NotifyActivity;

import android.os.Handler;
import android.os.Message;

public class NotifyChat {
   public NotifyChat(String data, Handler mHandler){
       Message msg = mHandler.obtainMessage();
       msg.what = 1;
       msg.obj = data;
       mHandler.sendMessage(msg);
   }
}
