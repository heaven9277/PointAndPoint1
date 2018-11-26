package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Adapter.ManagerGourpAdapter;
import com.example.zhw.piontandpiont2.Threadpack.SendManagerUserThread;
import com.example.zhw.piontandpiont2.Bean.GroupDataBean;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.ParseJson;

import java.util.List;

/*
    管理群的Activity
 */

public class ManageGroupActivity extends AppCompatActivity implements View.OnClickListener,ManagerGourpAdapter.InnerItemOnclitckListener,AdapterView.OnItemClickListener {
    public static ListView group_user_lsit;
    public ImageView manger_image_back;//返回键
    public static String groupId;
    public static String uuid;
    public static String data;
    public static List<GroupDataBean.MembersBean> members;//成员数组
    public static ManagerGourpAdapter managerGourpAdapter;
    public static Context context;
    public Button complete;
    ///接收删除群成员的消息

    private static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到删除群成员的信息");
            int what = msg.what;
            String datas = (String) msg.obj;
            switch (what){
                case 1:
                    Toast.makeText(context, ParseJson.getJsonInfo(datas),Toast.LENGTH_LONG).show();
                    //提示更新
                    members = ParseJson.getMemberListForDelete(datas);
                    System.out.println(members +"dsfasfafadfsdfsdfsd成员数组");
                    managerGourpAdapter = new ManagerGourpAdapter(context,members);
                    group_user_lsit.setAdapter(managerGourpAdapter);
                    managerGourpAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    //获取失败
                    Toast.makeText(context, ParseJson.getJsonInfo(datas),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);
        initViews();
    }

    private void initViews() {
        DarkStatusBar.setDarkStatusIcon(this);
        group_user_lsit = findViewById(R.id.group_user_lsit);
        manger_image_back = findViewById(R.id.manger_image_back);
        complete = findViewById(R.id.complete);
        manger_image_back.setOnClickListener(this);
        complete.setOnClickListener(this);
        Intent intent =getIntent();
        groupId  = intent.getStringExtra("groupId");
        uuid = intent.getStringExtra("uuid");
        data  = intent.getStringExtra("data");
        System.out.println(data==null +" "+data+"?????????????");
        if (data==null){
            members = null;
        }else{
            members = ParseJson.getMemberList(data);
        }
        managerGourpAdapter = new ManagerGourpAdapter(this, members);
        managerGourpAdapter.setOnInnerItemOnClickListener(this);
        group_user_lsit.setAdapter(managerGourpAdapter);
        group_user_lsit.setOnItemClickListener(this);
        context = this;
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.manger_image_back:  //返回键
                Intent groupInfoActivity = new Intent(this,GroupInfoActivity.class);
                groupInfoActivity.putExtra("groupId",groupId);
                groupInfoActivity.putExtra("uuid",uuid);
                groupInfoActivity.putExtra("groupRole",GroupInfoActivity.groupRole);
                startActivity(groupInfoActivity);
                finish();
                break;
            case R.id.complete:
                Intent groupInfoActivity1 = new Intent(this,GroupInfoActivity.class);
                groupInfoActivity1.putExtra("groupId",groupId);
                groupInfoActivity1.putExtra("uuid",uuid);
                groupInfoActivity1.putExtra("groupRole",GroupInfoActivity.groupRole);
                startActivity(groupInfoActivity1);
                finish();
                break;
        }
    }

    //item的button的点击事件
    @Override
    public void itemClick(View v) {
            int position;
             position = (int) v.getTag();
             System.out.println("???????点击btn"+position);
             String delUuid = members.get(position).getGroupUserUuid();

             if (delUuid.equals(uuid)){
                 Toast.makeText(this,"不能删除自己",Toast.LENGTH_LONG).show();
             }else{
                 System.out.println("删除的群成员名"+delUuid);
                 //发送删除群成员请求
                 SendManagerUserThread sendManagerUserThread = new SendManagerUserThread(groupId,uuid,delUuid);
                 sendManagerUserThread.start();
                 System.out.println("发送删除群成员的请求");
             }
    }
    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(i +"??????????");
    }
    public static Handler getmHandler(){
        return mHandler;
    }
}
