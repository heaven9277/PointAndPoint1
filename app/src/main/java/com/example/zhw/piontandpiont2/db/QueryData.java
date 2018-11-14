package com.example.zhw.piontandpiont2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhw.piontandpiont2.Bean.NotificationData;
import com.example.zhw.piontandpiont2.Fragment.MessageFragment;

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
            datalilst = null;
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
                datalilst.add(notificationData);
            }
        }
        cursor.close();
        db.close();
        return datalilst;
    }
}
