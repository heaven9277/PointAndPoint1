package com.example.zhw.piontandpiont2.MessagePack;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhw.piontandpiont2.Bean.NotificationData;
import com.example.zhw.piontandpiont2.ChatActivity;
import com.example.zhw.piontandpiont2.ConnectMemberActivity;
import com.example.zhw.piontandpiont2.CreateActivity;
import com.example.zhw.piontandpiont2.EditGroupActivity;
import com.example.zhw.piontandpiont2.GroupInfoActivity;
import com.example.zhw.piontandpiont2.GroupPositionActivity;
import com.example.zhw.piontandpiont2.GroupUserInfoActivity;
import com.example.zhw.piontandpiont2.HomeActivity;
import com.example.zhw.piontandpiont2.MainActivity;
import com.example.zhw.piontandpiont2.ManageGroupActivity;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyChat;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyEditGroup;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyManagerGroup;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyMessage;
import com.example.zhw.piontandpiont2.NotifyActivity.NotifyOutGroup;
import com.example.zhw.piontandpiont2.ProfileActivity;
import com.example.zhw.piontandpiont2.SearchActivity;
import com.example.zhw.piontandpiont2.Threadpack.ChatDataThread;
import com.example.zhw.piontandpiont2.Threadpack.CreateGroupActivityThread;
import com.example.zhw.piontandpiont2.Threadpack.FirstActiviityThread;
import com.example.zhw.piontandpiont2.Threadpack.GetResultChangeProfile;
import com.example.zhw.piontandpiont2.Threadpack.GroupInfoThread;
import com.example.zhw.piontandpiont2.Threadpack.MainThread;
import com.example.zhw.piontandpiont2.Threadpack.SendFisrtDataThread;
import com.example.zhw.piontandpiont2.Util.ParseJson;
import com.example.zhw.piontandpiont2.db.MessageHelper;
import com.example.zhw.piontandpiont2.db.QueryData;

import java.util.List;

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
                new NotifyOutGroup(text,GroupInfoActivity.getGroupInfoHandler());
                System.out.println("开始退出群资料的");
                break;
            case 7:
                System.out.println("后台数据");
                new NotifyEditGroup(text, EditGroupActivity.getEditGroupInfoHandler());
                System.out.println("开始修改群资料的");
                //保存修改群资料
                break;
            case 8:
                System.out.println("开始位置");
                //查看群成员位置
               new NotifyManagerGroup(text, GroupPositionActivity.positionHandler);
                break;
            case 9:
                //电话联系成员
                new NotifyManagerGroup(text, ConnectMemberActivity.memberHandler);
                break;
            case 10:
                //申请加入群聊
                new NotifyEditGroup(text,SearchActivity.getSeach_handler());
                System.out.println("开始申请加入群聊的");
                break;
            case 11:
                //接收用户加入群聊
                SendFisrtDataThread sendFisrtDataThread = new SendFisrtDataThread(HomeActivity.user_name);
                sendFisrtDataThread.start();
                break;
            case 12:
                //拒绝用户加入群聊
                break;
            case 13:
                System.out.println("后台数据");
                new NotifyManagerGroup(text, ManageGroupActivity.getmHandler());
                System.out.println("开始删除成员资料的");
                //管理群成员
                break;
            case 14:
                new NotifyManagerGroup(text, SearchActivity.getSeach_handler());
                System.out.println("开始搜索群的");
                //搜索群
                break;
            case 15:
                //查看群成员个人信息
                new NotifyManagerGroup(text, GroupUserInfoActivity.userinfo_handler);
                System.out.println("开始查看群成员个人信息的");
                break;
            case 16:
                //我
                break;
            case 17:
                //我的资料
                break;
            case 18:
                //保存资料
                GetResultChangeProfile getResultChangeProfile = new GetResultChangeProfile(text, ProfileActivity.getHandler());
                getResultChangeProfile.start();
                break;
            case 19:
                //用户注销
                new NotifyManagerGroup(text,HomeActivity.outlogin_handler);
                break;
            case 20:
                new NotifyOutGroup(text,GroupInfoActivity.getGroupInfoHandler());
                System.out.println("开始解散群资料的");
                //解散群
                break;
            case 21:
                //群内发送消息
                //                //ChatMessageThread chatMessageThread = new ChatMessageThread(text, ChatActivity.getChat_handler());
                //                //chatMessageThread.start();
                //                //System.out.println("开始聊天内容的线程");
                break;
            case 22:
                //消息推送
                //将消息放进数据库
                QueryData.InsertDatas(text,MainActivity.context);
                if (ChatActivity.isChatActivity.equals("Chatactivity")){
                    new NotifyChat(text,ChatActivity.getChat_handler());
                }
                SendFisrtDataThread sendFisrtDataThread1 = new SendFisrtDataThread(MainActivity.main_username);
                sendFisrtDataThread1.start();
                System.out.println("开始聊天内容的通知");
                //将消息放进数据
                break;
            case 23:
                //在线通知消息
                //把数据放进数据库
                MessageHelper messageHelper = new MessageHelper(MainActivity.context);
                SQLiteDatabase db = messageHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                System.out.println("开始插数据了");
                boolean isApplicaton=false;//判断用户是否已申请加入群聊。
                //开始插数据
                List<NotificationData> notificationDataList = ParseJson.getNotificationData(text);
                for (int i=0;i<notificationDataList.size();i++){
                    NotificationData notificationData = notificationDataList.get(i);
                    Cursor cursor = db.query ("usertable",null,null,null,null,null,null);
                    //判断游标是否为空
                   cursor.moveToFirst();
                    //遍历游标
                    for(int n=0;n<cursor.getCount();n++) {
                        cursor.move(n);
                        //获得用户ID
                        String userUuid = cursor.getString(1);
                        //获得群名
                        String groupName = cursor.getString(7);
                        System.out.println(userUuid + "aaaaaaaaaa" + groupName + "mmmmmmm");
                        if (notificationData.getUserUuid().equals(userUuid)&&notificationData.getGroupName().equals(groupName)){
                            isApplicaton=true;
                        }
                    }
                    if(isApplicaton==false){
                        contentValues.put("userUuid",notificationData.getUserUuid());
                        contentValues.put("noticeContent",notificationData.getNoticeContent());
                        contentValues.put("noticeTime",notificationData.getNoticeTime());
                        contentValues.put("groupName",notificationData.getGroupName());
                        contentValues.put("groupPortrait",notificationData.getGroupPortrait());
                        contentValues.put("groupStatus",notificationData.getStatus());
                        contentValues.put("groupId",notificationData.getGroupId());
                        contentValues.put("sendUuid",notificationData.getSendUuid());
                        contentValues.put("sendUserName",notificationData.getSendUserName());
                        contentValues.put("noticeId",notificationData.getNoticeId());
                        db.insert("messageTable",null,contentValues);
                        System.out.println("插入数据库"+notificationData.getUserUuid()+""+notificationData.getNoticeContent()+""+notificationData.getGroupName());
                        System.out.println("通知编号"+notificationData.getNoticeId());
                    }
                    isApplicaton=false;
                }
                System.out.println("运行到这里");
                System.out.println("状态呢说的话"+HomeActivity.isHomeActivity);
               if (HomeActivity.isHomeActivity.equals("Homeactivity")){
                   new NotifyMessage(text,HomeActivity.message_handler);
               }
                break;

            default:
                break;
        }

    }
}