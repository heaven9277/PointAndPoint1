package com.example.zhw.piontandpiont2.Util;

import com.google.gson.Gson;

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
}
