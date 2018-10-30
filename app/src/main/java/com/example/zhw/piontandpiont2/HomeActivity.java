package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zhw.piontandpiont2.Util.Homeutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // private TextView mTextMessage;
    public ListView mListView;
    public String data;
    //封装成数组
    List<Homeutil> groupsLists;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText("白名单");
                    return true;
                case R.id.navigation_dashboard:
                    //  mTextMessage.setText("黑名单");
                    return true;
                case R.id.navigation_notifications:
                    //  mTextMessage.setText("设置");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置为没有标题栏，也可以在AndroidManifest.xml文件设置
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

// 请求添加自定义标题栏
// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //    setContentView(R.layout.activity_main);
// 设置自定义标题栏布局
// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);

        setContentView(R.layout.activity_main);
        init();


        //  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    //初始化
    public void init(){
        //初始化组件
        mListView = findViewById(R.id.grorp_list);
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter();
        mListView.setAdapter(myBaseAdapter);


        //接收由登陆发送过来的数据
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        System.out.println(data+"接收");
        //解析数据，封装进数组
        initData(data);
    }
    public void initData(String data){
        groupsLists = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("groups");
            //得到数组信息
            System.out.println(jsonArray+"数组");
            for (int i=0;i<jsonArray.length();i++){
                JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                for (int j = 0;j<jsonArray1.length();j++){
                    String groupName = jsonArray1.getString(1);
                    String groupPortrait = jsonArray1.getString(2);
                    String lastestGroupUser = jsonArray1.getString(3);
                    String lastGroupNumberName = jsonArray1.getString(4);
                    String lastGroupSendTime = jsonArray1.getString(5);
                    String lastestGroupMessage = jsonArray1.getString(6);
                    String groupMessageCount = jsonArray1.getString(7);
                    String groupRole = jsonArray1.getString(8);
                    Homeutil homeutil = new Homeutil(groupName,groupPortrait,lastestGroupUser,lastGroupNumberName,lastGroupSendTime,lastestGroupMessage,
                            groupMessageCount,groupRole);
                    groupsLists.add(homeutil);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("发生错误");
            groupsLists.clear();
        }
    }
    class MyBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return groupsLists.size();
        }

        @Override
        public Object getItem(int i) {
            return groupsLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            return null;
        }
    }
}
