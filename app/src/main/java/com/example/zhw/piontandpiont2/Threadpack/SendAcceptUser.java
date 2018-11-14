package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendAcceptUser extends Thread {
    public String requestUserUuid;
    public String sendUserUuid;
    public String groupId;
    public WsManager wsManager;
    public WebSocket webSocket;
    public int accept;
    public SendAcceptUser(int accept,String requestUserUuid,String sendUserUuid,String groupId){
        this.requestUserUuid = requestUserUuid;
        this.sendUserUuid = sendUserUuid;
        this.groupId = groupId;
        this.accept =accept;//判断接受还是i拒绝
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String accepts = Jsonpack.getAccept(accept,requestUserUuid,sendUserUuid,groupId);
            System.out.println(groupId);
            ByteBuffer bf_accept = BufferChange.getByteBuffer(accepts);
            webSocket.sendBinary(bf_accept.array());
            System.out.println("发送接受或者拒绝的请求数据");
        }
    }
}
