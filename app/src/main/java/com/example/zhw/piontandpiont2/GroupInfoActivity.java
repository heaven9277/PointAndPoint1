package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Threadpack.SendGroupInfoThread;
import com.example.zhw.piontandpiont2.Threadpack.SendOutGroupThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.PareJson;
import com.loopj.android.image.SmartImageView;

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
                    Toast.makeText(context,PareJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
                    HomeActivity.TEST="GroupOut";
                    Intent homeIntent = new Intent(context,HomeActivity.class);
                    homeIntent.putExtra("data",data);
                    //homeIntent.putExtra("username",uuid);
                    homeIntent.putExtra("TAG",CreateActivity.TAG);
                    //homeIntent.putExtra("user_portrait",MainActivity.user_portrait);
                    //homeIntent.putExtra("user_h_name",MainActivity.user_h_name);
                    context.startActivity(homeIntent);

                     break;
                case  20:
                    //退出失败
                    data =datas;
                    initview(data);
                    Toast.makeText(context,PareJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
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
                    System.out.println("群号？？？？"+uuid);
                break;
            case R.id.image_back: //群头像
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
        group_name.setText(PareJson.getGroupData(data).getGroupName());
        group_id.setText(PareJson.getGroupData(data).getGroupNumber());
        group_desc.setText(PareJson.getGroupData(data).getGroupAnoun());
        group_image.setImageUrl(PareJson.getGroupData(data).getGroupPortrait(),R.drawable.group003);
        groupName = PareJson.getGroupData(data).getGroupName();
        groupDsc = PareJson.getGroupData(data).getGroupAnoun();
        groupPro = PareJson.getGroupData(data).getGroupPortrait();
        System.out.println(groupName+" "+groupPro+"  "+groupDsc);
    }
}
