package com.example.android.sample.sotuken.simpleline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sho on 2017/10/11.
 */

public class SimpleLineDatabaseHelper extends SQLiteOpenHelper {

    static final private String DB_NAME = "graph.sqlite";
    static final private int VERSION = 1;
    public static final String TABLE_NAME = "achievement";

    //コンストラクタ
    public SimpleLineDatabaseHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase  db){
        super.onOpen(db);
    }

    //データベース作成
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE achievement (_id INTEGER PRIMARY KEY AUTOINCREMENT, date INTEGER, record_count INTEGER, flag_count INTEGER, achieve INTEGER, day TEXT NOT NULL);");
    }

    //データベースをバージョンアップした時、テーブルを再作成
    @Override
    public void onUpgrade(SQLiteDatabase db,int old_v,int new_v){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
