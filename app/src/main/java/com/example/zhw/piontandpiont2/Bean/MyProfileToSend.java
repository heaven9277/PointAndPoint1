package com.example.zhw.piontandpiont2.Bean;

public class MyProfileToSend {
    private int operateId;
    private String uuid,userName,userQianming,userPhone,userEmail;
    String uuidPic;

    public MyProfileToSend(String uuid, String uuidPic, String userName, String userQianming, String userPhone, String userEmail) {
        this.operateId = 18;
        this.uuid = uuid;
        this.uuidPic = uuidPic;
        this.userName = userName;
        this.userQianming = userQianming;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

}
