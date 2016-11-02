package com.wbh.week7_mycartoon.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/26.
 */
public class MyOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public MyOpenHelper(Context context, String name) {
        super(context, name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table if not exists cartoontb(_id integer primary key autoincrement, id text, name text," +
                " icon text, author text, theme text, ranking text, state text, introduction text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
