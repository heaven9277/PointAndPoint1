package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendOutLoginThread extends Thread {
    public String uuid;
    public WsManager wsManager;
    public WebSocket webSocket;
    public SendOutLoginThread(String uuid){
        this.uuid = uuid;
    }
    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String outlogin = Jsonpack.getOutLogin(uuid);
            System.out.println(outlogin);
            ByteBuffer bf_outlogin = BufferChange.getByteBuffer(outlogin);
            webSocket.sendBinary(bf_outlogin.array());
            System.out.println("发送退出登陆的请求数据");
        }
    }
}
