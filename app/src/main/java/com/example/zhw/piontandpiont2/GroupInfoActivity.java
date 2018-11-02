package com.example.zhw.piontandpiont2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
/*
群资料的Activity
 */
public class GroupInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public Button edit_group,out_group;//编辑群聊和退出群聊
    public ImageView image_back;//返回
    public SmartImageView group_image;//群聊头像
    public TextView group_name;//群名字
    public TextView group_id;//群id
    public TextView group_desc;//群公告
    public TextView manager_group;//群管理
    public TextView clear_group_message;//清空群消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
       //初始化组件
        initView();
    }

    private void initView() {
        edit_group = findViewById(R.id.edit_group);
        out_group = findViewById(R.id.out_group);
        image_back = findViewById(R.id.image_back);
        group_image = findViewById(R.id.group_image);
        group_name = findViewById(R.id.group_name);
        group_id = findViewById(R.id.group_id);
        group_desc = findViewById(R.id.group_desc);
        manager_group = findViewById(R.id.manager_group);
        clear_group_message = findViewById(R.id.clear_group_message);

        //绑定监听器
        edit_group.setOnClickListener(this);
        out_group.setOnClickListener(this);
        image_back.setOnClickListener(this);
        manager_group.setOnClickListener(this);
        clear_group_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.edit_group: //编辑群资料
                break;
            case R.id.out_group:  //退出群聊
                break;
            case R.id.image_back: //群头像
                break;
            case R.id.manager_group: //管理群
                break;
            case  R.id.clear_group_message: //清空群资料
                break;
            default:
                break;
        }
    }
}
