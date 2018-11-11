package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Networksockets.AdressHttp;
import com.example.zhw.piontandpiont2.Networksockets.HttpUtil;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.example.zhw.piontandpiont2.Util.TeleVify;
import com.example.zhw.piontandpiont2.Util.VifycationCode;
import com.example.zhw.piontandpiont2.Video.CustomVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomVideoView videoview;
    public Button btn_register;
    public EditText et_username,et_passwd,et_repasswd,et_vify,et_telphone;
    public TextView tv_vifys;//随机验证码
    public final String url = AdressHttp.getUrl()+"register/post"; //网址
    public String json=""; //josn数据

    //随机生成验证码
    VifycationCode vifycationCode;
    String Code="";
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        init();
    }
    //加载组件的方法
    public void init(){
        btn_register = findViewById(R.id.btn_register);
        et_username  = findViewById(R.id.et_username);
        et_passwd = findViewById(R.id.et_passwd);
        et_repasswd = findViewById(R.id.repasswd);
        et_telphone = findViewById(R.id.et_telphone);
        tv_vifys = findViewById(R.id.tv_vify);
        et_vify = findViewById(R.id.et_vify);
        linearLayout = findViewById(R.id.line);
        btn_register.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

        //生成验证码
       updateVify();
    }
    @Override
    public void onClick(View view) {

            int id = view.getId();
            switch (id){
                case R.id.btn_register:
                    String username  = et_username.getText().toString().trim();
                    String passwd = et_passwd.getText().toString().trim();
                    String repasswd = et_repasswd.getText().toString().trim();
                    String telphone = et_telphone.getText().toString().trim();
                    String vify = et_vify.getText().toString().trim();

                    if (!username.isEmpty() && !passwd.isEmpty() && !telphone.isEmpty()){
                       if (TeleVify.isMobileNO(telphone)){
                           if (passwd.equals(repasswd)){
                               if (Code.equals(vify)){//验证码是否一样
                                   //开始把信息分装为json数据
                                   json = Jsonpack.getJosn(username,passwd,telphone);
                                   System.out.println(json);
                                   //发送数据
                                   sendOkhttpInfo();
                               }else{
                                   updateVify();
                                   Toast.makeText(this,"验证码不正确",Toast.LENGTH_LONG).show();

                               }
                           }else{
                               updateVify();
                               Toast.makeText(this,"两次密码不相同，请重新输入",Toast.LENGTH_LONG).show();
                           }
                       }else{
                           Toast.makeText(this,"这不是手机号，请重新输入",Toast.LENGTH_LONG).show();
                       }
                    }else {
                            updateVify();
                        Toast.makeText(this,"信息不能为空",Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.line:
                    Code = "";
                    updateVify();
                    break;
                 default:
                     break;
            }
    }
    /*
    更新验证码
     */
    public void updateVify(){
        //生成验证码
        vifycationCode = new VifycationCode();
        Code = vifycationCode.getCode();
        tv_vifys.setText(Code);
    }
    /*
        发送json数据给服务器
     */
    private void sendOkhttpInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOkHttpRequest(url,json,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData=response.body().string();
                        System.out.println(responseData);
                        if (response != null){
                            String status = parseJSONWithGSON(responseData);
                            System.out.println(status);
                            if (status.equals("success")){
                                //Toast.makeText(RegisterActivity.this,"用户注册成功",Toast.LENGTH_LONG).show();
                                //跳转到登陆页面
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                               // Toast.makeText(RegisterActivity.this,"用户注册失败",Toast.LENGTH_LONG).show();
                            }
                            //显示UI界面，调用的showResponse方法
                            // showResponse(responseData.toString());
                        }else
                            System.out.println("返回数据为空");
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //在这里进行异常情况处理
                        //Toast.makeText(RegisterActivity.this,"网络出错，请检查网络",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
    public String parseJSONWithGSON(String json){
        JSONObject jsonObject;
        String status = "";
        try {
            jsonObject = new JSONObject(json);
            status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
    //背景视频部分

    /**
     * 初始化,
     */
    private void initView() {
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //videoview.start();
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
            }
        });

    }

    //
    //返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        super.onStop();
        videoview.stopPlayback();
    }
}
