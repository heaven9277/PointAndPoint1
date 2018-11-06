package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendFisrtDataThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    public String username;
    public SendFisrtDataThread(String username){
        this.username = username;
    }
    public void run(){
        //向服务器发送请求数据
        sendServeiceData();
    }
    /*
发送首页请求
*/
    private void sendServeiceData() {
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String fisrt_data = Jsonpack.getFisrtData(username);
            ByteBuffer bf_first = BufferChange.getByteBuffer(fisrt_data);
            webSocket.sendBinary(bf_first.array());
            System.out.println("发送首页数据");
        }
    }
}
