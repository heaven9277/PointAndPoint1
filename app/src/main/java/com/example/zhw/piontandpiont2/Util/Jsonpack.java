package com.example.zhw.piontandpiont2.Util;

import com.example.zhw.piontandpiont2.Bean.AcceptUserBean;
import com.example.zhw.piontandpiont2.Bean.Applications;
import com.example.zhw.piontandpiont2.Bean.ChatMessage;
import com.example.zhw.piontandpiont2.Bean.ChatUtil;
import com.example.zhw.piontandpiont2.Bean.CreateGroupUtil;
import com.example.zhw.piontandpiont2.Bean.EditGroupInfo;
import com.example.zhw.piontandpiont2.Bean.FirstRequest;
import com.example.zhw.piontandpiont2.Bean.Forgetpw;
import com.example.zhw.piontandpiont2.Bean.GruopInfo;
import com.example.zhw.piontandpiont2.Bean.LocationBean;
import com.example.zhw.piontandpiont2.Bean.LoginSuccessData;
import com.example.zhw.piontandpiont2.Bean.ManagerGroupUser;
import com.example.zhw.piontandpiont2.Bean.MemberBean;
import com.example.zhw.piontandpiont2.Bean.OutGroup;
import com.example.zhw.piontandpiont2.Bean.PositionBean;
import com.example.zhw.piontandpiont2.Bean.SeachData;
import com.example.zhw.piontandpiont2.Bean.UserLogin;
import com.example.zhw.piontandpiont2.Bean.UserRegister;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

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
    /*
    聊天信息的json
     */
    public static String getChatMessage(String uuid,String groupId,String groupMessage){
        ChatMessage chatMessage = new ChatMessage(21,uuid,groupId,groupMessage);
        Gson gson = new Gson();
        String message_json = gson.toJson(chatMessage);
        return message_json;
    }
    /*
    群资料的json
     */
    public static String getGroupInfo(String groupId){
        GruopInfo gruopInfo = new GruopInfo(5,groupId);
        Gson gson = new Gson();
        String groupinfo_json = gson.toJson(gruopInfo);
        return groupinfo_json;
    }
    /*
    修改群资料的son
     */
    public static String getGroupEditInfo(String groupName,String groupId,String groupDesc){
        EditGroupInfo editGroupInfo = new EditGroupInfo(7,groupName,groupId,groupDesc);
        Gson gson = new Gson();
        String editgroupinfo_json = gson.toJson(editGroupInfo);
        return editgroupinfo_json;
    }
    /*
    退出群的请求json
     */
    public static String getOutGroup(String groupRole,String groupId,String uuid){
        OutGroup outGroup;
        if (groupRole.equals("0")){
            //群主
             outGroup = new OutGroup(20,groupId,uuid);
        }else{
            //群成员
             outGroup = new OutGroup(6,groupId,uuid);
        }
        System.out.println("?????????用户名"+uuid);
        Gson gson = new Gson();
        String outGroup_json = gson.toJson(outGroup);
        return outGroup_json;
    }
    /*
    删除群成员请求json
     */
    public static String getManagerUser(String groupId,String uuid,String delUuid){
        ManagerGroupUser managerGroupUser = new ManagerGroupUser(13,groupId,uuid,delUuid);
        Gson gson = new Gson();
        String outGroup_json = gson.toJson(managerGroupUser);
        return outGroup_json;
    }
    /*
    搜索群的json
     */
    public static String getSeach(String groupId){
        SeachData seachData = new SeachData(14,groupId);
        Gson gson = new Gson();
        String outGroup_json = gson.toJson(seachData);
        return outGroup_json;
    }
    /*
    加入群聊的json
     */
    public static String getSApplication(String uuid,String groupId){
        Applications applications = new Applications(10,uuid,groupId);
        Gson gson = new Gson();
        String application_json = gson.toJson(applications);
        return application_json;
    }
    /*
    返回位置的json
     */
    public static String getLocation(String uuid,double longitude,double latitude){
        LocationBean locationBean = new LocationBean(24,uuid,longitude,latitude);
        Gson gson = new Gson();
        String location_json = gson.toJson(locationBean);
        return location_json;
    }
    /*
    返回定位的json
     */
    public static String getposition(String groupId){
        PositionBean positionBean = new PositionBean(8,groupId);
        Gson gson = new Gson();
        String position_json = gson.toJson(positionBean);
        return position_json;
    }
    /*
    返回联系群成员的json
     */
    public static String getMember(String groupId){
        MemberBean memberBean = new MemberBean(9,groupId);
        Gson gson = new Gson();
        String memberBean_json = gson.toJson(memberBean);
        return memberBean_json;
    }
    /*
    返回接受成员请求json
     */
    public static String getAccept(int accept,String requestUserUuid,String sendUserUuid,String groupId){
        AcceptUserBean acceptUserBean;
        if (accept==1){
             acceptUserBean = new AcceptUserBean(11,requestUserUuid,sendUserUuid,groupId);
        }else{
             acceptUserBean = new AcceptUserBean(12,requestUserUuid,sendUserUuid,groupId);
        }
        Gson gson = new Gson();
        String accept_json = gson.toJson(acceptUserBean);
        return accept_json;
    }
}
