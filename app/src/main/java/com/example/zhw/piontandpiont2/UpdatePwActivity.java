package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Networksockets.AdressHttp;
import com.example.zhw.piontandpiont2.Networksockets.HttpUtil;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.Jsonpack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class UpdatePwActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_updatepw;
    public EditText et_passwd,et_repasswd;
    String telphone;
    public final String url = AdressHttp.getUrl()+"register/post"; //网址
    String update_passwd_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pw);
        DarkStatusBar.setDarkStatusIcon(this);
        btn_updatepw = findViewById(R.id.btn_updatepw);
        et_passwd =findViewById(R.id.et_passwd);
        et_repasswd = findViewById(R.id.et_repasswd);
        btn_updatepw.setOnClickListener(this);
        telphone = getIntent().getStringExtra("telphone");
    }

    @Override
    public void onClick(View view) {
        //将数据传给服务器
        System.out.println("电话号码："+telphone);

        if (et_repasswd.getText().toString() != null &&et_repasswd.getText().toString() != null &&et_repasswd.getText().toString().equals(et_repasswd.getText().toString())){
            //在根据服务器的返回值，跳转到登陆页面
            //开始把信息分装为json数据
            update_passwd_json = Jsonpack.getRepsswdJosn(telphone,et_passwd.getText().toString());
            System.out.println(update_passwd_json);
            //发送数据
            sendOkhttpInfo();
        }else{
            //两次密码不一样
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_LONG).show();
        }
    }

    private void sendOkhttpInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOkHttpRequest(url,update_passwd_json,new okhttp3.Callback(){
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
                                Intent intent = new Intent(UpdatePwActivity.this,MainActivity.class);
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
    public String parseJSONWithGSON(String pareJson){
        JSONObject jsonObject;
        String status = "";
        try {
            jsonObject = new JSONObject(pareJson);
            status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
}
