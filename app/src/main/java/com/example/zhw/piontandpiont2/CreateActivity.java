package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Networksockets.WsManager;
import com.example.zhw.piontandpiont2.Util.BufferChange;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.example.zhw.piontandpiont2.Util.PareJson;
import com.loopj.android.image.SmartImageView;
import com.neovisionaries.ws.client.WebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.Objects;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_create_invite, button_create;
    private SmartImageView gourp_manager_image;//群主头像
    private TextView tv_group_manager;
    public ImageView create_image_back;//返回键
    public TextInputEditText group_name_edit;//群名称
    public TextInputEditText group_hobby_edit;//群爱好
    public TextInputEditText group_descript_deit;//群描述
    public WsManager wsManager;
    public WebSocket webSocket;
    public static String userName;
    String data;
    public static Context context;
    public static String TAG = "Createactivity";
    public static String TAG2 = "Mainactivity";
    public static String user_portrait;//头像
    public static String user_h_name;//昵称

    ///接收创建群的消息

    private static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到创建群的信息");
            int what = msg.what;
            String datas = (String) msg.obj;
            switch (what){
                case 9:
                    //获取成功
                        Toast.makeText(context, PareJson.getJsonInfo(datas),Toast.LENGTH_LONG).show();
                        Intent homeAvtivity = new Intent(context,HomeActivity.class);
                        homeAvtivity.putExtra("data",datas);
                        homeAvtivity.putExtra("username",userName);
                        homeAvtivity.putExtra("TAG",TAG);
                        homeAvtivity.putExtra("user_portrait",user_portrait);
                        homeAvtivity.putExtra("user_h_name",user_h_name);
                        context.startActivity(homeAvtivity);
                    break;
                case 10:
                    //获取失败
                    Toast.makeText(context, PareJson.getJsonInfo(datas),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        intView();
    }
    private void intView() {
        button_create_invite = findViewById(R.id.button_create_invite);
        button_create = findViewById(R.id.button_create);
        create_image_back = findViewById(R.id.create_image_back);//返回键
        gourp_manager_image = findViewById(R.id.gourp_manager_image);
        tv_group_manager = findViewById(R.id.tv_group_manager);

        group_name_edit = findViewById(R.id.group_name_edit);
        group_hobby_edit = findViewById(R.id.group_hobby_edit);
        group_descript_deit = findViewById(R.id.group_descript_deit);

        button_create_invite.setOnClickListener(this);
        button_create.setOnClickListener(this);
        create_image_back.setOnClickListener(this);

        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        data = intent.getStringExtra("data");
        user_portrait = intent.getStringExtra("user_portrait");
        user_h_name = intent.getStringExtra("user_h_name");
        System.out.println(user_h_name+"？？？？？？？？？？？？？");

        gourp_manager_image.setImageUrl(user_portrait,R.drawable.users);
        tv_group_manager.setText(user_h_name.replace("\"", ""));
        context= this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_invite:

                break;
            case R.id.button_create:
                //将数据发送给后台服务器
                wsManager = WsManager.getInstance();
                if (wsManager != null){
                    webSocket = wsManager.getWebsocket();
                    if (group_name_edit.getText().toString().trim() != null&&group_hobby_edit.getText().toString().trim() != null
                             &&  group_descript_deit.getText().toString().trim() !=null){
                        //发送请求
                                String create_json = Jsonpack.getCreateGroupData(userName,group_name_edit.getText().toString().trim(),
                                        group_descript_deit.getText().toString().trim(),group_hobby_edit.getText().toString().trim());
                                ByteBuffer bf_createGroup = BufferChange.getByteBuffer(create_json);
                                webSocket.sendBinary(bf_createGroup.array());
                                System.out.println("发送创建群的消息");
                    }else{
                        Toast.makeText(this,"信息不能为空，请填写",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.create_image_back:
                Intent homeintent = new Intent(this,HomeActivity.class);
                homeintent.putExtra("username",userName);
                homeintent.putExtra("data",data);
                System.out.println("????????????常量"+HomeActivity.TEST);
                if (HomeActivity.TEST.equals("GroupOut")){
                    homeintent.putExtra("TAG",TAG);
                }else {
                    homeintent.putExtra("TAG",TAG2);
                }
                homeintent.putExtra("user_portrait",user_portrait);
                homeintent.putExtra("user_h_name",user_h_name);
                startActivity(homeintent);
                finish();
                break;
        }
    }
    public static Handler getCreateGroupHandler(){
        return  mHandler;
    }
}
