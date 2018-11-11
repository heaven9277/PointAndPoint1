package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Adapter.ChatAdapter;
import com.example.zhw.piontandpiont2.Threadpack.ChatThread;
import com.example.zhw.piontandpiont2.Threadpack.SendChatMessageThread;
import com.example.zhw.piontandpiont2.Bean.EnterGroupData;
import com.example.zhw.piontandpiont2.Util.PareJson;

import java.util.ArrayList;
import java.util.List;

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
    public static ChatAdapter myChatAdapter;//适配器

    //接收上一个activity传过来的数据
    public String uuid;
    public String groupName;
    public static String groupId;
    public static String groupRole;
    //数据
    public static String data;
    public static Context context;
    public static List<EnterGroupData> chatDatas = new ArrayList<>();
    //定义一个handler进行消息接收
    private static Handler chat_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到聊天？？？？？？？？？？？？？？信息");
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 11:
                    //获取成功
                    chatDatas = PareJson.getEnterGroupData(data);
                    myChatAdapter.notifyDataSetChanged();
                    break;
                case 12:
                    //获取失败
                    Toast.makeText(context,"获取失败，请重新获取",Toast.LENGTH_LONG).show();
                    break;
                case 13:
                    //成功
                    Toast.makeText(context,"发送成功",Toast.LENGTH_LONG).show();
                    break;
                case 14:
                    //失败、
                    break;
                default:
                    break;
            }
        }
    };
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
        btn_chat_message_send = findViewById(R.id.btn_chat_message_send);
        et_chat_message = findViewById(R.id.et_chat_message);//内容
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
        groupRole = intent.getStringExtra("groupRole");
        chat_title.setText(groupName);

        //listView
        lv_chat_dialog = findViewById(R.id.lv_chat_dialog);
        myChatAdapter = new ChatAdapter(this,chatDatas,uuid);
        lv_chat_dialog.setAdapter(myChatAdapter);
        context= this;

        btn.setOnClickListener(this);
        home_add.setOnClickListener(this);
        btn_chat_message_send.setOnClickListener(this);
        bt_chat_add.setOnClickListener(this);
        chat_image_back.setOnClickListener(this);

        group_user_position.setOnClickListener(this);
        group_user_connection.setOnClickListener(this);

        //发送聊天页面请求
        ChatThread chatThread = new ChatThread(this,groupName,uuid,groupId);
        chatThread.start();

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
                groupInfoActivity.putExtra("groupId",groupId);
                groupInfoActivity.putExtra("uuid",uuid);
                groupInfoActivity.putExtra("groupRole",groupRole);
                startActivity(groupInfoActivity);
               // SendGroupInfoThread sendGroupInfoThread = new SendGroupInfoThread(groupId);
               // sendGroupInfoThread.start();
                    break;
            case R.id.chat_image_back:  //返回键
                   // Intent homeActivity = new Intent(this,HomeActivity.class);
                   // startActivity(homeActivity);
                    finish();
                    break;
            case R.id.group_user_connection:    //联系
                    Intent connectMember = new Intent(this,ConnectMemberActivity.class);
                    connectMember.putExtra("groupId",groupId);
                    startActivity(connectMember);
                    break;
            case R.id.group_user_position:  //位置
                    Intent groupposition = new Intent(this,GroupPositionActivity.class);
                    startActivity(groupposition);
                    break;
            case R.id.btn_chat_message_send://发送
                if (et_chat_message.getText().toString().trim() != null){
                    SendChatMessageThread sendChatMessageThread = new SendChatMessageThread(uuid,groupId,et_chat_message.getText().toString().trim());
                    sendChatMessageThread.start();
                }else{
                    Toast.makeText(this,"内容不能为空",Toast.LENGTH_LONG).show();
                }
                break;
            default :
                break;
        }
    }
    public static Handler getChat_handler(){
        return chat_handler;
    }
}
