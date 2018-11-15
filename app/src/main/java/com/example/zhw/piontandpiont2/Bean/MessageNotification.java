package com.example.zhw.piontandpiont2.Bean;

import java.util.List;

//操作码23

public class MessageNotification {
    private String groupUuid;
    private List<MessagesBean> messages;

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public class MessagesBean {
        private String messageGroupId, userPortrait, messageUserName, messageUserId, messageContent, messageTime;

        public String getMessageGroupId() {
            return messageGroupId;
        }

        public void setMessageGroupId(String messageGroupId) {
            this.messageGroupId = messageGroupId;
        }

        public String getUserPortrait() {
            return userPortrait;
        }

        public void setUserPortrait(String userPortrait) {
            this.userPortrait = userPortrait;
        }

        public String getMessageUserName() {
            return messageUserName;
        }

        public void setMessageUserName(String messageUserName) {
            this.messageUserName = messageUserName;
        }

        public String getMessageUserId() {
            return messageUserId;
        }

        public void setMessageUserId(String messageUserId) {
            this.messageUserId = messageUserId;
        }

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }

        public String getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(String messageTime) {
            this.messageTime = messageTime;
        }
    }
}
