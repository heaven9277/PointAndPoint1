package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendApplicationThread  extends Thread{
    public WsManager wsManager;
    public WebSocket webSocket;
    public String uuid;
    public String groupId;
    public SendApplicationThread(String uuid,String groupId){
        this.uuid = uuid;
        this.groupId = groupId;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String application = Jsonpack.getSApplication(uuid,groupId);

            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(application);
            webSocket.sendBinary(bf_chatMessage.array());
            System.out.println("发送申请加入群聊数据");
        }
    }
}
