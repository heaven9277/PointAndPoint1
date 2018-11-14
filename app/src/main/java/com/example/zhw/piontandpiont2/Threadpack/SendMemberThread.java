package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendMemberThread extends Thread {
    public String groupId;
    public WsManager wsManager;
    public WebSocket webSocket;
    public SendMemberThread(String groupId){
        this.groupId =groupId;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String  member = Jsonpack.getMember(groupId);
            ByteBuffer bf_member = BufferChange.getByteBuffer(member);
            webSocket.sendBinary(bf_member.array());
            System.out.println("发送群成员联系的请求数据");
        }
    }
}
