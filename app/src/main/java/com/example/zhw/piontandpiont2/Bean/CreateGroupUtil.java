package com.example.zhw.piontandpiont2.Bean;
/*
    封装创建群
 */
public class CreateGroupUtil {
    public int operateId;
    public String uuid;//用户名
    public String groupName;//群名称
    public String groupDec;//群描述
    public String groupHobby;//群爱好
    public CreateGroupUtil(int operateId,String uuid,String groupName,String groupDec,String groupHobby){
        this.operateId = operateId;
        this.uuid = uuid;
        this.groupName = groupName;
        this.groupDec = groupDec;
        this.groupHobby = groupHobby;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDec() {
        return groupDec;
    }

    public void setGroupDec(String groupDec) {
        this.groupDec = groupDec;
    }

    public String getGroupHobby() {
        return groupHobby;
    }

    public void setGroupHobby(String groupHobby) {
        this.groupHobby = groupHobby;
    }
}
