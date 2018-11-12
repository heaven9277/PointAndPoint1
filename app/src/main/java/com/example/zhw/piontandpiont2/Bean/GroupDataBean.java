package com.example.zhw.piontandpiont2.Bean;

import java.util.List;

public class GroupDataBean {
    private String operateId,groupName,groupPortrait,groupNumber,groupAnoun;
    private List<MembersBean> members;

    public List<MembersBean> getMembers() {
        return members;
    }
    public String getOperateId() {
        return operateId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public String getGroupAnoun() {
        return groupAnoun;
    }

    public class MembersBean{
        private String groupUserName,groupUserPortrait,groupUserUuid;

        public String getGroupUserName() {
            return groupUserName;
        }

        public String getGroupUserPortrait() {
            return groupUserPortrait;
        }

        public String getGroupUserUuid() {
            return groupUserUuid;
        }

    }
}
