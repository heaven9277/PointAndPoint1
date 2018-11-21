package com.example.zhw.piontandpiont2.Bean;

public class UserInfo {
    int operateId;
    String uuid;
    public UserInfo(int operateId,String uuid){
        this.operateId = operateId;
        this.uuid = uuid;
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
}
