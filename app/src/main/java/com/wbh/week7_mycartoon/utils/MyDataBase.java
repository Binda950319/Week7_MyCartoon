package com.wbh.week7_mycartoon.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wbh.week7_mycartoon.javabean.Cartoon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class MyDataBase {

    private Context context;
    private MyOpenHelper helper;

    private static MyDataBase mySql;
    private SQLiteDatabase db;

    public static MyDataBase getInstance(Context context) {
        if (mySql == null) {
            mySql = new MyDataBase(context);
        }
        return mySql;
    }

    public MyDataBase(Context scontext) {
        super();
        helper = new MyOpenHelper(scontext, "cars.db");
        db = helper.getReadableDatabase();
    }
    public List<Cartoon> query() {
        List<Cartoon> list = new ArrayList<Cartoon>();
        Cursor cursor = db.rawQuery("select * from  cartoontb", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String theme = cursor.getString(cursor.getColumnIndex("theme"));
                String ranking = cursor.getString(cursor.getColumnIndex("ranking"));
                String state = cursor.getString(cursor.getColumnIndex("state"));
                String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
                Cartoon  cartoon = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                list.add(cartoon);
            }
            return list;
        }
        return null;
    }
}
