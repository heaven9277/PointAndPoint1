package com.example.zhw.piontandpiont2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.ProfileActivity;
import com.example.zhw.piontandpiont2.R;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;


public class UserFragment extends Fragment implements View.OnClickListener {
    public SmartImageView siv_icon;//头像
    public TextView tv_title;//昵称
    public TextView tv_author;//个性签名
    public LinearLayout lin_self_data;//我的资料
    public LinearLayout ll_photos;//相册
    public LinearLayout about_our;//关于我们

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container, false);

        initView(view);

        return view;
    }
    private void initView(View view) {
        siv_icon = view.findViewById(R.id.siv_icon);
        tv_title = view.findViewById(R.id.tv_title);
        tv_author = view.findViewById(R.id.tv_author);
        lin_self_data = view.findViewById(R.id.lin_self_data);
        ll_photos = view.findViewById(R.id.ll_photos);
        about_our = view.findViewById(R.id.about_our);

        lin_self_data.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_self_data:        //我的资料
                Intent proflieActivity = new Intent(getContext(), ProfileActivity.class);
                startActivity(proflieActivity);
                break;
        }
    }
}
