package com.example.zhw.piontandpiont2;

import android.app.Application;
import android.content.SharedPreferences;

public class App extends Application {
    public static SharedPreferences.Editor editor;
    public static SharedPreferences shared;
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("自动运行。。。。。");
        //判断是否第一次进入。注：除非清空应用数据或者卸载软件重新安装才能再次进入第一次
        date();
    }
    private void date() {
        shared = getSharedPreferences("is", MODE_PRIVATE);
        editor = shared.edit();
    }
    public static SharedPreferences.Editor getEditor(){
        return editor;
    }
    public static SharedPreferences getShared(){
        return shared;
    }
}
