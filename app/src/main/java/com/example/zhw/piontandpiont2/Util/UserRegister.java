package com.example.zhw.piontandpiont2.Util;

public class UserRegister {
    private String 	uuid;
    private String password;
    private String userPhone;
    public UserRegister(String uuid,String password,String userPhone){
        this.uuid = uuid;
        this.password = password;
        this.userPhone  = userPhone;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
