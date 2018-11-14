package com.example.zhw.piontandpiont2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhw.piontandpiont2.Util.BaseActivity;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;

public class ApplicationActivity extends BaseActivity implements View.OnClickListener {
    public ImageView message_image_back;
    private Button btn_accept,btn_decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        initView();
    }

    private void initView() {
        DarkStatusBar.setDarkStatusIcon(this);
        message_image_back = findViewById(R.id.image_message_back);
        message_image_back.setOnClickListener(this);
        btn_accept=findViewById(R.id.application_button_accept);
        btn_accept.setOnClickListener(this);
        btn_decline=findViewById(R.id.application_decline);
        btn_decline.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_message_back:  //返回键
                // Intent homeActivity = new Intent(ApplicationActivity.this, HomeActivity.class);
                // homeActivity.putExtra("id",2);
                //  startActivity(homeActivity);
                this.finish();
                break;
            case R.id.application_button_accept:  //接受
                btn_accept.setText("踢出群");
                btn_decline.setVisibility(View.GONE);
                break;
            case R.id.application_decline:   //拒绝
                btn_accept.setText("邀请进群");
                btn_decline.setVisibility(View.GONE);
                break;
        }
    }
}
