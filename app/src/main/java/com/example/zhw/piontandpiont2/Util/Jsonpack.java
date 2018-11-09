package com.example.zhw.piontandpiont2.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    /*
    解析
     */
    public static List<LoginSuccessData> getLoginSuccessData(String string) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject gjsonObject = jsonParser.parse(string).getAsJsonObject();
        System.out.println(gjsonObject);
        JsonObject data = gjsonObject.getAsJsonObject("data");
        System.out.println(data);
        JsonArray gjsonArray = data.getAsJsonArray("groups");
        System.out.println(gjsonArray);
        List<LoginSuccessData> loginSuccessData = gson.fromJson(gjsonArray, new TypeToken<List<LoginSuccessData>>() {
        }.getType());
        return loginSuccessData;
    }
    public static List<LoginSuccessData> creategetLoginSuccessData(String string) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject gjsonObject = jsonParser.parse(string).getAsJsonObject();
        JsonArray gjsonArray = gjsonObject.getAsJsonArray("groups");

        List<LoginSuccessData> loginSuccessData = gson.fromJson(gjsonArray, new TypeToken<List<LoginSuccessData>>() {
        }.getType());
        return loginSuccessData;
    }

    public static JsonObject getSignal(String string){
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject gjsonObject = jsonParser.parse(string).getAsJsonObject();
        System.out.println(gjsonObject);
        JsonObject data = gjsonObject.getAsJsonObject("data");
        System.out.println(data);
        JsonObject signalJson = data.getAsJsonObject("singal");
        System.out.println(signalJson);
        return signalJson;
    }

    public static String getUserPortrait(String string){
        String userPortrait = getSignal(string).get("userPortrait").toString();
        return userPortrait;
    }

    public static String getUserName(String string){
        String userName = getSignal(string).get("userName").toString();
        return userName;
    }

    public static String getUserSign(String string){
        String userSign = getSignal(string).get("userSign").toString();
        return userSign;
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
