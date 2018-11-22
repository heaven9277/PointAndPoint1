package com.example.zhw.piontandpiont2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.Threadpack.SendEditGroupThread;
import com.example.zhw.piontandpiont2.Util.LogUtil;
import com.example.zhw.piontandpiont2.Util.PareJson;
import com.example.zhw.piontandpiont2.Util.Portrait;
import com.loopj.android.image.SmartImageView;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

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
    private static String portraitPath = "/sdcard/PointAndPoint/.head/group/";  //头像路径

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
                    Toast.makeText(context, PareJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
                    Intent groupInfo = new Intent(context,GroupInfoActivity.class);
                    groupInfo.putExtra("groupId",groupId);
                    groupInfo.putExtra("uuid",uuid);
                    groupInfo.putExtra("groupRole",groupRole);
                    context.startActivity(groupInfo);
                    break;
                case 18:
                    //获取失败
                    Toast.makeText(context,PareJson.getJsonInfo(data),Toast.LENGTH_LONG).show();
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

        bt = BitmapFactory.decodeFile(portraitPath + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            gourp_pro.setImageDrawable(drawable);
        } else {
            //如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中

            gourp_pro.setImageUrl(group_pro,R.drawable.group003);
        }
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
                Portrait.showGroupInfoDialog(this,this);
                break;
        }
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

    public static Handler getEditGroupInfoHandler(){
        return EditGroupInfoHandler;
    }
}
