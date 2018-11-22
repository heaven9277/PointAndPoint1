package com.example.zhw.piontandpiont2.Bean;

public class EditGroupInfo {
    public int operateId;
    public String groupName;
    public String groupId;
    public String groupDec;
    private String groupPortrait;
    public EditGroupInfo(int operateId, String groupName, String groupId, String groupDec, String groupPortrait){
        this.operateId = operateId;
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupDec = groupDec;
        this.groupPortrait = groupPortrait;
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

    public String getGroupDec() {
        return groupDec;
    }

    public void setGroupDec(String groupDec) {
        this.groupDec = groupDec;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }
}
