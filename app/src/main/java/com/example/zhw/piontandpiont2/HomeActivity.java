package com.example.zhw.piontandpiont2;

//三个首页界面
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Adapter.FragAdapter;
import com.example.zhw.piontandpiont2.Fragment.ChatFragment;
import com.example.zhw.piontandpiont2.Fragment.MessageFragment;
import com.example.zhw.piontandpiont2.Fragment.UserFragment;
import com.example.zhw.piontandpiont2.Threadpack.SendFisrtDataThread;
import com.example.zhw.piontandpiont2.Util.BaseActivity;
import com.example.zhw.piontandpiont2.Util.DataBean;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.example.zhw.piontandpiont2.Util.LoginSuccessData;
import com.example.zhw.piontandpiont2.Util.PareJson;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public String data;
    public static String user_name;
    private ArrayList<Fragment> fragments;
    //声明四个导航对应fragment
    public static ChatFragment chatFragment;
    MessageFragment messageFragment;
    UserFragment userFragment;
    //声明ViewPager
    private ViewPager viewPager;
    FragmentManager fragmentManager;//声明fragment管理
    MenuItem menuItem1,menuItem2,menuItem3;
    BottomNavigationView navigation;
    //声明组件
    Button btn_add;
    LinearLayout group_search,group_creat;
    RelativeLayout relativeLayout;

    boolean display=false;

    //定义一个handler进行消息接收
    private static Handler First_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到首页信息");
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 7:
                    //获取成功
                    sendDataChatFragemtn(data);
                    //提示更新listview
                   // chatFragment.getMyBaseAdapter().notifyDataSetChanged();
                    break;
                case 8:
                    //获取失败
                    break;
                default:
                    break;
            }
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //  menuItem=item;
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    // mTextMessage.setText("");
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_message:
                    //  mTextMessage.setText("");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_user:
                    //  mTextMessage.setText("");
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        relativeLayout = findViewById(R.id.relativeLayout);
        btn_add = findViewById(R.id.home_add);
        group_search = findViewById(R.id.linear_search);
        group_creat = findViewById(R.id.liear_create);
        btn_add.setOnClickListener(this);
        group_creat.setOnClickListener(this);
        group_search.setOnClickListener(this);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //从登陆界面得到数据
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        user_name = intent.getStringExtra("username");
        System.out.println(data+"接收到的信息"+user_name);

        initView();
        initListener();
        //发送数据给Fragment
        sendDataChatFragemtn(data);

        //发送请求
        SendFisrtDataThread sendFisrtDataThread = new SendFisrtDataThread(user_name);
        sendFisrtDataThread.start();
    }
    private void initView() {
        //在主布局中根据id找到ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //实例化所属三个fragment
        chatFragment = new ChatFragment();
        messageFragment = new MessageFragment();
        userFragment = new UserFragment();

        fragments = new ArrayList<>();
        //添加fragments到集合中
        fragments.add(chatFragment);
        fragments.add(messageFragment);
        fragments.add(userFragment);
        fragmentManager = getSupportFragmentManager();
        //为ViewPager设置适配器用于部署fragments
        viewPager.setAdapter(new FragAdapter(fragmentManager,fragments));
    }

    private void initListener() {
        menuItem1=navigation.getMenu().findItem(R.id.navigation_chat);
        menuItem2=navigation.getMenu().findItem(R.id.navigation_message);
        menuItem3=navigation.getMenu().findItem(R.id.navigation_user);

        //为viewpager添加页面变化的监听以及事件处理
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据位置直接决定显示哪个fragment
                viewPager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        menuItem1.setChecked(true);
                        break;
                    case 1:
                        menuItem2.setChecked(true);
                        break;
                    case 2:
                        menuItem3.setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public static void sendDataChatFragemtn(String data){
        // 发送给Fragment
            Bundle bundle = new Bundle();
            bundle.putString("data",data);
            bundle.putString("username",user_name);
            chatFragment.setArguments(bundle);

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.home_add:
                //点击加号显示
                if(display==false) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.setEnabled(true);
                    display = true;
                }else {

                    relativeLayout.setVisibility(View.INVISIBLE);
                    relativeLayout.setEnabled(false);
                    display = false;
                }
                break;
            case R.id.linear_search:
                //搜索群
                Intent SearchActivity = new Intent(this,SearchActivity.class);
                startActivity(SearchActivity);
                System.out.println("点击了搜索群");
                break;
            case R.id.liear_create:
                //创建群
                Intent createActivity = new Intent(this,CreateActivity.class);
                startActivity(createActivity);
                System.out.println("点击了create群");
                break;
            default:
                relativeLayout.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int id=getIntent().getIntExtra("id", 0);
        System.out.println(id);
        if(id==2){
            viewPager.setCurrentItem(1);  //view2是viewPager中的第二个view，因此设置setCurrentItem（1）。
        }
    }
    //得到一个handler
    public static Handler getFirst_handler(){
        return First_handler;
    }
}
