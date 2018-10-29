package com.example.zhw.piontandpiont2.Util;

import org.json.JSONException;
import org.json.JSONObject;

//解析服务器发过来的数据
public class PareJson {
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
}
