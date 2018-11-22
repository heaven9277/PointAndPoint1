package com.example.zhw.piontandpiont2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhw.piontandpiont2.Bean.ChatMessageData;
import com.example.zhw.piontandpiont2.Bean.MessageNotification;
import com.example.zhw.piontandpiont2.Bean.NotificationData;
import com.example.zhw.piontandpiont2.Util.PaseJson;

import java.util.ArrayList;
import java.util.List;

public class QueryData {
    public Context context;
    public static MessageHelper messageHelper;
    public static List<NotificationData> getData(Context context){
        List<NotificationData> datalilst = new ArrayList<>();
        messageHelper = new MessageHelper(context);
        SQLiteDatabase db = messageHelper.getReadableDatabase();
        Cursor cursor = db.query("messageTable",null,null,null,null,null,null);
        System.out.println(".......。。。。"+cursor.getCount());
        if (cursor.getCount()==0){
            datalilst = new ArrayList<>();
        }else{
            cursor.moveToFirst();
            NotificationData notificationData1 = new NotificationData();
            notificationData1.setUserUuid(cursor.getString(cursor.getColumnIndex("userUuid")));
            notificationData1.setNoticeContent(cursor.getString(cursor.getColumnIndex("noticeContent")));
            notificationData1.setNoticeTime(cursor.getString(cursor.getColumnIndex("noticeTime")));
            notificationData1.setGroupName(cursor.getString(cursor.getColumnIndex("groupName")));
            notificationData1.setGroupPortrait(cursor.getString(cursor.getColumnIndex("groupPortrait")));
            notificationData1.setStatus(cursor.getString(cursor.getColumnIndex("groupStatus")));
            notificationData1.setGroupId(cursor.getString(cursor.getColumnIndex("groupId")));
            notificationData1.setSendUuid(cursor.getString(cursor.getColumnIndex("sendUuid")));
            notificationData1.setSendUserName(cursor.getString(cursor.getColumnIndex("sendUserName")));
            notificationData1.setNoticeId(cursor.getString(cursor.getColumnIndex("noticeId")));
            datalilst.add(notificationData1);
            while (cursor.moveToNext()){
                NotificationData notificationData = new NotificationData();
                notificationData.setUserUuid(cursor.getString(cursor.getColumnIndex("userUuid")));
                notificationData.setNoticeContent(cursor.getString(cursor.getColumnIndex("noticeContent")));
                notificationData.setNoticeTime(cursor.getString(cursor.getColumnIndex("noticeTime")));
                notificationData.setGroupName(cursor.getString(cursor.getColumnIndex("groupName")));
                notificationData.setGroupPortrait(cursor.getString(cursor.getColumnIndex("groupPortrait")));
                notificationData.setStatus(cursor.getString(cursor.getColumnIndex("groupStatus")));
                notificationData.setGroupId(cursor.getString(cursor.getColumnIndex("groupId")));
                notificationData.setSendUuid(cursor.getString(cursor.getColumnIndex("sendUuid")));
                notificationData.setSendUserName(cursor.getString(cursor.getColumnIndex("sendUserName")));
                notificationData.setNoticeId(cursor.getString(cursor.getColumnIndex("noticeId")));
                datalilst.add(notificationData);
            }
        }
        cursor.close();
        db.close();
        return datalilst;
    }
    /*
    根据groupId查询数据库里面拥有的数据
     */
    public static List<ChatMessageData> getchatMessageList(Context context,String groupId){
        List<ChatMessageData>  chatMessageList= new ArrayList<>();
        messageHelper = new MessageHelper(context);
        SQLiteDatabase db = messageHelper.getReadableDatabase();
        Cursor cursor = db.query("chatMessageTable",null,"groupId=?",new String[]{groupId},null,null,null);
        System.out.println(".......。。。。"+cursor.getCount());
        if (cursor.getCount()==0){
            chatMessageList = new ArrayList<>();
        }else{
            cursor.moveToFirst();
            ChatMessageData chatMessageData = new ChatMessageData();
            chatMessageData.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
            chatMessageData.setGroupId(cursor.getString(cursor.getColumnIndex("groupId")));
            chatMessageData.setGroupMessage(cursor.getString(cursor.getColumnIndex("groupMessage")));
            chatMessageData.setUserPro(cursor.getString(cursor.getColumnIndex("userPro")));
            chatMessageList.add(chatMessageData);
            while (cursor.moveToNext()){
                ChatMessageData chatMessageData1 = new ChatMessageData();
                chatMessageData1.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
                chatMessageData1.setGroupId(cursor.getString(cursor.getColumnIndex("groupId")));
                chatMessageData1.setGroupMessage(cursor.getString(cursor.getColumnIndex("groupMessage")));
                chatMessageData1.setUserPro(cursor.getString(cursor.getColumnIndex("userPro")));
                chatMessageList.add(chatMessageData1);
            }
        }
        cursor.close();
        db.close();
        return chatMessageList;
    }
    //把自己发的消息放进数据库
    public static void InsertData(Context context,String uuid,String grouId,String groupMessage,String username,String userPro){
        messageHelper = new MessageHelper(context);
        SQLiteDatabase db = messageHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",uuid);
        values.put("groupId",grouId);
        values.put("groupMessage",groupMessage);
        values.put("username",username);
        values.put("userPro",userPro);
        db.insert("chatMessageTable",null,values);
        db.close();
        System.out.println("开始插进数据库");
    }
    //把成员的聊天消息进行放进数据库
    public static void InsertDatas(String text,Context context){
        MessageHelper messageHelper = new MessageHelper(context);
        SQLiteDatabase db = messageHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //从数据里面得到数据
        //得到外面的对象
        List<MessageNotification> notificationList = PaseJson.getMessagesNotificationData(text);
        for (int i=0;i<notificationList.size();i++){
            //得到里面的消息对象
            List<MessageNotification.MessagesBean> messagesBeans = notificationList.get(i).getMessages();
            for (int j=0;j<messagesBeans.size();j++){
                //开始将数据插进数据库
                contentValues.put("uuid",messagesBeans.get(j).getMessageUserId());
                contentValues.put("groupId",notificationList.get(i).getGroupUuid());
                contentValues.put("groupMessage",messagesBeans.get(j).getMessageContent());
                contentValues.put("userPro",messagesBeans.get(j).getUserPortrait());
                contentValues.put("username",messagesBeans.get(j).getMessageUserName());
                db.insert("chatMessageTable",null,contentValues);
                System.out.println("插数据:>>>>>>>>>>>>"+messagesBeans.get(j).getMessageUserId()+notificationList.get(i).getGroupUuid()+messagesBeans.get(j).getMessageContent()+
                        messagesBeans.get(j).getUserPortrait()+" "+messagesBeans.get(j).getMessageUserName());
            }
        }
        db.close();
        System.out.println("开始把收到的信息插进数据库????");
    }
    /*
    更新消息通知
     */
    public static void updateAccept(Context context,String noticeId){
        MessageHelper messageHelper = new MessageHelper(context);
        SQLiteDatabase db = messageHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("groupStatus","1");
        db.update("messageTable",contentValues,"noticeId=?",new String[]{noticeId} );
        System.out.println("更新了");
    }
}
