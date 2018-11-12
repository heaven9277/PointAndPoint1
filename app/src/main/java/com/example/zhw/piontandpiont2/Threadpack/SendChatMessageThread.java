package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

/*
发送用户想要发送的内容的线程
 */
public class SendChatMessageThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public String uuid;
    public String groupId;
    public String groupMessage;
    public SendChatMessageThread(String uuid,String groupId,String groupMessage){
        this.uuid = uuid;
        this.groupId = groupId;
        this.groupMessage = groupMessage;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String chatMessage = Jsonpack.getChatMessage(uuid,groupId,groupMessage);

            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(chatMessage);
            webSocket.sendBinary(bf_chatMessage.array());
            System.out.println("发送用户聊天的发送内容数据");
        }
    }
}
