package com.example.zhw.piontandpiont2.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import static com.example.zhw.piontandpiont2.Networksockets.WsManager.getInstance;


//服务类

public class MyServer extends Service {
    WsManager wsManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyIBider();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    public class MyIBider extends Binder {
        //自定义操作
        //初始化WEBASOCKET
        public void initWebsocket(){
            wsManager = getInstance();
            if (wsManager != null){
                //进行连接
                wsManager.init();
            }
        }
        //连接Websocket的方法，返回一个boolean类型
        //得到一个websocket的方法
        //定义两个参数的方法来接受输入框的用户名和密码的方法，来等待WEbscoket连接成功然后把参数传送上去服务器

        //定义在接受服务器消息后就接受消息，解析json然后根据返回码对数据进行分类，分发给定义的几个类，进行消息的分发

        //然后在类里面进行操作

        //先定义几个解析json数据的工具类，例如用来存储账号密码的工具类。

    }
}
