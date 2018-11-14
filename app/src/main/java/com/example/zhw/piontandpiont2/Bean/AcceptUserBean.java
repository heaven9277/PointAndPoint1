package com.example.zhw.piontandpiont2.Bean;

public class AcceptUserBean {
    public int operateId;
    public String requestUserUuid;
    public String sendUserUuid;
    public String groupId;
    public AcceptUserBean(int operateId,String requestUserUuid,String sendUserUuid,String groupId){
        this.operateId = operateId;
        this.requestUserUuid = requestUserUuid;
        this.sendUserUuid = requestUserUuid;
        this.groupId = groupId;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getRequestUserUuid() {
        return requestUserUuid;
    }

    public void setRequestUserUuid(String requestUserUuid) {
        this.requestUserUuid = requestUserUuid;
    }

    public String getSendUserUuid() {
        return sendUserUuid;
    }

    public void setSendUserUuid(String sendUserUuid) {
        this.sendUserUuid = sendUserUuid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
