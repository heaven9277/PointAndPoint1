package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/*
    管理群的Activity
 */

public class ManageGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public ListView group_user_lsit;
    public ImageView image_back;//返回键
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);
        initViews();
    }

    private void initViews() {
        group_user_lsit = findViewById(R.id.group_user_lsit);
        image_back = findViewById(R.id.image_back);
        group_user_lsit.setAdapter(new MyManageGroupBaseAdapter());
        image_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_back:  //返回键
                Intent groupInfoActivity = new Intent(this,GroupInfoActivity.class);
                startActivity(groupInfoActivity);
                break;
        }
    }

    class MyManageGroupBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view_managerGroup = LayoutInflater.from(ManageGroupActivity.this).inflate(R.layout.manager_user_item,null);

            return view_managerGroup;
        }
    }
}
