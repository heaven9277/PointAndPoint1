package com.example.zhw.piontandpiont2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Adapter.MemberAdapter;
import com.example.zhw.piontandpiont2.Bean.ConnectMemberBean;
import com.example.zhw.piontandpiont2.Threadpack.SendMemberThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.PareJson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ConnectMemberActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,MemberAdapter.InnerItemOnclitckListener {
    public ListView member_list;
    public static MemberAdapter menuAdapter;
    public static List<ConnectMemberBean> list;
    public static ProgressBar pro;
    public static Handler memberHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到联系的信息");
            int what = msg.what;
            String datas = (String) msg.obj;
            switch (what){
                case 1:
                    //成功
                    pro.setVisibility(View.GONE);
                    list = PareJson.getConnectMemberData(datas);
                    menuAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    //失败

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_member);
        DarkStatusBar.setDarkStatusIcon(this);
        initView();
    }

    private void initView() {
        pro =findViewById(R.id.connect_pro);
        member_list = findViewById(R.id.connect_listView);
        menuAdapter = new MemberAdapter(this);
        menuAdapter.setOnInnerItemOnClickListener(this);
        //做到这里adapter
        member_list.setAdapter(menuAdapter);
        member_list.setOnItemClickListener(this);
        //发送请求
        System.out.println("群号：：："+ChatActivity.groupId);
        SendMemberThread sendMemberThread = new SendMemberThread(ChatActivity.groupId);
        sendMemberThread.start();
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent GroupUserInfo = new Intent(this,GroupInfoActivity.class);
        startActivity(GroupUserInfo);
    }


    @Override
    public void itemClick(View v) {
        int position = (int) v.getTag();
        System.out.println("点你////////////////"+position);
        String number = list.get(position).getUserPhone();
        Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + number));//跳转到拨号界面，同时传递电话号码
        startActivity(Intent);
    }
}
