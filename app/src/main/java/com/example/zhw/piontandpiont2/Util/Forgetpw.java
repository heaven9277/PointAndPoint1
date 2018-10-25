package com.example.zhw.piontandpiont2.Util;
//忘记密码的工具类
public class Forgetpw {
    String newPassword;
    String telphone;
    public Forgetpw(String password,String telphone){
        this.newPassword = password;
        this.telphone = telphone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
