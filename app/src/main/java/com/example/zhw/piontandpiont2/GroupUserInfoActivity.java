package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Bean.GroupMemberInfo;
import com.example.zhw.piontandpiont2.Threadpack.SendUserInfoThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.loopj.android.image.SmartImageView;

/*
点击群聊成员的Activity
 */
public class GroupUserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView image_back;//返回键
    public static SmartImageView user_image;//用户头像
    public static TextView user_name;//用户名
    public static TextView user_email;//用户邮箱
    public static TextView user_telphone;
    public static TextView self;//个性签名
    public Button content_user;//联系ta
    String uuid;//uuid
    public static String phone;
    public static ProgressBar pro;
    public static RelativeLayout relativeLayout;
    public static Handler userinfo_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            super.handleMessage(msg);
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what) {
                case 1:
                    pro.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    GroupMemberInfo groupMemberInfo = ParseJson.getGroupMemberInfo(data);
                    user_image.setImageUrl(groupMemberInfo.getUserPortrait(),R.drawable.users);
                    System.out.println(groupMemberInfo.getUserPortrait()+"头像");
                    user_name.setText(groupMemberInfo.getUserName());
                    user_email.setText(groupMemberInfo.getUserEmail());
                    self.setText("个性签名："+groupMemberInfo.getUserSign());
                    user_telphone.setText("电话："+groupMemberInfo.getUserPhone());
                    phone = groupMemberInfo.getUserPhone();
                    System.out.println(groupMemberInfo.getUserPhone()+"电话");
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_user_info);
        initView();
    }

    private void initView() {
        DarkStatusBar.setDarkStatusIcon(this);
        image_back = findViewById(R.id.image_back);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        self = findViewById(R.id.self);
        content_user = findViewById(R.id.content_user);
        pro = findViewById(R.id.pro);
        relativeLayout = findViewById(R.id.re);
        user_telphone = findViewById(R.id.user_telphone);
        image_back.setOnClickListener(this);
        content_user.setOnClickListener(this);
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
        //从上个Acitivity接受数据
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        //开始新获取数据
        SendUserInfoThread sendUserInfoThread = new SendUserInfoThread(uuid);
        sendUserInfoThread.start();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_back://返回键
                finish();
                break;
            case R.id.content_user: //联系ta
                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
                startActivity(Intent);
                break;
        }
    }
}
