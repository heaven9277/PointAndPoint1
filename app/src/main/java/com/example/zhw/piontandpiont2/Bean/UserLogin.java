package com.example.zhw.piontandpiont2.Bean;

public class UserLogin {
    public int operateId;
    public String uuid;
    public String password;
    public UserLogin(int operateId,String uuid,String password){
        this.operateId = operateId;
        this.uuid = uuid;
        this.password = password;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
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
