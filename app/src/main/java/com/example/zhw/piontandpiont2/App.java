package com.example.zhw.piontandpiont2;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

public class App extends Application {
    public static SharedPreferences.Editor editor;
    @Override
    public void onCreate() {
        super.onCreate();
        //判断是否第一次进入。注：除非清空应用数据或者卸载软件重新安装才能再次进入第一次
        date();
    }
    private void date() {
        SharedPreferences shared = getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer = shared.getBoolean("isfer", true);
        editor = shared.edit();
        if (isfer) {
            //第一次进入跳转
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
            editor.putBoolean("isfer", false);
            editor.commit();
        } else {
            //第二次进入跳转
            Intent in = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(in);

        }
    }
    public static SharedPreferences.Editor getEditor(){
        return editor;
    }
}
