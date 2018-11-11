package com.example.zhw.piontandpiont2.Bean;

public class ChatMessage {
    public int operateId;
    public String uuid;
    public String groupId;
    public String groupMessage;
    public ChatMessage(int operateId,String uuid,String groupId,String groupMessage){
        this.operateId = operateId;
        this.uuid = uuid;
        this.groupId = groupId;
        this.groupMessage = groupMessage;
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

    public String getGroupMessage() {
        return groupMessage;
    }

    public void setGroupMessage(String groupMessage) {
        this.groupMessage = groupMessage;
    }
}
