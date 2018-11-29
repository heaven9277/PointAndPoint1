package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Adapter.MyRecyAdapter;
import com.example.zhw.piontandpiont2.Bean.GroupDataBean;
import com.example.zhw.piontandpiont2.Threadpack.SendGroupInfoThread;
import com.example.zhw.piontandpiont2.Threadpack.SendOutGroupThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.Util.SpaceItemDecoration;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/*
群资料的Activity
 */
public class GroupInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public Button edit_group,out_group;//编辑群聊和退出群聊
    public ImageView image_back;//返回
    public static SmartImageView group_image;//群聊头像
    public static TextView group_name;//群名字
    public static TextView group_id;//群id
    public static TextView group_desc;//群公告
    public static TextView manager_group;//群管理
    public static TextView clear_group_message;//清空群消息
    public static ProgressBar progressBar;
    public static ScrollView scrollView;
    //数据
    public static String datas;
    public static String groupId;
    public static Context context;
    public static String uuid;
    public static String groupRole;

    public static String groupName;
    public static String groupDsc;
    public static String groupPro;//头像
    public static RecyclerView recyclerView;//横向布局
    public static List<GroupDataBean.MembersBean> members; //数据
    public static MyRecyAdapter myRecyAdapter;

    //定义一个handler进行消息接收
    private static Handler GroupInfoHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到群资料？？？？？？？？？？？？？？信息");
            int what = msg.what;
            String data = (String) msg.obj;
            System.out.println(data);
            switch (what){
                case 15:
                    //成功
                    datas =data;
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    initview(datas);
                    break;
                case 16:
                    //获取失败
                    Toast.makeText(context,"获取失败，请重新获取",Toast.LENGTH_LONG).show();
                    break;
                case 19:
                    //退出成功
                    Toast.makeText(context, ParseJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
                    HomeActivity.TEST="GroupOut";
                    Intent homeIntent = new Intent(context,HomeActivity.class);
                    homeIntent.putExtra("data",data);
                    homeIntent.putExtra("TAG",CreateActivity.TAG);
                    context.startActivity(homeIntent);
                     break;
                case  20:
                    //退出失败
                    data =datas;
                    initview(data);
                    Toast.makeText(context, ParseJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
       //初始化组件
        initView();
        DarkStatusBar.setDarkStatusIcon(this);
    }

    private void initView() {
        edit_group = findViewById(R.id.edit_group);
        out_group = findViewById(R.id.out_group);
        image_back = findViewById(R.id.image_back);
        group_image = findViewById(R.id.group_image);
        group_name = findViewById(R.id.group_name);
        group_id = findViewById(R.id.group_id);
        group_desc = findViewById(R.id.group_desc);
        manager_group = findViewById(R.id.manager_group);
        clear_group_message = findViewById(R.id.clear_group_message);

        progressBar = findViewById(R.id.progressbar);
        scrollView = findViewById(R.id.scrollview);

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        //LinearLayoutManager 种 含有3 种布局样式  第一个就是最常用的 1.横向 , 2. 竖向,3.偏移
        recyclerView.setLayoutManager(ms);  //给RecyClerView 添加设置好的布局样式
        myRecyAdapter = new MyRecyAdapter();
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setAdapter(myRecyAdapter);
        //接收上一个activity的信息
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        uuid = intent.getStringExtra("uuid");
        groupRole = intent.getStringExtra("groupRole");
        System.out.println("角色"+groupRole);

        System.out.println("用户？？？？???????"+uuid);
        if (groupRole.equals("0")){
            out_group.setText("解散群");
        }
        //绑定监听器
        edit_group.setOnClickListener(this);
        out_group.setOnClickListener(this);
        image_back.setOnClickListener(this);
        manager_group.setOnClickListener(this);
        clear_group_message.setOnClickListener(this);
       //发送群资料的请求
         SendGroupInfoThread sendGroupInfoThread = new SendGroupInfoThread(groupId);
         sendGroupInfoThread.start();
         context = this;
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.edit_group: //编辑群资料
                Intent editGroupActivity = new Intent(this,EditGroupActivity.class);
                editGroupActivity.putExtra("group_image",groupPro);
                editGroupActivity.putExtra("group_name",groupName);
                editGroupActivity.putExtra("groupId",groupId);
                editGroupActivity.putExtra("group_desc",groupDsc);
                editGroupActivity.putExtra("groupRole",groupRole);
                startActivity(editGroupActivity);
                break;
            case R.id.out_group:  //退出群聊
                    //成员
                    SendOutGroupThread sendOutGroupThread = new SendOutGroupThread(groupRole,groupId,uuid);
                    sendOutGroupThread.start();
                    System.out.println("群号？？？？"+uuid+"用户"+groupId);
                break;
            case R.id.image_back: //返回
//                    Intent chatIntent = new Intent(this,ChatActivity.class);
//                    chatIntent.putExtra("groupName",ChatActivity.groupName);
//                    chatIntent.putExtra("uuid",ChatActivity.uuid);
//                    chatIntent.putExtra("GroupId",ChatActivity.groupId);
//                    chatIntent.putExtra("groupRole",ChatActivity.groupRole);
//                    startActivity(chatIntent);
                    finish();
                break;
            case R.id.manager_group: //管理群
                if (groupRole.equals("0")){
                    Intent managerActivity = new Intent(this,ManageGroupActivity.class);
                    managerActivity.putExtra("groupId",groupId);
                    managerActivity.putExtra("uuid",uuid);
                    managerActivity.putExtra("data",datas);

                    startActivity(managerActivity);
                    System.out.println("进入管理群？？？？？？");
                }else{
                    Toast.makeText(this,"您不是该群管理员，不能管理该群",Toast.LENGTH_LONG).show();
                }
                break;
            case  R.id.clear_group_message: //清空群资料
                break;
            default:
                break;
        }
    }
    public  static Handler getGroupInfoHandler(){
        return GroupInfoHandler;
    }
    public static void initview(String data){
        group_name.setText(ParseJson.getGroupData(data).getGroupName());
        group_id.setText(ParseJson.getGroupData(data).getGroupNumber());
        group_desc.setText(ParseJson.getGroupData(data).getGroupAnoun());
        group_image.setImageUrl(ParseJson.getGroupData(data).getGroupPortrait(),R.drawable.loading);
        groupName = ParseJson.getGroupData(data).getGroupName();
        groupDsc = ParseJson.getGroupData(data).getGroupAnoun();
        groupPro = ParseJson.getGroupData(data).getGroupPortrait();
        System.out.println(groupName+" "+groupPro+"  "+groupDsc);
        members = ParseJson.getMemberList(data);
        //提示更新
        myRecyAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        ChatActivity.groupName = intent.getStringExtra("groupName");
        SendGroupInfoThread sendGroupInfoThread = new SendGroupInfoThread(groupId);
        sendGroupInfoThread.start();
    }
}
