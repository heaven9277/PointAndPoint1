package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendUserInfoThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public String uuid;
    public SendUserInfoThread(String uuid){
        this.uuid = uuid;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();

            String userInfo = Jsonpack.getUserInfo(uuid);
            System.out.println(userInfo);
            ByteBuffer bf_userInfo = BufferChange.getByteBuffer(userInfo);
            webSocket.sendBinary(bf_userInfo.array());
            System.out.println("发送搜索群的请求数据");
        }
    }
}
