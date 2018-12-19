package com.example.zhw.piontandpiont2.Bean;
//提醒服务器该条消息已经处理
public class TimeStamp {
    private int operateId;
    private String userUuid;
    private String groupUuid;
    private long timeStamp;
    public TimeStamp(int operateId,String userUuid,String groupUuid,long timeStamp){
        this.operateId = operateId;
        this.userUuid =userUuid;
        this.groupUuid = groupUuid;
        this.timeStamp = timeStamp;
    }
    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
