package com.example.zhw.piontandpiont2.Bean;

public class FirstRequest {
    public int operateId;
    public String uuid;
    public FirstRequest(int operateId,String uuid){
        this.operateId = operateId;
        this.uuid = uuid;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }
}
