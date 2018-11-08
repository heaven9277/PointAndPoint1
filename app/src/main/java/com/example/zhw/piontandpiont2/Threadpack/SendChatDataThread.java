package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

/*
群聊向后台发送数据
 */
public class SendChatDataThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public String groupName;
    public String groupId;
    public String uuid;
    public SendChatDataThread(String groupName,String groupId,String uuid){
        this.groupName = groupName;
        this.groupId = groupId;
        this.uuid = uuid;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String Chat_data = Jsonpack.getChatData(groupName,groupId,uuid);
            ByteBuffer bf_chat = BufferChange.getByteBuffer(Chat_data);
            webSocket.sendBinary(bf_chat.array());
            System.out.println("发送群聊的数据");
        }
    }
}
