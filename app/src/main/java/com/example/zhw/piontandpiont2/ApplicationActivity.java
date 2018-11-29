package com.example.zhw.piontandpiont2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.Fragment.MessageFragment;
import com.example.zhw.piontandpiont2.Threadpack.SendAcceptUser;
import com.example.zhw.piontandpiont2.Util.BaseActivity;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.db.QueryData;
import com.loopj.android.image.SmartImageView;

public class ApplicationActivity extends BaseActivity implements View.OnClickListener {
    public ImageView message_image_back;
    private Button btn_accept,btn_decline;
    public SmartImageView application_portrait;
    public TextView application_applicant_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        initView();
    }

    private void initView() {
        DarkStatusBar.setDarkStatusIcon(this);
        application_portrait = findViewById(R.id.application_portrait);
        application_applicant_name = findViewById(R.id.application_applicant_name);
        message_image_back = findViewById(R.id.image_message_back);
        message_image_back.setOnClickListener(this);
        btn_accept=findViewById(R.id.application_button_accept);
        btn_accept.setOnClickListener(this);
        btn_decline=findViewById(R.id.application_decline);
        btn_decline.setOnClickListener(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("acceptName");
        application_applicant_name.setText(name);
        application_portrait.setImageUrl(MessageFragment.request_pro,R.drawable.loading);
        System.out.println("请求者的头像"+MessageFragment.request_pro);
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_message_back:  //返回键
                this.finish();
                break;
            case R.id.application_button_accept:  //接受
                    SendAcceptUser sendAcceptUser = new SendAcceptUser(1,MessageFragment.request,MessageFragment.uuid,MessageFragment.groupId,MessageFragment.noticeId);
                    sendAcceptUser.start();
                QueryData.updateAccept(HomeActivity.context,MessageFragment.noticeId+"");
                btn_decline.setVisibility(View.GONE);
                break;
            case R.id.application_decline:   //拒绝
                SendAcceptUser sendAcceptUser1 = new SendAcceptUser(2,MessageFragment.request,MessageFragment.uuid,MessageFragment.groupId,MessageFragment.noticeId);
                sendAcceptUser1.start();
                btn_accept.setText("邀请进群");
                btn_decline.setVisibility(View.GONE);
                break;
        }
    }
}
