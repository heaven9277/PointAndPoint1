package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendOutGroupThread extends Thread{
    String groupId;
    String uuid;
    String groupRole;
    public WsManager wsManager;
    public WebSocket webSocket;
    public SendOutGroupThread( String groupRole,String groupId,String uuid){
        this.groupId = groupId;
        this.uuid = uuid;
        this.groupRole = groupRole;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
           String outgroup = Jsonpack.getOutGroup(groupRole,groupId,uuid);
            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(outgroup);
            webSocket.sendBinary(bf_chatMessage.array());
            System.out.println("发送退出群的请求数据");
        }
    }
}
