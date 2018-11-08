package com.example.zhw.piontandpiont2.MessagePack;


import com.example.zhw.piontandpiont2.CreateActivity;
import com.example.zhw.piontandpiont2.HomeActivity;
import com.example.zhw.piontandpiont2.MainActivity;
import com.example.zhw.piontandpiont2.Threadpack.CreateGroupActivityThread;
import com.example.zhw.piontandpiont2.Threadpack.FirstActiviityThread;
import com.example.zhw.piontandpiont2.Threadpack.MainThread;

//消息分发的类
public class MessageNotication {
    public static void sendInfoToActivity(String text,int id){
        switch (id){
            case 0:
                break;
            case 1:
                //登陆跳转操作

                MainThread mainThread = new MainThread(text, MainActivity.getHandler());
                mainThread.start();
                System.out.println("开始线程");
                break;
            case 2:
                //首页操作
                FirstActiviityThread firstActiviityThread = new FirstActiviityThread(text, HomeActivity.getFirst_handler());
                firstActiviityThread.start();
                System.out.println("开始首页线程");
                break;
            case 3:
                //创建群操作
                CreateGroupActivityThread createGroupActivityThread = new CreateGroupActivityThread(text, CreateActivity.getCreateGroupHandler());
                createGroupActivityThread.start();
                System.out.println("开始创建群的线程");
                break;
            case 4:
                //进入群聊
                break;
            case 5:
                //群资料
                break;
            case 6:
                //退出群聊
                break;
            case 7:
                //保存修改群资料
                break;
            case 8:
                //查看群成员位置
                break;
            case 9:
                //电话联系成员
                break;
            case 10:
                //申请加入群聊
                break;
            case 11:
                //接收用户加入群聊
                break;
            case 12:
                //拒绝用户加入群聊

                break;
            case 13:
                //管理群成员
                break;
            case 14:
                //搜索群
                break;
            case 15:
                //查看群成员个人信息
                break;
            case 16:
                //我
                break;
            case 17:
                //我的资料
                break;
            case 18:
                //保存资料
                break;
            case 19:
                //用户注销
                break;
            case 20:
                //解散群
                break;
            case 21:
                //群内发送消息
                break;
            case 22:
                //消息推送
                break;
            case 23:
                //通知消息
                break;
            default:
                break;
        }

    }
}