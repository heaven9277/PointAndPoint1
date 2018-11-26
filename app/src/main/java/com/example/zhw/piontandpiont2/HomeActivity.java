package com.example.zhw.piontandpiont2;

//三个首页界面

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.example.zhw.piontandpiont2.Util.BDLocationUtils;
import com.example.zhw.piontandpiont2.Util.BaseActivity;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.Jsonpack;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.db.QueryData;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener{
    public static String data;
    public static String user_name;
    public static String TAG;
    public String user_portrait;//头像
    public String user_h_name;//昵称

    private ArrayList<Fragment> fragments;
    //声明四个导航对应fragment
    public static ChatFragment chatFragment;
    public static MessageFragment messageFragment;
    public static UserFragment userFragment;
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

    public static MessageFragment.MyMessageBaseAdapter myMessageBaseAdapter;
    public static String TEST =  "";
    public static Context context;

    public static String isHomeActivity;
    public static ChatFragment.MyBaseAdapter myBaseAdapters;
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
                   System.out.println("收到首页数据。。。"+data);
                   ChatFragment.datalilst = Jsonpack.getLoginSuccessData(data);
                  // System.out.println("获得数据。。"+ChatFragment.datalilst);

                   //更新数据
                    MainActivity.user_portrait = Jsonpack.getUserPortrait(data);
                    System.out.println("头像。。。。。。。。。。。。。。。。："+MainActivity.user_portrait);
                    System.out.println("是吗？？？？？？？？？？"+isHomeActivity);
                    MainActivity.user_h_name = Jsonpack.getUserName(data);
                    MainActivity.sign = Jsonpack.getUserSign(data);
                    MainActivity.email = Jsonpack.getUserEmail(data);
                    MainActivity.phone = Jsonpack.getUserPhone(data);
                    //userFragment.siv_icon.setImageUrl(MainActivity.user_portrait,R.drawable.users);
                    //userFragment.tv_title.setText(MainActivity.user_h_name);
                    //userFragment.tv_author.setText(MainActivity.sign);
                    myBaseAdapters = ChatFragment.getMyBaseAdapter();
                    myBaseAdapters.notifyDataSetChanged();
                    System.out.println("提示更新。。");
                    break;
                case 8:
                    //获取失败
                    break;
                default:
                    break;
            }
        }
    };
    public static Handler message_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 22:
                    //推送失败
                    break;
                case 23:
                    System.out.println("接收到消息") ;
                    MessageFragment.datalilst = QueryData.getData(context);
                    myMessageBaseAdapter = MessageFragment.getMyBaseAdapter();
                    myMessageBaseAdapter.notifyDataSetChanged();
                    //推送成功
                    break;
            }
        }
    };
    public static Handler outlogin_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String data = (String) msg.obj;
            switch (what){
                case 2:
                    //推送失败
                    Toast.makeText(context, ParseJson.getJsonInfo(data),Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //推送成功
                    System.out.println("接收到消息") ;
                    Toast.makeText(context, ParseJson.getJsonInfo(data),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
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
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_message:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_user:
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
        DarkStatusBar.setDarkStatusIcon(this);
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
        user_name = MainActivity.main_username;
        TAG = intent.getStringExtra("TAG");
        user_portrait = MainActivity.user_portrait;
        user_h_name = MainActivity.user_h_name;
        System.out.println("结兽皮"+data+"接收到的信息"+user_name+"第一次登陆");
        context = this;
        initView();
        initListener();
        //发送数据给Fragment
        sendDataChatFragemtn(data);
    }
    private void initView() {
        DarkStatusBar.setDarkStatusIcon(this);
        BDLocationUtils location=new BDLocationUtils(HomeActivity.this);
        location.initMap();
        //在主布局中根据id找到ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //实例化所属三个fragment
        chatFragment = new ChatFragment();
        messageFragment = new MessageFragment();
        userFragment = new UserFragment();
        System.out.println("实例化完毕。。。。。。。。。。。。。。");
        fragments = new ArrayList<>();
        //添加fragments到集合中
        fragments.add(chatFragment);
        fragments.add(messageFragment);
        fragments.add(userFragment);
        fragmentManager = getSupportFragmentManager();

        myBaseAdapters= chatFragment.getMyBaseAdapter();
        //为ViewPager设置适配器用于部署fragments
        viewPager.setAdapter(new FragAdapter(fragmentManager,fragments));

        HomeActivity.isHomeActivity="Homeactivity";
        ChatActivity.isChatActivity="";
    }

    private void initListener() {
        menuItem1=navigation.getMenu().findItem(R.id.navigation_chat);
        menuItem2=navigation.getMenu().findItem(R.id.navigation_message);
        menuItem3=navigation.getMenu().findItem(R.id.navigation_user);

        //为viewpager页面触碰的监听，当点击除弹出按钮时，弹出按钮隐藏
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                relativeLayout.setVisibility(View.GONE);
                display = false;
                return false;
            }
        });
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
                relativeLayout.setVisibility(View.GONE);
                display = false;
            }
        });

    }
    public static void sendDataChatFragemtn(String data){
        // 发送给Fragment
            Bundle bundle = new Bundle();
            bundle.putString("data",data);
            bundle.putString("username",user_name);
            bundle.putString("TAG",TAG);
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
                    display = true;
                }else {

                    relativeLayout.setVisibility(View.GONE);
                    display = false;
                }
                break;
            case R.id.linear_search:
                //搜索群
                Intent searchActivity = new Intent(this,SearchActivity.class);
                startActivity(searchActivity);
                display = false;
                System.out.println("点击了搜索群");
                break;
            case R.id.liear_create:
                //创建群
                Intent createActivity = new Intent(this,CreateActivity.class);
                createActivity.putExtra("username",user_name);
                createActivity.putExtra("data",data);
                startActivity(createActivity);
                display = false;
                System.out.println("点击了create群");
                break;
            case R.id.viewpager:
                relativeLayout.setVisibility(View.GONE);
                display = false;
                System.out.println("viewpager");
                break;
            default:
                relativeLayout.setVisibility(View.GONE);
                display = false;
                System.out.println("defalt");
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

     /*   try {
            Thread.sleep(5000);
            //发送请求
            SendFisrtDataThread sendFisrtDataThread = new SendFisrtDataThread(user_name);
            sendFisrtDataThread.start();
            System.out.println("睡眠5秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        relativeLayout.setVisibility(View.GONE);
        display = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        HomeActivity.isHomeActivity="Homeactivity";
        ChatActivity.isChatActivity="";
        //发送请求
        SendFisrtDataThread sendFisrtDataThread = new SendFisrtDataThread(user_name);
        sendFisrtDataThread.start();

    }
    //得到一个handler
    public static Handler getFirst_handler(){
        return First_handler;
    }

}
