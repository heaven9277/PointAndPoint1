package com.example.zhw.piontandpiont2.Bean;

public class PositionBean {
    public int operateId;
    public String groupId;
    public PositionBean(int operateId,String groupId){
        this.operateId = operateId;
        this.groupId = groupId;
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
}
