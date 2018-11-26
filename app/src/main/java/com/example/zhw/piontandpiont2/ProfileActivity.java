package com.example.zhw.piontandpiont2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
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
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.Util.Portrait;
import com.loopj.android.image.SmartImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private SmartImageView portrait;   //头像
    private ImageView back;
    private EditText name, description, phone, email;
    private TextView id;
    private ConstraintLayout line,line1;  //头像所在的一行,账号所在行
//    private static String portraitPath = "/sdcard/PointAndPoint/.head/user/";  //头像路径
    private File file2 = new File(Environment.getExternalStorageDirectory(),
            "PointAndPoint/.head/user/");
    private Button saveChanges;
    public static Bitmap head;//换头像Bitmap
    private Bitmap bt; //本地头像bitmap
    final File file = new File(Environment.getExternalStorageDirectory(),
            "head.jpg");
    private static Context context;
    private AlertDialog alertEditProfileDialog;

    @SuppressLint("HandlerLeak")
    private static Handler profile_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到保存个人资料反馈");
            int what = msg.what;
            String data = (String) msg.obj;
            System.out.println(data);
            String info = ParseJson.getJsonInfo(data);
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
        HomeActivity.isHomeActivity="";
        ChatActivity.isChatActivity ="";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image_back:  //返回
                finish();
                break;
            case R.id.profile_portrait: //显示换头像选择来源
//                Portrait.showProfileDialog(this, this);
                initPermission();
                showProfileDialog();
                break;
            case R.id.profile_line://显示换头像选择来源
//                Portrait.showProfileDialog(this, this);
                initPermission();
                showProfileDialog();
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

    public void showProfileDialog() {
        if (!file2.exists())
            file2.mkdirs();
        final File file = new File(file2,"catch.png");
        if (alertEditProfileDialog == null) {
            //设置按钮图标
//            Resources resources = this.getResources();
//            Drawable gallery = resources.getDrawable(R.drawable.gallery);
//            Drawable camera = resources.getDrawable(R.drawable.camera);
            alertEditProfileDialog = new AlertDialog.Builder(this)
                    .setMessage("选择头像来源")
                    .setPositiveButton("图库", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertEditProfileDialog.cancel();
                            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent1, 1);
                        }
                    })
                    //.setPositiveButtonIcon(camera)
                    .setNegativeButton("相机", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri ImageUri;
                            //关闭页面或者做其他操作
                            alertEditProfileDialog.cancel();
                            if (Build.VERSION.SDK_INT >= 24) {//7.0及以上
                                ImageUri = FileProvider.getUriForFile(context, "com.example.zhw.piontandpiont2.FileProvider", file);
                            } else {//7.0以下
                                ImageUri = Uri.fromFile(file);
                            }
                            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent2.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
                            startActivityForResult(intent2, 2);//采用ForResult打开
                            System.out.println("启用相机！！！！");
                        }
                    })
                    //.setNegativeButtonIcon(gallery)
                    .create();
        }
        alertEditProfileDialog.show();
    }

    //处理返回数据
    @Override
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

    //权限数组
    String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求

    //权限判断和申请
    private void initPermission() {
        mPermissionList.clear();//清空没有通过的权限
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                Toast.makeText(this, "权限已被拒绝，请到系统权限设置授权", LENGTH_SHORT).show();
//                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            } else {
                //全部权限通过，可以进行下一步操作。。。
            }
        }
    }

    public static Handler getHandler() {
        return profile_handler;
    }

}
