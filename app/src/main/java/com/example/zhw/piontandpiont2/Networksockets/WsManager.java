package com.example.zhw.piontandpiont2.Networksockets;

import com.example.zhw.piontandpiont2.MessagePack.MessageNotication;
import com.example.zhw.piontandpiont2.Util.WsStatus;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//wensock连接的类

public class WsManager {
    private static WsManager mInstance;
    private final String TAG = this.getClass().getSimpleName();

    /**
     * WebSocket config
     */
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String DEF_TEST_URL = AdressHttp.getUrl()+"ws";//测试服默认地址

    //private String url;

    private WsStatus mStatus;
    private WebSocket ws;
    private WsListener mListener;
    private WsManager() {
    }

    public static WsManager getInstance(){
        if(mInstance == null){
            synchronized (WsManager.class){
                if(mInstance == null){
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    public void init(){
        try {
            /**
             * configUrl其实是缓存在本地的连接地址
             * 这个缓存本地连接地址是app启动的时候通过http请求去服务端获取的,
             * 每次app启动的时候会拿当前时间与缓存时间比较,超过6小时就再次去服务端获取新的连接地址更新本地缓存
             */
            String configUrl = "";
           // url = TextUtils.isEmpty(configUrl) ? DEF_URL : configUrl;
            ws = new WebSocketFactory().createSocket(DEF_TEST_URL, CONNECT_TIMEOUT)
                    .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                    .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                    .addListener(mListener = new WsListener())//添加回调监听
                    .connectAsynchronously();//异步连接
            setStatus(WsStatus.CONNECTING);
            System.out.println("进行连接");
           // Logger.t(TAG).d("第一次连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 继承默认的监听空实现WebSocketAdapter,重写我们需要的方法
     * onTextMessage 收到文字信息
     * onConnected 连接成功
     * onConnectError 连接失败
     * onDisconnected 连接关闭
     */
    class WsListener extends WebSocketAdapter {
        //得到服务器发送过来的信息

        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            //Logger.t(TAG).d(text);

            System.out.println("接收服务器发送过来的信息"+text);
            //得到消息，进行解码
            int id = getOperateId(text);
            System.out.println("得到操作码"+id);
            //进行消息分发
            MessageNotication.sendInfoToActivity(text,id);
        }
        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers)
                throws Exception {
            super.onConnected(websocket, headers);

            System.out.println("连接成功");
            setStatus(WsStatus.CONNECT_SUCCESS);

        }


        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception)
                throws Exception {
            super.onConnectError(websocket, exception);

            System.out.println("连接错误");
            setStatus(WsStatus.CONNECT_FAIL);
            init();
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
                throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            System.out.println("断开连接");
            setStatus(WsStatus.CONNECT_FAIL);
            init();
            System.out.println("进行连接。。。。？？？？");
        }
    }

    private void setStatus(WsStatus status){
        this.mStatus = status;
    }

    public WsStatus getStatus(){
        return mStatus;
    }

    public void disconnect(){
        if(ws != null)
            ws.disconnect();
    }

    //返回一个websocket对象
    public WebSocket getWebsocket(){
        if (ws == null && getStatus() != WsStatus.CONNECT_SUCCESS){
            init();
        }
        return ws;
    }
    //得到操作码
    public int getOperateId(String text){
        JSONObject jsonObject;
        int id = 0;
        try {
            jsonObject = new JSONObject(text);
            id = jsonObject.getInt("operateId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }
}
