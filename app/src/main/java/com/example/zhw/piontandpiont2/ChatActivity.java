package com.example.zhw.piontandpiont2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll= findViewById(R.id.ll);
        btn=findViewById(R.id.bt_chat_add);
        btn.setOnClickListener(this);
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
                break;
            default :
                break;
        }

    }
}
