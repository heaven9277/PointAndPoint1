package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendManagerUserThread  extends Thread{
    public WsManager wsManager;
    public WebSocket webSocket;
    String groupId;
    String uuid;
    String delUuid;
    public SendManagerUserThread(String groupId,String uuid,String delUuid){
        this.groupId =groupId;
        this.uuid = uuid;
        this.delUuid = delUuid;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            //String outgroup = Jsonpack.getOutGroup(groupId,uuid);
            String managerUser = Jsonpack.getManagerUser(groupId,uuid,delUuid);
            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(managerUser);
            webSocket.sendBinary(bf_chatMessage.array());
            System.out.println("发送删除群成员的请求数据");
        }
    }
}
