package com.example.zhw.piontandpiont2.Bean;

public class ManagerGroupUser {
    public int operateId;
    public String groupId;
    public String uuid;
    public String delUuid;
    public ManagerGroupUser(int operateId,String groupId,String uuid,String delUuid){
        this.operateId = operateId;
        this.groupId = groupId;
        this.uuid = uuid;
        this.delUuid = delUuid;
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

    public String getDelUuid() {
        return delUuid;
    }

    public void setDelUuid(String delUuid) {
        this.delUuid = delUuid;
    }
}
