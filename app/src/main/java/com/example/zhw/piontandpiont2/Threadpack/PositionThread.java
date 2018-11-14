package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class PositionThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public String groupId;
    public PositionThread(String groupId){
        this.groupId = groupId;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String postion = Jsonpack.getposition(groupId);
            ByteBuffer bf_position = BufferChange.getByteBuffer(postion);
            webSocket.sendBinary(bf_position.array());
            System.out.println("发送定位的请求数据");
        }
    }
}
