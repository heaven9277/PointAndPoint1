package com.example.zhw.piontandpiont2.Util;

public class UserLogin {
    public String operateId;
    public String uuid;
    public String password;
    public UserLogin(String operateId,String uuid,String password){
        this.operateId = operateId;
        this.uuid = uuid;
        this.password = password;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
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
}
