package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zhw.piontandpiont2.Util.BaseActivity;

public class ApplicationActivity extends BaseActivity implements View.OnClickListener {
    public ImageView message_image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        initView();
    }

    private void initView() {
            message_image_back = findViewById(R.id.image_message_back);
            message_image_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_message_back:  //返回键
                        Intent homeActivity = new Intent(ApplicationActivity.this, HomeActivity.class);
                        homeActivity.putExtra("id",2);
                       startActivity(homeActivity);
                break;
        }
    }
}
