package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

//发送提醒已经处理消息
public class SendTimeStamp  extends Thread{
    public WsManager wsManager;
    public WebSocket webSocket;
    public String groupUuid;
    public String userUuid;
    public long timeStamp;
    public SendTimeStamp(String userUuid,String groupUuid,long timeStamp){
        this.userUuid = userUuid;
        this.groupUuid = groupUuid;
        this.timeStamp = timeStamp;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String time = Jsonpack.getTimeStamp(userUuid,groupUuid,timeStamp);
            ByteBuffer bf_time = BufferChange.getByteBuffer(time);
            webSocket.sendBinary(bf_time.array());
            System.out.println("发送消息已经处理的请求数据");
        }
    }
}
