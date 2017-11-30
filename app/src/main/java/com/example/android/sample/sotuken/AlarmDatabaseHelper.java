package com.example.android.sample.sotuken;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sho on 2017/10/16.
 */

public class AlarmDatabaseHelper extends SQLiteOpenHelper {
    static final private String DB_NAME = "alarm.sqlite";
    static final private int VERSION = 1;
    public static final String TABLE_NAME = "alarms";

    //コンストラクタ
    public AlarmDatabaseHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    //データベース作成
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE alarms (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT NOT NULL);");
    }

    //データベースをバージョンアップした時、テーブルを再作成
    @Override
    public void onUpgrade(SQLiteDatabase db,int old_v,int new_v){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
