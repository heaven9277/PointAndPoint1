package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendSeachThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public String groupId;
    public SendSeachThread(String groupId){
        this.groupId = groupId;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
             String seach = Jsonpack.getSeach(groupId);
             System.out.println(groupId);
            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(seach);
            webSocket.sendBinary(bf_chatMessage.array());
            System.out.println("发送搜索群的请求数据");
        }
    }
}
