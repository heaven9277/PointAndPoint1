package com.example.zhw.piontandpiont2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Threadpack.SendMyProfileThread;
import com.example.zhw.piontandpiont2.Util.DarkStatusBar;
import com.example.zhw.piontandpiont2.Util.LogUtil;
import com.example.zhw.piontandpiont2.Util.PareJson;
import com.example.zhw.piontandpiont2.Util.Portrait;
import com.loopj.android.image.SmartImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private SmartImageView portrait;   //头像
    private ImageView back;
    private EditText name, description, phone, email;
    private TextView id;
    private ConstraintLayout line,line1;  //头像所在的一行,账号所在行
    private static String portraitPath = "/sdcard/PointAndPoint/.head/user/";  //头像路径
    private Button saveChanges;
    public static Bitmap head;//换头像Bitmap
    private Bitmap bt; //本地头像bitmap
    final File file = new File(Environment.getExternalStorageDirectory(),
            "head.jpg");
    private static Context context;

    @SuppressLint("HandlerLeak")
    private static Handler profile_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到保存个人资料反馈");
            int what = msg.what;
            String data = (String) msg.obj;
            System.out.println(data);
            String info = PareJson.getJsonInfo(data);
            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    void init() {
        back = findViewById(R.id.profile_image_back);
        line = findViewById(R.id.profile_line);
        line1 = findViewById(R.id.profile_line1);
        id = findViewById(R.id.profile_tv_id);
        portrait = findViewById(R.id.profile_portrait);
        name = findViewById(R.id.profile_edt_name);
        description = findViewById(R.id.profile_edt_descript);
        phone = findViewById(R.id.profile_edt_phone_number);
        email = findViewById(R.id.profile_edt_email);
        saveChanges = findViewById(R.id.profile_button_saveChanges);

        back.setOnClickListener(this);
        line.setOnClickListener(this);
        line1.setOnClickListener(this);
        portrait.setOnClickListener(this);
        saveChanges.setOnClickListener(this);
        DarkStatusBar.setDarkStatusIcon(this);
        context = ProfileActivity.this;

        name.setText(MainActivity.user_h_name);
        description.setText(MainActivity.sign);
        id.setText(MainActivity.main_username);
        phone.setText(MainActivity.phone);
        email.setText(MainActivity.email);
        portrait.setImageUrl(MainActivity.user_portrait,R.drawable.avator_0003);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image_back:  //返回
                finish();
                break;
            case R.id.profile_portrait: //显示换头像选择来源
                Portrait.showProfileDialog(this, this);
                break;
            case R.id.profile_line://显示换头像选择来源
                Portrait.showProfileDialog(this, this);
                break;
            case R.id.profile_line1:
                Toast.makeText(this,"账号不可修改",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile_button_saveChanges://保存修改按钮，在此实行对资料的保存，并把数据传给首页或后台
                if (name.getText().toString().equals(""))
                    Toast.makeText(this, "用户不能为空", Toast.LENGTH_SHORT).show();
                else {
//                    if (head != null)
//                        Portrait.setPicToView(head, portraitPath);//保存在SD卡中
                    uploadNewProfile(); //
                }
                break;
        }
    }

    //处理返回数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Portrait.onResult(this, this, 2, requestCode, resultCode, data);
        if (head != null)
            portrait.setImageBitmap(head);//用ImageView显示出来
        super.onActivityResult(requestCode, resultCode, data);
    }

    //上传服务器代码
    void uploadNewProfile() {
        byte[] datas;
        String portraitData = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (head != null) {//更换来的头像
            head.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            datas = baos.toByteArray();
            portraitData = Base64.encodeToString(datas, Base64.DEFAULT);//使用BASE64
        }
//         else if (bt != null) {//本地已存在的头像
////            bt.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////            datas = baos.toByteArray();
////            portraitData = Base64.encodeToString(datas, Base64.DEFAULT);//使用BASE64
////        }
//        else {//app自带头像
//            Drawable drawable = getDrawable(R.drawable.avator_0003);
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            bt = Objects.requireNonNull(bitmapDrawable).getBitmap();
//            datas = baos.toByteArray();
//            portraitData = Base64.encodeToString(datas, Base64.DEFAULT);//使用BASE64
//        }
//        byte[] data2 = Base64.decode(portraitData, Base64.DEFAULT);
        LogUtil.e("个人头像字符串", portraitData);
        String name = this.name.getText().toString();
        String desc = this.description.getText().toString();
        String phone = this.phone.getText().toString();
        String email = this.email.getText().toString();

        SendMyProfileThread sendMyProfileThread = new SendMyProfileThread(MainActivity.main_username, portraitData, name, desc, phone, email);
        sendMyProfileThread.start();
    }

    public static Handler getHandler() {
        return profile_handler;
    }

}
