package com.example.zhw.piontandpiont2.Util;
//忘记密码的工具类
public class Forgetpw {
    String newPassword;
    String telephone;
    public Forgetpw(String password,String telphone){
        this.newPassword = password;
        this.telephone = telphone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getTelphone() {
        return telephone;
    }

    public void setTelphone(String telephone) {
        this.telephone = telephone;
    }
}
