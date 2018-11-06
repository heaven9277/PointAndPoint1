package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn,home_add;//群资料
    public Button bt_chat_add;//加号
    public Button btn_chat_message_send;//发送按钮
    public ImageView chat_image_back;
    private LinearLayout ll;
    public LinearLayout group_user_position;//位置
    public LinearLayout group_user_connection;//联系

    public EditText et_chat_message;//内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_chat);
        initView();
    }

    private void initView() {
        ll= findViewById(R.id.ll);
        home_add = findViewById(R.id.home_add);
        btn=findViewById(R.id.bt_chat_add);
        bt_chat_add = findViewById(R.id.bt_chat_add);
        chat_image_back = findViewById(R.id.chat_image_back);
        group_user_position = findViewById(R.id.group_user_position);
        group_user_connection = findViewById(R.id.group_user_connection);

        btn.setOnClickListener(this);
        home_add.setOnClickListener(this);
        bt_chat_add.setOnClickListener(this);
        chat_image_back.setOnClickListener(this);

        group_user_position.setOnClickListener(this);
        group_user_connection.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_chat_add:
                // 隐藏底部菜单框
                if (ll.getVisibility() == ll.VISIBLE)
                    ll.setVisibility(ll.GONE);
                else
                    ll.setVisibility(ll.VISIBLE);
                break;
            case R.id.home_add: ///群资料

                    Intent groupInfoActivity = new Intent(this,GroupInfoActivity.class);
                    startActivity(groupInfoActivity);
                    break;
            case R.id.chat_image_back:  //返回键
                    Intent chatActivty = new Intent(this,ChatActivity.class);
                    startActivity(chatActivty);
                    break;
            case R.id.group_user_connection:    //联系
                    Intent groupuserinfo = new Intent(this,GroupUserInfoActivity.class);
                    startActivity(groupuserinfo);
                    break;
            case R.id.group_user_position:  //位置
                    Intent groupposition = new Intent(this,GroupPositionActivity.class);
                    startActivity(groupposition);
                    break;
            default :
                break;
        }

    }
}
