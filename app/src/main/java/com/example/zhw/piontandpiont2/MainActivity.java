package com.example.zhw.piontandpiont2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Services.MyServer;
import com.example.zhw.piontandpiont2.Util.PareJson;
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
    public static Context context;
    private static Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到信息");
            int what = msg.what;
            String text = (String) msg.obj;
            switch (what){
                case 5:
                    //登陆失败
                    Toast.makeText(context, PareJson.getJsonInfo(text),Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    //登陆成功
                    Intent homeIntent = new Intent(context,HomeActivity.class);
                    homeIntent.putExtra("data",text);
                    context.startActivity(homeIntent);
                    break;
                default:
                    break;
            }
        }
    };
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

        context = this;
        if (myConn == null){
            myConn = new MyConn();
        }
        //绑定服务
        Intent intentService  = new Intent(this,MyServer.class);
        bindService(intentService,myConn, Service.BIND_AUTO_CREATE);
        System.out.println("开始测试");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case  R.id.btn_login:
                Intent i=new Intent(MainActivity.this , HomeActivity.class);
                startActivity(i);
               /* if (myConn == null){
                    myConn = new MyConn();
                }
                //绑定服务
                Intent intentService  = new Intent(this,MyServer.class);
                bindService(intentService,myConn, Service.BIND_AUTO_CREATE);
                System.out.println("开始测试");*/

               if (username.getText().toString().trim()!=null||userpasswd.getText().toString().trim()!=null){
                   myIBinder.sendData(username.getText().toString().trim(),userpasswd.getText().toString().trim());
               }else {
                   Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_LONG).show();
               }
              /* Intent intent = new Intent(this,HomeActivity.class);
               startActivity(intent);*/

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

            MyThread myThread = new MyThread();
            myThread.start();
        }
        //当服务失去连接调用的方法
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            //开始发送数据
            myIBinder.initWebsocket();
        }
    }
    public static Handler getHandler(){
        return mHandler;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        register.setTextColor(Color.parseColor("#000000"));
        forgetpasswd.setTextColor(Color.parseColor("#ffffff"));
    }
}
