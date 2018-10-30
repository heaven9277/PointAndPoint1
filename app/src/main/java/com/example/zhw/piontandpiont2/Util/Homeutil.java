package com.example.zhw.piontandpiont2.Util;

//首页的工具类
public class Homeutil {
    public String groupName;//群聊名字
    public String groupPortrait;//头像
    public String lastestGroupUser;//最新消息的成员账号
    public String lastGroupNumberName;//成员的昵称
    public String lastGroupSendTime;//发送时间
    public String lastestGroupMessage;//最新发送的消息
    public String groupMessageCount;//未读信息
    public String groupRole;//是否为群主
    public Homeutil(String groupName,String groupPortrait,String lastestGroupUser,String lastGroupNumberName,
                    String lastGroupSendTime,String lastestGroupMessage,String groupMessageCount,String groupRole){
        this.groupName = groupName;
        this.groupPortrait = groupPortrait;
        this.lastestGroupUser = lastestGroupUser;
        this.lastGroupNumberName = lastGroupNumberName;
        this.lastGroupSendTime = lastGroupSendTime;
        this.lastestGroupMessage = lastestGroupMessage;
        this.groupMessageCount = groupMessageCount;
        this.groupRole = groupRole;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }

    public String getLastestGroupUser() {
        return lastestGroupUser;
    }

    public void setLastestGroupUser(String lastestGroupUser) {
        this.lastestGroupUser = lastestGroupUser;
    }

    public String getLastGroupNumberName() {
        return lastGroupNumberName;
    }

    public void setLastGroupNumberName(String lastGroupNumberName) {
        this.lastGroupNumberName = lastGroupNumberName;
    }

    public String getLastGroupSendTime() {
        return lastGroupSendTime;
    }

    public void setLastGroupSendTime(String lastGroupSendTime) {
        this.lastGroupSendTime = lastGroupSendTime;
    }

    public String getLastestGroupMessage() {
        return lastestGroupMessage;
    }

    public void setLastestGroupMessage(String lastestGroupMessage) {
        this.lastestGroupMessage = lastestGroupMessage;
    }

    public String getGroupMessageCount() {
        return groupMessageCount;
    }

    public void setGroupMessageCount(String groupMessageCount) {
        this.groupMessageCount = groupMessageCount;
    }

    public String getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(String groupRole) {
        this.groupRole = groupRole;
    }
}
