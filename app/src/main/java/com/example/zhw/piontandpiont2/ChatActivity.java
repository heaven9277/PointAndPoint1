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
import com.example.zhw.piontandpiont2.Bean.ChatMessageData;
import com.example.zhw.piontandpiont2.Threadpack.SendChatMessageThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.db.QueryData;

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
    public static String uuid;
    public static String groupName;
    public static String groupId;
    public static String groupRole;
    //数据
    public static String data;
    public static Context context;
    public static List<ChatMessageData> chatMessageDataList;

    public static String isChatActivity;
    //定义一个handler进行消息接收
    private static Handler chat_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到聊天？？？？？？？？？？？？？？信息");
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 1:
                    //获取成功
                   //进行更新
                    chatMessageDataList = QueryData.getchatMessageList(context,groupId);
                    myChatAdapter.notifyDataSetChanged();
                    break;
                case 2:
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
        com.example.zhw.piontandpiont2.Util.AndroidBug5497Workaround.assistActivity(this);
        initView();
    }
    private void initView() {
        DarkStatusBar.setDarkStatusIcon(this);
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

        context= this;
        isChatActivity= "Chatactivity";
        HomeActivity.isHomeActivity="";
        //初始化list数据
        chatMessageDataList = new ArrayList<>();
        chatMessageDataList = QueryData.getchatMessageList(this,groupId);
        //listView
        lv_chat_dialog = findViewById(R.id.lv_chat_dialog);
        myChatAdapter = new ChatAdapter(this);
        lv_chat_dialog.setAdapter(myChatAdapter);

        btn.setOnClickListener(this);
        home_add.setOnClickListener(this);
        btn_chat_message_send.setOnClickListener(this);
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
                groupInfoActivity.putExtra("groupId",groupId);
                groupInfoActivity.putExtra("uuid",uuid);
                groupInfoActivity.putExtra("groupRole",groupRole);
                startActivity(groupInfoActivity);
                    break;
            case R.id.chat_image_back:  //返回键
//                    Intent intent = new Intent(this,HomeActivity.class);
//                    intent.putExtra("data",HomeActivity.data);
//                    intent.putExtra("TAG",HomeActivity.TAG);
                    System.out.print("点击返回....");
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
                String chat_message_content=et_chat_message.getText().toString();
                if (chat_message_content.equals("")){
                    System.out.println("221234hhdhhdddhdhdhdh");
                    Toast.makeText(this,"内容不能为空",Toast.LENGTH_LONG).show();
                }
                else{
                    System.out.println(et_chat_message.getText().toString().trim()+"hhdhhdddhdhdhdh");
                    chat_message_content = et_chat_message.getText().toString().trim();
                    ChatMessageData chatMessageData = new ChatMessageData();
                    chatMessageData.setGroupMessage(chat_message_content);
                    chatMessageData.setUuid(MainActivity.main_username);
                    chatMessageData.setGroupId(groupId);
                    System.out.println(MainActivity.main_username+ " "+chat_message_content+" "+groupId+" "+chatMessageData+"昵称"+MainActivity.user_h_name+"头像"+MainActivity.user_portrait);
                    chatMessageDataList.add(chatMessageData);
                    myChatAdapter.notifyDataSetChanged();
                    //将数据放进数据库
                    //QueryData.InsertData(this,uuid,groupId,chat_message_content,MainActivity.user_h_name,MainActivity.user_portrait);
                    System.out.println("内容："+chat_message_content);
                    SendChatMessageThread sendChatMessageThread = new SendChatMessageThread(uuid,groupId,chat_message_content);
                    sendChatMessageThread.start();
                    et_chat_message.setText("");
                }
                break;
            default :
                break;
        }
    }
    public static Handler getChat_handler(){
        return chat_handler;
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        chat_title.setText(groupName);
    }
}
