package com.example.zhw.piontandpiont2.Bean;

public class AcceptUserBean {
    public int operateId;
    public String requestUserUuid;
    public String sendUserUuid;
    public String groupUuid;
    public String result;
    public long noticeId;
    public AcceptUserBean(int operateId,String requestUserUuid,String sendUserUuid,String groupUuid,String result,long noticeId){
        this.operateId = operateId;
        this.requestUserUuid = requestUserUuid;
        this.sendUserUuid = sendUserUuid;
        this.groupUuid = groupUuid;
        this.result =result;
        this.noticeId = noticeId;
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

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }
}
