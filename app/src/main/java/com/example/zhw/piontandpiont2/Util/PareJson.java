package com.example.zhw.piontandpiont2.Util;


import com.example.zhw.piontandpiont2.Bean.EnterGroupData;
import com.example.zhw.piontandpiont2.Bean.GroupDataBean;
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
public class PareJson {
    static Gson gson = new Gson();
    static JsonParser jsonParser = new JsonParser();
   public static String getJsonStatus(String text){
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
   public static String getJsonInfo(String text){
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

    public static  JsonObject getJsonObject(String text){

        JsonObject jsonObject = jsonParser.parse(text).getAsJsonObject();
        return jsonObject;
    }

    public static List<EnterGroupData> getEnterGroupData(String string){
        JsonArray data = getJsonObject(string).getAsJsonArray("data");
        System.out.println(data);
        List<EnterGroupData> enterGroupDataList = gson.fromJson(data, new TypeToken<List<EnterGroupData>>() {
        }.getType());
        return enterGroupDataList;
    }
    //获群资料，包括成员资料，可再加.getNumbersBeanList(),即可获得群成员资料
    public static GroupDataBean getGroupData(String json){
        JsonObject jsonObject = getJsonObject(json);
        GroupDataBean groupDataBean = gson.fromJson(jsonObject,GroupDataBean.class);
        return groupDataBean;
    }

    //仅获取群成员资料
    public static List<GroupDataBean.MembersBean> getNumberList(String json){
        List<GroupDataBean.MembersBean> numbersBeanList = getGroupData(json).getMembers();
        return numbersBeanList;
    }

    //搜索群获取数据
    public static List<SearchGroupDataBean> getSearchData(String string){
       JsonObject jsonObject = getJsonObject(string);
       JsonArray jsonArray = jsonObject.getAsJsonArray("data");
       List<SearchGroupDataBean> searchGroupDataBeanList = gson.fromJson(jsonArray,new TypeToken<List<SearchGroupDataBean>>() {
       }.getType());
       return searchGroupDataBeanList;
    }
}