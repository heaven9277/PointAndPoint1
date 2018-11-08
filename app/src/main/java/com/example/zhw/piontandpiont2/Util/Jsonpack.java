package com.example.zhw.piontandpiont2.Util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//
//对类进行json分装
public class Jsonpack {

    //返回注册的json数据
    public static String getJosn(String username,String passwd,String telphone){
        UserRegister userRegister = new UserRegister(username,passwd,telphone);
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
        String json = gson.toJson(userRegister);
        return json;
    }
    //返回忘记密码的json数据
    public static String getRepsswdJosn(String telphone,String passwd ){
        Forgetpw forgetpw = new Forgetpw(passwd,telphone);
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
        String repassed_json = gson.toJson(forgetpw);
        return repassed_json;
    }
    //放回登录的json数据
    public static String getLoginJosn(String uuid,String password ){
        UserLogin userLogin = new UserLogin(1,uuid,password);
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
        String Login_json = gson.toJson(userLogin);
        return Login_json;
    }

    public static LoginSuccessData[] getLoginSuccessData(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        JSONArray jsonArray = jsonObject.getJSONArray("groups");
        System.out.println("jsonArray"+jsonArray + "" +jsonArray.length());
        LoginSuccessData[] loginSuccessData = new LoginSuccessData[jsonArray.length()];
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            LoginSuccessData data = new LoginSuccessData();
            data.setGroupName(jsonObject1.getString("groupName"));
            data.setGroupId(jsonObject1.getString("groupId"));
            data.setGroupPortrait(jsonObject1.getString("groupPortrait"));
            data.setLastestGroupUser(jsonObject1.getString("lastestGroupUser"));
            data.setLastGroupNumberName(jsonObject1.getString("lastGroupNumberName"));
            data.setLastGroupSendTime(jsonObject1.getString("lastGroupSendTime"));
            data.setLastestGroupMessage(jsonObject1.getString("lastestGroupMessage"));
            data.setGroupMessageCount(jsonObject1.getString("groupMessageCount"));
            data.setGroupRole(jsonObject1.getString("groupRole"));
            loginSuccessData[i] = data;
        }
        return loginSuccessData;
    }
    //返回首页的json
    public static String getFisrtData(String username){
        FirstRequest firstRequest = new FirstRequest(2,username);
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
        String First_json = gson.toJson(firstRequest);
        return First_json;
    }
    /*
    创建群的json
     */
    public static  String getCreateGroupData(String uuid,String groupName,String groupDec,String groupHobby){
        CreateGroupUtil createGroupUtil = new CreateGroupUtil(3,uuid,groupName,groupDec,groupHobby);
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
        String CreateGroup_json = gson.toJson(createGroupUtil);
        return CreateGroup_json;
    }
    /*
    群聊的json
     */
    public static String getChatData(String groupName,String groupUuid,String uuid){

        ChatUtil chatUtil = new ChatUtil(4,groupName,groupUuid,uuid);
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
        String chat_json = gson.toJson(chatUtil);
        return chat_json;
    }
}
