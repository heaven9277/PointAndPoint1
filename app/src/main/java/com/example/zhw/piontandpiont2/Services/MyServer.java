package com.example.zhw.piontandpiont2.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

import static com.example.zhw.piontandpiont2.Networksockets.WsManager.getInstance;
import static com.example.zhw.piontandpiont2.Util.WsStatus.CONNECT_SUCCESS;


//服务类

public class MyServer extends Service {
    WsManager wsManager;
    WebSocket webSocket;
    //发送心跳包
    //定义每15分钟想服务器发一条没用的数据
    private long sendTime = 0L;
    private final long HEART_BEAT_RATE = 5*60*1000;
    private Handler mHandler = new Handler();

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
        wsManager.disconnect();
        return super.onUnbind(intent);
    }
    //发送心跳包
    private Runnable hreatBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime  >= HEART_BEAT_RATE){
                if (webSocket != null){
                    boolean isSuccess = getConnStatus();
                    System.out.println(wsManager.getStatus()+"状态：");
                    if (isSuccess==false){
                        mHandler.removeCallbacks(hreatBeatRunnable);
                        webSocket = null;
                        new MyIBider().initWebsocket();
                        System.out.println("进入重连");
                    }else{
                        //处于长连接
                        System.out.println("处于连接？？？");
                    }
                    sendTime = System.currentTimeMillis();

                }
                mHandler.postDelayed(this, HEART_BEAT_RATE);//每隔一定的时间，对长连接进行一次心跳检测
            }
        }
    };
    private Boolean getConnStatus(){
        if (wsManager.getStatus() == CONNECT_SUCCESS){
            return true;
        }
        return false;
    }
    public class MyIBider extends Binder {
        //自定义操作
        //初始化WEBASOCKET

        public void initWebsocket() {
            wsManager = WsManager.getInstance();
                System.out.println("进入这里外面webscoket");
             if (wsManager == null) {
                    //进行连接
                    wsManager.init();
                    wsManager = WsManager.getInstance();
                    System.out.println("进入这里webscoket");
                }
                webSocket = wsManager.getWebsocket();

                mHandler.postDelayed(hreatBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
            }
            //发送数据
            public void sendData (String username, String userpasswd){
                initWebsocket();
                if (username != null && userpasswd != null) {

                    String login_json = Jsonpack.getLoginJosn(username, userpasswd);
                    ByteBuffer bf = getByteBuffer(login_json);
                    webSocket.sendBinary(bf.array());

                    System.out.println("发送用户名和密码数据");
                }
            }
            /**
             * String 转换 ByteBuffer
             * @param str
             * @return
             */
            public ByteBuffer getByteBuffer(String str){
                return ByteBuffer.wrap(str.getBytes());
            }
        }
}
