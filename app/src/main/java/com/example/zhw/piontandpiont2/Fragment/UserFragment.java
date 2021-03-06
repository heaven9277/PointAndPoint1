package com.example.zhw.piontandpiont2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhw.piontandpiont2.AboutUs;
import com.example.zhw.piontandpiont2.HomeActivity;
import com.example.zhw.piontandpiont2.MainActivity;
import com.example.zhw.piontandpiont2.ProfileActivity;
import com.example.zhw.piontandpiont2.R;
import com.example.zhw.piontandpiont2.Threadpack.SendOutLoginThread;
import com.loopj.android.image.SmartImageView;
import com.yanzhenjie.album.Album;


public class UserFragment extends Fragment implements View.OnClickListener {
    public static SmartImageView siv_icon;//头像
    public static TextView tv_title;//昵称
    public static TextView tv_author;//个性签名
    public  LinearLayout lin_self_data;//我的资料
    public  LinearLayout ll_photos;//相册
    public  LinearLayout about_our;//关于我们
    public  Button out_login;//退出登陆
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container, false);
        initView(view);
        return view;
    }

    public void  initView(View view) {
        siv_icon = view.findViewById(R.id.siv_icon);
        tv_title = view.findViewById(R.id.tv_title);
        tv_author = view.findViewById(R.id.tv_author);
        lin_self_data = view.findViewById(R.id.lin_self_data);
        ll_photos = view.findViewById(R.id.ll_photos);
        about_our = view.findViewById(R.id.about_our);
        out_login = view.findViewById(R.id.out_login);
        lin_self_data.setOnClickListener(this);
        siv_icon.setImageUrl(MainActivity.user_portrait,R.drawable.avator_0003);
        tv_title.setText(MainActivity.user_h_name);
        tv_author.setText(MainActivity.sign);
        out_login.setOnClickListener(this);
        ll_photos.setOnClickListener(this);
        about_our.setOnClickListener(this);
        System.out.println("进入这里??????????????????????????????????????"+ HomeActivity.isHomeActivity);
        System.out.println("?????"+ MainActivity.user_portrait);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_self_data:        //我的资料
                Intent proflieActivity = new Intent(getContext(), ProfileActivity.class);
                startActivity(proflieActivity);
                break;
            case R.id.out_login:
                //退出登陆
                SendOutLoginThread sendOutLoginThread = new SendOutLoginThread(HomeActivity.user_name);
                sendOutLoginThread.start();
                System.out.println("发送退出登陆");
                break;
            case R.id.about_our:       //关于我们
                Intent useActivity = new Intent(getContext(), AboutUs.class);
                startActivity(useActivity);
                break;
            case R.id.ll_photos:
                Album.startAlbum(this, 0, 9);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        siv_icon.setImageUrl(MainActivity.user_portrait,R.drawable.avator_0003);
        System.out.println("?????头像?"+MainActivity.user_portrait);
    }
    public static  void updateView(){
        siv_icon.setImageUrl(MainActivity.user_portrait,R.drawable.avator_0003);
        tv_title.setText(MainActivity.user_h_name);
        tv_author.setText(MainActivity.sign);
        System.out.println("?????头像?"+MainActivity.user_portrait+siv_icon);
    }
}













