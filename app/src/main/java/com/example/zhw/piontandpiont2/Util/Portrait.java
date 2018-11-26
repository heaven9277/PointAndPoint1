package com.example.zhw.piontandpiont2.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.zhw.piontandpiont2.EditGroupActivity;
import com.example.zhw.piontandpiont2.ProfileActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_SHORT;

public class Portrait {
//    static AlertDialog alertEditGroupInfoDialog, alertEditProfileDialog;
    private static File file2 = new File(Environment.getExternalStorageDirectory(),
            "PointAndPoint/.head/user/");
    private static File file1 = new File(Environment.getExternalStorageDirectory(),
            "PointAndPoint/.head/group/");

    //显示换头像dialog
//    public static void showGroupInfoDialog(final Context context, final Activity activity) {
//        if (!file1.exists())
//            file1.mkdirs();
//        final File file = new File(file1, "catch.png");
//        if (alertEditGroupInfoDialog == null) {
//            //设置按钮图标
////            Resources resources = this.getResources();
////            Drawable gallery = resources.getDrawable(R.drawable.gallery);
////            Drawable camera = resources.getDrawable(R.drawable.camera);
//            alertEditGroupInfoDialog = new AlertDialog.Builder(context.getApplicationContext())
//                    .setMessage("选择头像来源")
//                    .setPositiveButton("图库", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            alertEditGroupInfoDialog.cancel();
//                            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                            activity.startActivityForResult(intent1, 1);
//                        }
//                    })
//                    //.setPositiveButtonIcon(camera)
//                    .setNegativeButton("相机", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Uri ImageUri;
//                            //关闭页面或者做其他操作
//                            alertEditGroupInfoDialog.cancel();
//                            if (Build.VERSION.SDK_INT >= 24) {
//                                //7.0及以上
//                                ImageUri = FileProvider.getUriForFile(context, "com.example.zhw.piontandpiont2.FileProvider", file);
//                            } else {
//                                ImageUri = Uri.fromFile(file); //7.0以下
//                            }
//                            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            intent2.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
//                            activity.startActivityForResult(intent2, 2);//采用ForResult打开
//                            System.out.println("启用相机！！！！");
//                        }
//                    })
//                    //.setNegativeButtonIcon(gallery)
//                    .create();
//        }
//        alertEditGroupInfoDialog.show();
//    }

//    public static void showProfileDialog(final Context context, final Activity activity) {
//        if (!file2.exists())
//            file2.mkdirs();
//        final File file = new File(file2,"catch.png");
//        if (alertEditProfileDialog == null) {
//            //设置按钮图标
////            Resources resources = this.getResources();
////            Drawable gallery = resources.getDrawable(R.drawable.gallery);
////            Drawable camera = resources.getDrawable(R.drawable.camera);
//            alertEditProfileDialog = new AlertDialog.Builder(context.getApplicationContext())
//                    .setMessage("选择头像来源")
//                    .setPositiveButton("图库", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            alertEditProfileDialog.cancel();
//                            Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                            activity.startActivityForResult(intent1, 1);
//                        }
//                    })
//                    //.setPositiveButtonIcon(camera)
//                    .setNegativeButton("相机", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Uri ImageUri;
//                            //关闭页面或者做其他操作
//                            alertEditProfileDialog.cancel();
//                            if (Build.VERSION.SDK_INT >= 24) {//7.0及以上
//                                ImageUri = FileProvider.getUriForFile(context, "com.example.zhw.piontandpiont2.FileProvider", file);
//                            } else {//7.0以下
//                                ImageUri = Uri.fromFile(file);
//                            }
//                            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            intent2.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
//                            activity.startActivityForResult(intent2, 2);//采用ForResult打开
//                            System.out.println("启用相机！！！！");
//                        }
//                    })
//                    //.setNegativeButtonIcon(gallery)
//                    .create();
//        }
//        alertEditProfileDialog.show();
//    }

    //调用系统的裁剪
    public static void cropPhoto(Uri uri, Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件，7.0及以上
        }
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("return-data", true);
        System.out.println("剪图片!!!!!!!!!!!");
        activity.startActivityForResult(intent, 3);
        System.out.println("启用剪图片!!!!!!!!!!!");
    }

    public static void onResult(Context context, Activity activity, int activityCode, int requestCode, int resultCode, Intent data) {
        Uri imageUri;

        switch (requestCode) {
            case 1: //图库回调
                if (resultCode == RESULT_OK)
                    Portrait.cropPhoto(data.getData(), activity);//裁剪图片
                else
                    Toast.makeText(context, "取消修改头像", LENGTH_SHORT).show();

                break;
            case 2: //相机回调
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 24) { //7.0以上
                        if (activityCode == 1)
                            imageUri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.zhw.piontandpiont2.FileProvider", new File(file1,"catch.png"));//7.0
                        else
                            imageUri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.zhw.piontandpiont2.FileProvider", new File(file2,"catch.png"));//7.0

                        System.out.println("24242424242");
                    } else {//7.0以下
                        if (activityCode == 1)
                            imageUri = Uri.fromFile(new File(file1,"catch.png"));
                        else
                            imageUri = Uri.fromFile(new File(file2,"catch.png"));
                        System.out.println("小于24242424242");
                    }

                    cropPhoto(imageUri, activity);//裁剪图片
                } else
                    Toast.makeText(context, "取消修改头像", LENGTH_SHORT).show();

                break;
            case 3://裁剪回调
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null)
                        Toast.makeText(context, "取消修改头像", LENGTH_SHORT).show();
                    else {
                        if (activityCode == 1)
                            EditGroupActivity.head = extras.getParcelable("data");
                        else if (activityCode == 2)
                            ProfileActivity.head = extras.getParcelable("data");
                    }
                }
                break;
            default:
                break;
        }
    }

    //保存裁剪后的图片
    public static void setPicToView(Bitmap mBitmap, String portraitPath) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file1 = new File(portraitPath);
        file1.mkdirs();// 创建文件夹
        String fileName = portraitPath + "head.jpg";//图片名字
        try {
            System.out.println("实例化流之前");
            b = new FileOutputStream(fileName);
            System.out.println("实例化流之后");
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            System.out.println("写入文件");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
