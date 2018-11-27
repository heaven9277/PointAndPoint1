package com.example.zhw.piontandpiont2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.TeleVify;
import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPwActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_vify,btn_commitVify;
    public EditText et_usertelphone,et_telVify;
    public CountDownTimer countDownTimer;
    public   int TIME = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw);
        init();
        // 初始化 SDK 单例，可以多次调用
        MobSDK.init(this, "286c2bb5db880", "667ad3d28906469a7a7220f8f39d36ef");
        EventHandler eventHandler = new EventHandler(){    // 操作回调
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);   // 注册回调接口
    }
    public void init(){
        DarkStatusBar.setDarkStatusIcon(this);
        btn_vify = findViewById(R.id.btn_vify);
        btn_commitVify = findViewById(R.id.btn_commitVify);
        et_telVify = findViewById(R.id.et_telVify);
        et_usertelphone = findViewById(R.id.et_usertelphone);
        //绑定点击事件
        btn_vify.setOnClickListener(this);
        btn_commitVify.setOnClickListener(this);
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_vify:
                System.out.println("点击 +"+et_usertelphone.getText().toString().length()+"  "+et_usertelphone.getText().toString());
                if (et_usertelphone.getText().toString() != null & et_usertelphone.getText().toString().trim().length() == 11){
                   if (TeleVify.isMobileNO(et_usertelphone.getText().toString().trim())){
                       //发送短信权限
                       if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
                           //显示申请权限弹窗
                           showRequestPermission();
                       }else{
                           //弹窗确认
                           alterWarning();
                       }
                   }else{
                       Toast.makeText(this,"这不是手机号，请重新输入",Toast.LENGTH_LONG).show();
                   }
                }
                break;
            case R.id.btn_commitVify:
                //将收到的验证码和手机号提交再次核对
                SMSSDK.submitVerificationCode("86", et_usertelphone.getText().toString().trim(), et_telVify
                        .getText().toString());
                System.out.println("点击");
                break;
        }
    }
    //申请权限弹窗
    private void showRequestPermission(){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        //接收短信权限
                        ActivityCompat.requestPermissions(ForgetPwActivity.this,new String[]{Manifest.permission.RECEIVE_SMS},1);
                        //发送短信权限
                        ActivityCompat.requestPermissions(ForgetPwActivity.this,new String[]{Manifest.permission.SEND_SMS},2);
                        //弹窗确认
                        alterWarning();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        Toast.makeText(ForgetPwActivity.this, "拒绝" + which, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(ForgetPwActivity.this);  //先得到构造器
        builder.setTitle("申请权限"); //设置标题
        builder.setMessage("要允许点滴之间接收验证码短信吗?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("允许",dialogOnclicListener);
        builder.setNegativeButton("拒绝", dialogOnclicListener);
        builder.create().show();
    }

    //申请权限
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //initSDK();
                    Toast.makeText(this, "你授权。。。接收短信权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "你拒绝。。。接收短信权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "你授权。。。发送短信权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "你拒绝。。。发送短信权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    //弹窗确认
    private void alterWarning(){
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        dialog.dismiss();
                        //通过sdk发送短信验证（请求获取短信验证码，在监听（eventHandle）中返回）
                        SMSSDK.getVerificationCode("86", et_usertelphone.getText().toString().trim());


                        //设置获取验证码按钮不能点击
                        btn_vify.setClickable(false);
                        btn_vify.setBackgroundResource(R.drawable.btnback_black);
                        //倒计时，执行次数为（TIME+1）*1000/1000,countDownTimer每次执行间隔：1000（单位为毫秒）
                        countDownTimer  = new CountDownTimer((TIME+1)*1000, 1000) {
                             @Override
                          public void onTick(long millisUntilFinished) {
                                 btn_vify.setText(TIME-- + "秒后再次获取验证码");
                          }

                                   @Override
                            public void onFinish() {
                                       //设置获取验证码按钮可以点击
                                    btn_vify.setClickable(true);
                                    btn_vify.setBackgroundResource(R.drawable.btnback);
                                    btn_vify.setText("点击获取短信验证码");
                                                            }
                         };
                        countDownTimer.start();

                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        Toast.makeText(ForgetPwActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("发送短信"); //设置标题
        builder.setMessage("我们将把验证码发送到以下号码:\n"+86+"-"+et_usertelphone.getText().toString().trim()); //设置内容
        //   builder.setIcon(R.drawable.icon);//设置图标，图片id即可
        builder.setPositiveButton("确认",dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
               // btn.setText("重新发送(" + 1 + ")");
            } else if (msg.what == -8) {
                //btn.setText("获取验证码");
                btn_vify.setClickable(true);
                btn_vify.setBackgroundResource(R.drawable.btnback);
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;

                System.out.println("event=" + event + "  "+ msg.what);

                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        //成功跳转页面
                        Intent intent = new Intent(ForgetPwActivity.this,
                              UpdatePwActivity.class);
                        intent.putExtra("telphone",et_usertelphone.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };
}
