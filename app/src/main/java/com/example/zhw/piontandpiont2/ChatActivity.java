package com.example.zhw.piontandpiont2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Adapter.ChatAdapter;
import com.example.zhw.piontandpiont2.Bean.ChatMessageData;
import com.example.zhw.piontandpiont2.Listener.KeyboardChangeListener;
import com.example.zhw.piontandpiont2.Threadpack.SendChatMessageThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.db.QueryData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, KeyboardChangeListener.KeyBoardListener{
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

    //监听键盘是否收起
    private KeyboardChangeListener mKeyboardChangeListener;

    public static String isChatActivity;
    //表情包
    private ImageView expression;
    private int[] imageIds = new int[107];
    private Dialog builder;

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
        //表情包
        expression = findViewById(R.id.team_singlechat_id_expression);
        expression.setOnClickListener(this);

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
        if (myChatAdapter.getCount() !=0){
            lv_chat_dialog.setSelection(myChatAdapter.getCount()-1);
        }
        btn.setOnClickListener(this);
        home_add.setOnClickListener(this);
        btn_chat_message_send.setOnClickListener(this);
        bt_chat_add.setOnClickListener(this);
        chat_image_back.setOnClickListener(this);

        group_user_position.setOnClickListener(this);
        group_user_connection.setOnClickListener(this);

        //设置键盘的监听事件
        mKeyboardChangeListener = new KeyboardChangeListener(this);
        mKeyboardChangeListener.setKeyBoardListener(this);

        //listView的底部显示
        System.out.println("是否显示"+lv_chat_dialog.getLastVisiblePosition()+"......"+lv_chat_dialog.getFirstVisiblePosition());
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
                InputMethodManager imm = (InputMethodManager) ChatActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(ChatActivity.this.getWindow().getDecorView().getWindowToken(), 0);

                break;
            case R.id.home_add: ///群资料

                Intent groupInfoActivity = new Intent(this,GroupInfoActivity.class);
                groupInfoActivity.putExtra("groupId",groupId);
                groupInfoActivity.putExtra("uuid",uuid);
                groupInfoActivity.putExtra("groupRole",groupRole);
                startActivity(groupInfoActivity);
                    break;
            case R.id.chat_image_back:  //返回键
                    Intent intent = new Intent(this,HomeActivity.class);
                    intent.putExtra("data",HomeActivity.data);
                    intent.putExtra("TAG",HomeActivity.TAG);
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
                if (chat_message_content.length() == 0){
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
            case R.id.team_singlechat_id_expression: //表情包
                createExpressionDialog();
                break;
            default :
                break;
        }
    }
    //键盘的监听事件
    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if(isShow){
            ll.setVisibility(ll.GONE);
        }else{
           // ll.setVisibility(ll.VISIBLE);
        }
    }
    /**
     * 创建一个表情选择对话框
     */
    private void createExpressionDialog() {
        builder = new Dialog(ChatActivity.this);
        GridView gridView = createGridView();
        builder.setContentView(gridView);
        builder.setTitle("默认表情");
        builder.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeResource(getResources(), imageIds[arg2 % imageIds.length]);
                ImageSpan imageSpan = new ImageSpan(ChatActivity.this, bitmap);
                String str = null;
                if(arg2<10){
                    str = "f00"+arg2;
                }else if(arg2<100){
                    str = "f0"+arg2;
                }else{
                    str = "f"+arg2;
                }
                SpannableString spannableString = new SpannableString(str);
                spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                et_chat_message.append(spannableString);
                builder.dismiss();
            }
        });
    }

    /**
     * 生成一个表情对话框中的gridview
     * @return
     */
    private GridView createGridView() {
        final GridView view = new GridView(this);
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        //生成107个表情的id，封装
        for(int i = 0; i < 107; i++){
            try {
                if(i<10){
                    Field field = R.drawable.class.getDeclaredField("f00" + i);
                    int resourceId = Integer.parseInt(field.get(null).toString());
                    imageIds[i] = resourceId;
                }else if(i<100){
                    Field field = R.drawable.class.getDeclaredField("f0" + i);
                    int resourceId = Integer.parseInt(field.get(null).toString());
                    imageIds[i] = resourceId;
                }else{
                    Field field = R.drawable.class.getDeclaredField("f" + i);
                    int resourceId = Integer.parseInt(field.get(null).toString());
                    imageIds[i] = resourceId;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("image", imageIds[i]);
            listItems.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.team_layout_single_expression_cell, new String[]{"image"}, new int[]{R.id.image});
        view.setAdapter(simpleAdapter);
        view.setNumColumns(6);
        view.setBackgroundColor(Color.rgb(214, 211, 214));
        view.setHorizontalSpacing(1);
        view.setVerticalSpacing(1);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setGravity(Gravity.CENTER);
        return view;
    }
    public static Handler getChat_handler(){
        return chat_handler;
    }

}
