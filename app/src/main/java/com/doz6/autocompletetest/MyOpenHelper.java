package com.doz6.autocompletetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    public String createTableSQL="create table if not exists word"+
            "(_id integer primary key autoincrement,word text)";

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSQL);
        db.execSQL("insert into word(word) values(?)",new String[]{"Android应用开发教程"});
        db.execSQL("insert into word(word) values(?)",new String[]{"疯狂Android讲义"});
        db.execSQL("insert into word(word) values(?)",new String[]{"Android开发揭秘"});
        db.execSQL("insert into word(word) values(?)",new String[]{"Android开发实战经典"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
