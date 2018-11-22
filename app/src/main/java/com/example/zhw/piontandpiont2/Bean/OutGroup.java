package com.example.zhw.piontandpiont2.Bean;

public class OutGroup {
    public int operateId;
    public String groupId;
    public String userUuid;
    public OutGroup(int operateId,String groupId,String userUuid){
        this.operateId = operateId;
        this.groupId = groupId;
        this.userUuid = userUuid;
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
        return userUuid;
    }

    public void setUuid(String userUuid) {
        this.userUuid = userUuid;
    }
}
