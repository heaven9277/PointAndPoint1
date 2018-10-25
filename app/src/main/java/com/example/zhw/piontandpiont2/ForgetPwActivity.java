package com.example.zhw.piontandpiont2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPwActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_vify,btn_commitVify;
    public EditText et_usertelphone,et_telVify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw);
        init();
    }
    public void init(){
        btn_vify = findViewById(R.id.btn_vify);
        btn_commitVify = findViewById(R.id.btn_commitVify);
        et_telVify = findViewById(R.id.et_telVify);
        et_usertelphone = findViewById(R.id.et_usertelphone);
        //绑定点击事件
        btn_vify.setOnClickListener(this);
        btn_commitVify.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_vify:
                break;
            case R.id.btn_commitVify:
                break;
        }
    }
}
