package com.example.zhw.piontandpiont2.Bean;

public class Applications {
    public int operateId;
    public String uuid;
    public String groupId;
    public Applications(int operateId,String uuid,String groupId){
        this.operateId = operateId;
        this.uuid = uuid;
        this.groupId = groupId;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
