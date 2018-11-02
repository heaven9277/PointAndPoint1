package com.example.zhw.piontandpiont2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
编辑群聊Activity
 */
public class EditGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView image_back;//返回键
    public EditText group_name;//群聊名
    public EditText group_desc;//群公告
    public Button btn_saveEdit;//保存修改
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        initView();
    }

    private void initView() {
        image_back = findViewById(R.id.image_back);
        group_name = findViewById(R.id.group_name);
        group_desc = findViewById(R.id.group_desc);
        btn_saveEdit = findViewById(R.id.btn_saveEdit);
        btn_saveEdit.setOnClickListener(this);
        image_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_back:  ///返回键
                break;
            case R.id.btn_saveEdit: //保存修改
                break;
        }
    }
}
