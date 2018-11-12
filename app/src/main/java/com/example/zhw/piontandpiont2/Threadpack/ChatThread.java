package com.example.zhw.piontandpiont2.Threadpack;

import android.content.Context;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class ChatThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public Context context;
    public String groupName;
    public String uuid;
    public String groupId;
    public ChatThread(Context context,String groupName,String uuid,String groupId){
        this.context =context;
        this.groupName = groupName;
        this.uuid = uuid;
        this.groupId = groupId;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String chat_data = Jsonpack.getChatData(groupName,groupId,uuid);
            ByteBuffer bf_first = BufferChange.getByteBuffer(chat_data);
            webSocket.sendBinary(bf_first.array());
            System.out.println("发送聊天数据");
        }
    }
}
