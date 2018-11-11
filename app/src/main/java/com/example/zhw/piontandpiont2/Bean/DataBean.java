package com.example.zhw.piontandpiont2.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DataBean implements Parcelable {
    private String groupName,groupPortrait,lastestGroupUser, lastGroupNumberName,
            lastGroupSendTime,lastestGroupMessage,groupMessageCount,groupRole;

    protected DataBean(Parcel in) {
        groupName = in.readString();
        groupPortrait = in.readString();
        lastestGroupUser = in.readString();
        lastGroupNumberName = in.readString();
        lastGroupSendTime = in.readString();
        lastestGroupMessage = in.readString();
        groupMessageCount = in.readString();
        groupRole = in.readString();
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel in) {
            return new DataBean(in);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(groupName);
        parcel.writeString(groupPortrait);
        parcel.writeString(lastestGroupUser);
        parcel.writeString(lastGroupNumberName);
        parcel.writeString(lastGroupSendTime);
        parcel.writeString(lastestGroupMessage);
        parcel.writeString(groupMessageCount);
        parcel.writeString(groupRole);
    }
}
