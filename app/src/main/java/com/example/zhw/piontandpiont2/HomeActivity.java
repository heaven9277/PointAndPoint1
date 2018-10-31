package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.example.zhw.piontandpiont2.Adapter.FragAdapter;
import com.example.zhw.piontandpiont2.Fragment.ChatFragment;
import com.example.zhw.piontandpiont2.Fragment.MessageFragment;
import com.example.zhw.piontandpiont2.Fragment.UserFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    public String data;
    private ArrayList<Fragment> fragments;
    //声明四个导航对应fragment
    ChatFragment chatFragment;
    MessageFragment messageFragment;
    UserFragment userFragment;
    //声明ViewPager
    private ViewPager viewPager;
    FragmentManager fragmentManager;//声明fragment管理
    MenuItem menuItem1,menuItem2,menuItem3;
    BottomNavigationView navigation;


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

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //从登陆界面得到数据
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        System.out.println(data+"接收到的信息");

        initView();
        initListener();
        sendDataChatFragemtn(data);
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
    public void sendDataChatFragemtn(String data){
        Bundle bundle = new Bundle();
        bundle.putString("data",data);
        chatFragment.setArguments(bundle);
    }

}
