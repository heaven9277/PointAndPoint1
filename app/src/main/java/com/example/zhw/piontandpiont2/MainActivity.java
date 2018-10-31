package com.example.zhw.piontandpiont2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;

import android.os.Bundle;

import android.media.MediaPlayer;
import android.net.Uri;


import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Services.MyServer;
import com.example.zhw.piontandpiont2.vdieo.CustomVideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login;
    private EditText username;
    private EditText userpasswd;
    private TextView forgetpasswd;
    private TextView register;
    private MyServer.MyIBider myIBinder;
    private MyConn myConn;
    private CustomVideoView videovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();

    }
    //初始化组件
    private void initview() {
        btn_login = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        userpasswd = findViewById(R.id.userpasswd);
        forgetpasswd = findViewById(R.id.forgetpasswd);
        register = findViewById(R.id.register);
        //绑定监听器
        btn_login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetpasswd.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case  R.id.btn_login:

                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
             /*   if (myConn == null){
=======
                if (myConn == null){
>>>>>>> parent of 686caee... 1
=======
                if (myConn == null){
>>>>>>> parent of 686caee... 1
                    myConn = new MyConn();
                }
                //绑定服务
                Intent intentService  = new Intent(this,MyServer.class);
                bindService(intentService,myConn, Service.BIND_AUTO_CREATE);
<<<<<<< HEAD
<<<<<<< HEAD
                System.out.println("开始测试");*/

                System.out.println("开始测试");


                System.out.println("开始测试");
                break;
            case R.id.register:
                register.setTextColor(Color.parseColor("#09A3DC"));
                //跳转到注册页面
                Intent intentRegister = new Intent(this,RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.forgetpasswd:
                forgetpasswd.setTextColor(Color.parseColor("#09A3DC"));
                Intent intentForget = new Intent(this,ForgetPwActivity.class);
                startActivity(intentForget);
                break;
            default:
                break;
        }
    }

    private class MyConn implements ServiceConnection {
        //当成功绑定服务时就会开启，返回iBinder对象
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myIBinder = (MyServer.MyIBider) iBinder;
            System.out.println("服务成功绑定，等待操作");

        }
        //当服务失去连接调用的方法
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        register.setTextColor(Color.parseColor("#000000"));
        forgetpasswd.setTextColor(Color.parseColor("#ffffff"));
    }
}
