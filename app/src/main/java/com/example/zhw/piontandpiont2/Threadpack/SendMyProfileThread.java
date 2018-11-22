package com.example.zhw.piontandpiont2.Threadpack;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.example.zhw.piontandpiont2.Util.LogUtil;
import com.neovisionaries.ws.client.WebSocket;

import java.nio.ByteBuffer;

public class SendMyProfileThread extends Thread {
    public WsManager wsManager;
    public WebSocket webSocket;
    private String uuid,userName,userQianming,userPhone,userEmail;
    private String uuidPic;

    public SendMyProfileThread(String uuid, String uuidPic, String userName, String userQianming, String userPhone, String userEmail) {
        this.uuid = uuid;
        this.uuidPic = uuidPic;
        this.userName = userName;
        this.userQianming = userQianming;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    @Override
    public void run() {
        super.run();
        wsManager = WsManager.getInstance();
        if (wsManager != null){
            webSocket = wsManager.getWebsocket();
            String s = Jsonpack.getSMyProfile(uuid,uuidPic, userName, userQianming, userPhone, userEmail);
//            System.out.println(s);
            LogUtil.e("profileJson",s);
            ByteBuffer bf_chatMessage = BufferChange.getByteBuffer(s);
//            ByteBuffer bf_chatMessage = BufferChange.getByteBufferForProfileDatas();
            webSocket.sendBinary(bf_chatMessage.array());
//            webSocket.sendText(s);
            System.out.println("发送保存资料的请求数据");
        }
    }

}
