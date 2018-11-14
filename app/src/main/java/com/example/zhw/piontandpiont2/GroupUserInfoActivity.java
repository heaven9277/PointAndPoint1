package com.example.zhw.piontandpiont2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.loopj.android.image.SmartImageView;

/*
点击群聊成员的Activity
 */
public class GroupUserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView image_back;//返回键
    public SmartImageView user_image;//用户头像
    public TextView user_name;//用户名
    public TextView user_email;//用户邮箱
    public TextView self;//个性签名
    public Button content_user;//联系ta
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

        image_back.setOnClickListener(this);
        content_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_back://返回键
                break;
            case R.id.content_user: //联系ta
                break;
        }
    }
}
