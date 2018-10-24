package com.example.zhw.piontandpiont2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

public class HomeActivity extends AppCompatActivity {

    // private TextView mTextMessage;

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

        //  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
