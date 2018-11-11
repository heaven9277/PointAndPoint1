package com.example.zhw.piontandpiont2.Bean;

public class EditGroupInfo {
    public int operateId;
    public String groupName;
    public String groupId;
    public String groupDec;
    public  EditGroupInfo(int operateId,String groupName,String groupId,String groupDec){
        this.operateId = operateId;
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupDec = groupDec;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupDesc() {
        return groupDec;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDec = groupDesc;
    }
}
