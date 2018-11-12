package com.example.zhw.piontandpiont2.MessagePack;


import com.example.zhw.piontandpiont2.ChatActivity;
import com.example.zhw.piontandpiont2.CreateActivity;
import com.example.zhw.piontandpiont2.EditGroupActivity;
import com.example.zhw.piontandpiont2.GroupInfoActivity;
import com.example.zhw.piontandpiont2.HomeActivity;
import com.example.zhw.piontandpiont2.MainActivity;
import com.example.zhw.piontandpiont2.ManageGroupActivity;
import com.example.zhw.piontandpiont2.SearchActivity;
import com.example.zhw.piontandpiont2.Threadpack.ChatDataThread;
import com.example.zhw.piontandpiont2.Threadpack.ChatMessageThread;
import com.example.zhw.piontandpiont2.Threadpack.CreateGroupActivityThread;
import com.example.zhw.piontandpiont2.Threadpack.FirstActiviityThread;
import com.example.zhw.piontandpiont2.Threadpack.GroupInfoThread;
import com.example.zhw.piontandpiont2.Threadpack.MainThread;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyEditGroup;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyManagerGroup;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyOutGroup;

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
                ChatDataThread chatDataThread = new ChatDataThread(text, ChatActivity.getChat_handler());
                chatDataThread.start();
                System.out.println("开始聊天内容的线程");
                break;
            case 5:
                //群资料
                GroupInfoThread groupInfoThread = new GroupInfoThread(text, GroupInfoActivity.getGroupInfoHandler());
                groupInfoThread.start();
                System.out.println("开始群资料的线程");
                break;
            case 6:
                //退出群聊
                NotifyOutGroup notifyOutGroup = new NotifyOutGroup(text,GroupInfoActivity.getGroupInfoHandler());
                System.out.println("开始退出群资料的");
                break;
            case 7:
                System.out.println("后台数据");
                NotifyEditGroup notifyEditGroup = new NotifyEditGroup(text, EditGroupActivity.getEditGroupInfoHandler());
                System.out.println("开始修改群资料的");
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
                NotifyEditGroup application = new NotifyEditGroup(text,SearchActivity.getSeach_handler());
                System.out.println("开始申请加入群聊的");
                break;
            case 11:
                //接收用户加入群聊
                break;
            case 12:
                //拒绝用户加入群聊

                break;
            case 13:
                System.out.println("后台数据");
                NotifyManagerGroup notifyManagerGroup = new NotifyManagerGroup(text, ManageGroupActivity.getmHandler());
                System.out.println("开始删除成员资料的");
                //管理群成员
                break;
            case 14:
                NotifyManagerGroup notifyManagerGroup1 = new NotifyManagerGroup(text, SearchActivity.getSeach_handler());
                System.out.println("开始搜索群的");
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
                NotifyOutGroup notifyOutGroup1 = new NotifyOutGroup(text,GroupInfoActivity.getGroupInfoHandler());
                System.out.println("开始解散群资料的");
                //解散群
                break;
            case 21:
                //群内发送消息

                ChatMessageThread chatMessageThread = new ChatMessageThread(text, ChatActivity.getChat_handler());
                chatMessageThread.start();
                System.out.println("开始聊天内容的线程");
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