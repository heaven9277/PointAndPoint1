package com.example.zhw.piontandpiont2.Util;


import com.example.zhw.piontandpiont2.Bean.ConnectMemberBean;
import com.example.zhw.piontandpiont2.Bean.EnterGroupData;
import com.example.zhw.piontandpiont2.Bean.GroupDataBean;
import com.example.zhw.piontandpiont2.Bean.GroupLocation;
import com.example.zhw.piontandpiont2.Bean.GroupMemberInfo;
import com.example.zhw.piontandpiont2.Bean.MessageNotification;
import com.example.zhw.piontandpiont2.Bean.NotificationData;
import com.example.zhw.piontandpiont2.Bean.SearchGroupDataBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

//解析服务器发过来的数据
public class PaseJson {
    static Gson gson = new Gson();
    static JsonParser jsonParser = new JsonParser();

    public static String getJsonStatus(String text) {
        JSONObject jsonObject;
        String status = "";
        try {
            jsonObject = new JSONObject(text);
            status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }


    //得到提示信息
    public static String getJsonInfo(String text) {
        JSONObject jsonObject;
        String info = "";
        try {
            jsonObject = new JSONObject(text);
            info = jsonObject.getString("information");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    //得到聊天的信息

    public static JsonObject getJsonObject(String text) {

        JsonObject jsonObject = jsonParser.parse(text).getAsJsonObject();
        return jsonObject;
    }

    public static List<EnterGroupData> getEnterGroupData(String string) {
        JsonArray data = getJsonObject(string).getAsJsonArray("data");
        System.out.println(data);
        List<EnterGroupData> enterGroupDataList = gson.fromJson(data, new TypeToken<List<EnterGroupData>>() {
        }.getType());
        return enterGroupDataList;
    }

    //获群资料，包括成员资料，可再加.getNumbersBeanList(),即可获得群成员资料
    public static GroupDataBean getGroupData(String json) {
        JsonObject jsonObject = getJsonObject(json);
        GroupDataBean groupDataBean = gson.fromJson(jsonObject, GroupDataBean.class);
        return groupDataBean;
    }

    //仅获取群成员资料
    public static List<GroupDataBean.MembersBean> getMemberList(String json) {
        List<GroupDataBean.MembersBean> membersBeanList = getGroupData(json).getMembers();
        return membersBeanList;
    }

    //删除群成员后使用
    public static List<GroupDataBean.MembersBean> getMemberListForDelete(String json){
        JsonObject jsonObject = getJsonObject(json);
        JsonArray jsonArray = jsonObject.getAsJsonArray("numbers");
        List<GroupDataBean.MembersBean> membersBeanList = gson.fromJson(jsonArray, new TypeToken<List<GroupDataBean.MembersBean>>() {
        }.getType());
        return membersBeanList;
    }

    //搜索群获取数据
    public static List<SearchGroupDataBean> getSearchData(String string) {
        JsonObject jsonObject = getJsonObject(string);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<SearchGroupDataBean> searchGroupDataBeanList = gson.fromJson(jsonArray, new TypeToken<List<SearchGroupDataBean>>() {
        }.getType());
        return searchGroupDataBeanList;
    }

    //解析得到群成员位置数据
    //操作8
    public static List<GroupLocation> getGroupLocationData(String string) {
        JsonObject jsonObject = getJsonObject(string);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<GroupLocation> groupDataBeanList = gson.fromJson(jsonArray, new TypeToken<List<GroupLocation>>() {
        }.getType());
        return groupDataBeanList;
    }

    //通知消息得到的数据bean
    //操作码23
    public static List<NotificationData> getNotificationData(String string) {
        JsonObject jsonObject = getJsonObject(string);
        JsonArray data = jsonObject.getAsJsonArray("data");
        List<NotificationData> notificationDataList = gson.fromJson(data, new TypeToken<List<NotificationData>>() {
        }.getType());
        return notificationDataList;
    }

    //解析得到群成员位置数据
    //操作码9
    public static List<ConnectMemberBean> getConnectMemberData(String string) {
        JsonObject jsonObject = getJsonObject(string);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<ConnectMemberBean> connectMemberBeanList = gson.fromJson(jsonArray, new TypeToken<List<ConnectMemberBean>>() {
        }.getType());
        return connectMemberBeanList;
    }

//操作码23

    public static List<MessageNotification> getMessagesNotificationData(String string) {
        JsonObject jsonObject = getJsonObject(string);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<MessageNotification> messageNotificationList = gson.fromJson(jsonArray, new TypeToken<List<MessageNotification>>() {
        }.getType());
        return messageNotificationList;
    }

    public static GroupMemberInfo getGroupMemberInfo(String string){
        JsonObject jsonObject = getJsonObject(string);
        JsonObject data = jsonObject.getAsJsonObject("data");
        GroupMemberInfo groupMemberInfo = gson.fromJson(data,GroupMemberInfo.class);
        return  groupMemberInfo;
    }

}