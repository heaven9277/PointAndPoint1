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
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Threadpack.SendEditGroupThread;
import com.example.zhw.piontandpiont2.Util.LogUtil;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.Util.Portrait;
import com.loopj.android.image.SmartImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/*
编辑群聊Activity
 */
public class EditGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView image_back;//返回键
    public EditText group_name;//群聊名
    public EditText group_desc;//群公告
    public SmartImageView gourp_pro;//群头像
    public Button btn_saveEdit;//保存修改
    public String group_pro;//头像
    public String groupName;
    public String groupDesc;//群公告
    public static String groupId;
    public static Context context;
    public static String uuid;//用户名
    public static String groupRole;//角色
    private Bitmap bt;
    public static Bitmap head;//换头像Bitmap
    private AlertDialog alertEditGroupInfoDialog;
//    private static String portraitPath = "/sdcard/PointAndPoint/.head/group/";  //头像路径
    private File file1 = new File(Environment.getExternalStorageDirectory(),
            "PointAndPoint/.head/group/");

    @SuppressLint("HandlerLeak")
    private static Handler EditGroupInfoHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("接收到群资料？？？？？？？？？？？？？？信息");
            int what = msg.what;
            String data = (String) msg.obj;
            System.out.println(data);
            switch (what){
                case 17:
                    //成功
                    Toast.makeText(context, ParseJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
                    Intent groupInfo = new Intent(context,GroupInfoActivity.class);
                    groupInfo.putExtra("groupId",groupId);
                    groupInfo.putExtra("uuid",uuid);
                    groupInfo.putExtra("groupRole",groupRole);
                    context.startActivity(groupInfo);
                    break;
                case 18:
                    //获取失败
                    Toast.makeText(context, ParseJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
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
        gourp_pro = findViewById(R.id.gourp_pro);
        btn_saveEdit = findViewById(R.id.btn_saveEdit);
        btn_saveEdit.setOnClickListener(this);
        image_back.setOnClickListener(this);
        gourp_pro.setOnClickListener(this);
        //得到上一个activity的数据
        Intent intent = getIntent();
        group_pro = intent.getStringExtra("group_image");
        groupName = intent.getStringExtra("group_name");
        groupDesc = intent.getStringExtra("group_desc");
        groupId = intent.getStringExtra("groupId");
        uuid = intent.getStringExtra("uuid");
        groupRole = intent.getStringExtra("groupRole");


        group_name.setText(groupName);
        group_desc.setText(groupDesc);
        context =this;

//        bt = BitmapFactory.decodeFile(portraitPath + "head.jpg");//从Sd中找头像，转换成Bitmap
//        if (bt != null) {
//            @SuppressWarnings("deprecation")
//            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
//            gourp_pro.setImageDrawable(drawable);
//        } else {
            //如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中

            gourp_pro.setImageUrl(group_pro,R.drawable.group003);
//        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_back:  ///返回键
                Intent groupInfoActivity = new Intent(this,GroupInfoActivity.class);
                groupInfoActivity.putExtra("groupId",groupId);
                groupInfoActivity.putExtra("uuid",uuid);
                groupInfoActivity.putExtra("groupRole",groupRole);
                startActivity(groupInfoActivity);
                finish();
                break;
            case R.id.btn_saveEdit: //保存修改
                if (group_name.getText().toString().trim() != null && group_desc.getText().toString().trim() !=null){
                    System.out.println(group_name.getText().toString().trim()+"???"+group_desc.getText().toString().trim());
                    upLoad();//上传资料
//                    if(head!=null)
//                        Portrait.setPicToView(head,portraitPath);//保存图片
//                    Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this,"信息不能为空",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.gourp_pro :
                //弹出换头像选择框
//                Portrait.showGroupInfoDialog(this,this);
                initPermission();
                showGroupInfoDialog();
                break;
        }
    }

    //显示换头像dialog
    public void showGroupInfoDialog() {
        if (!file1.exists())
            file1.mkdirs();
        final File file = new File(file1, "catch.png");
        if (alertEditGroupInfoDialog == null) {
            //设置按钮图标
//            Resources resources = this.getResources();
//            Drawable gallery = resources.getDrawable(R.drawable.gallery);
//            Drawable camera = resources.getDrawable(R.drawable.camera);
            alertEditGroupInfoDialog = new AlertDialog.Builder(this)
                    .setMessage("选择头像来源")
                    .setPositiveButton("图库", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertEditGroupInfoDialog.cancel();
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
                            alertEditGroupInfoDialog.cancel();
                            if (Build.VERSION.SDK_INT >= 24) {
                                //7.0及以上
                                ImageUri = FileProvider.getUriForFile(context, "com.example.zhw.piontandpiont2.FileProvider", file);
                            } else {
                                ImageUri = Uri.fromFile(file); //7.0以下
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
        alertEditGroupInfoDialog.show();
    }


    //处理返回数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Portrait.onResult(this,this,1,requestCode,resultCode,data);
        if (head != null)
            gourp_pro.setImageBitmap(head);//用ImageView显示出来
        super.onActivityResult(requestCode, resultCode, data);
    }

    //上传资料
    void upLoad(){
        byte[] datas;
        String portraitData = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (head != null) {//更换来的头像
            head.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            datas = baos.toByteArray();
            portraitData = Base64.encodeToString(datas, Base64.DEFAULT);//使用BASE64
        }
//        } else if (bt != null) {//本地已存在的头像
//            bt.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            datas = baos.toByteArray();
//            portraitData = Base64.encodeToString(datas, Base64.DEFAULT);//使用BASE64
//        }else {//app自带头像
//            Drawable drawable = getDrawable(R.drawable.avator_0003);
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            bt = Objects.requireNonNull(bitmapDrawable).getBitmap();
//            datas = baos.toByteArray();
//            portraitData = Base64.encodeToString(datas, Base64.DEFAULT);//使用BASE64
//        }
//        byte[] data2 = Base64.decode(portraitData, Base64.DEFAULT);
        LogUtil.e("群头像字符串", portraitData);
        SendEditGroupThread sendEditGroupThread = new SendEditGroupThread(group_name.getText().toString().trim(),groupId,group_desc.getText().toString().trim(),portraitData);
        sendEditGroupThread.start();
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


    public static Handler getEditGroupInfoHandler(){
        return EditGroupInfoHandler;
    }
}
