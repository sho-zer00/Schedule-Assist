package com.example.android.sample.sotuken;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sho on 2017/07/27.
 */

public class PlanDatabaseHelper extends SQLiteOpenHelper {

    static final private String DB_NAME = "plan.sqlite";
    static final private int VERSION = 1;
    public static final String TABLE_NAME = "plans";

    public PlanDatabaseHelper(Context context){super(context,DB_NAME,null,VERSION);}

    @Override
    public void onOpen(SQLiteDatabase db){super.onOpen(db);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("+
        "_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT NOT NULL, time TEXT NOT NULL, doing TEXT NOT NULL, flag INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
