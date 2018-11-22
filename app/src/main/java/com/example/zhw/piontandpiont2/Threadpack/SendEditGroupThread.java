package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendEditGroupThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    String groupName;
    String groupId;
    String groupDesc;
    String groupPortrait;
    public SendEditGroupThread(String groupName, String groupId, String groupDesc, String portrait){
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupDesc = groupDesc;
        this.groupPortrait = portrait;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String EditInfo = Jsonpack.getGroupEditInfo(groupName,groupId,groupDesc,groupPortrait);
            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(EditInfo);
            webSocket.sendBinary(bf_chatMessage.array());
            System.out.println("发送修改群资料的请求数据");
        }
    }
}
