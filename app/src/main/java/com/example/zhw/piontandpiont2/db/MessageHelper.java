package com.example.zhw.piontandpiont2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageHelper extends SQLiteOpenHelper {
    public MessageHelper(Context context) {
        super(context,"itcast.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化数据库的表结构，执行一条表的SQL语句
        db.execSQL("CREATE TABLE messageTable(_id INTEGER PRIMARY KEY AUTOINCREMENT,userUuid VARCHAR(50),sendUuid VARCHAR(50),sendUserName VARCHAR(50),noticeId VARCHAR(50),noticeContent VARCHAR(50), " +
                "noticeTime VARCHAR(50),groupName VARCHAR(50),groupPortrait VARCHAR(100),groupStatus VARCHAR(50),groupId VARCHAR(50))");
        db.execSQL("CREATE TABLE chatMessageTable(_id INTEGER PRIMARY KEY AUTOINCREMENT,uuid VARCHAR(50),username VARCHAR(50),groupId VARCHAR(50),groupMessage VARCHAR(200),userPro VARCHAR(200))" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
