package com.example.zhw.piontandpiont2.Bean;

public class OutGroup {
    public int operateId;
    public String groupId;
    public String uuid;
    public OutGroup(int operateId,String groupId,String uuid){
        this.operateId = operateId;
        this.groupId = groupId;
        this.uuid = uuid;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
