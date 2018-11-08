package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Adapter.ChatAdapter;
import com.example.zhw.piontandpiont2.Threadpack.SendChatDataThread;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn,home_add;//群资料
    public Button bt_chat_add;//加号
    public Button btn_chat_message_send;//发送按钮
    public ImageView chat_image_back; //返回按钮
    public TextView chat_title;//群的名称
    private LinearLayout ll;
    public LinearLayout group_user_position;//位置
    public LinearLayout group_user_connection;//联系

    public EditText et_chat_message;//内容
    public ListView lv_chat_dialog;//聊天的listView
    public ChatAdapter myChatAdapter;//适配器

    //接收上一个activity传过来的数据
    public String uuid;
    public String groupName;
    public String groupId;
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
        chat_title = findViewById(R.id.chat_title);
        group_user_position = findViewById(R.id.group_user_position);
        group_user_connection = findViewById(R.id.group_user_connection);

        //接收到上一个activity的数据
        Intent intent = getIntent();
        groupName = intent.getStringExtra("GroupName");
        uuid = intent.getStringExtra("uuid");
        groupId = intent.getStringExtra("GroupId");
        chat_title.setText(groupName);
        //listView
        lv_chat_dialog = findViewById(R.id.lv_chat_dialog);
        myChatAdapter = new ChatAdapter(this);
        lv_chat_dialog.setAdapter(myChatAdapter);


        btn.setOnClickListener(this);
        home_add.setOnClickListener(this);
        bt_chat_add.setOnClickListener(this);
        chat_image_back.setOnClickListener(this);

        group_user_position.setOnClickListener(this);
        group_user_connection.setOnClickListener(this);

        //向后台发送数据
        SendChatDataThread sendChatDataThread = new SendChatDataThread(groupName,uuid,groupId);
        sendChatDataThread.start();
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
                    Intent connectMember = new Intent(this,ConnectMemberActivity.class);
                    startActivity(connectMember);
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
