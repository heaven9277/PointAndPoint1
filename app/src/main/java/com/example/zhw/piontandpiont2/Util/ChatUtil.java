package com.example.zhw.piontandpiont2.Util;
/*
封装群聊的类
 */
public class ChatUtil {
    public int operateId;
    public String groupName;
    public String uuid;
    public String groupUuid;
    public ChatUtil(int operateId,String groupName,String groupUuid,String uuid){
        this.operateId = operateId;
        this.groupName = groupName;
        this.uuid = uuid;
        this.groupUuid = groupUuid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }
}
