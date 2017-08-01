package com.example.android.sample.sotuken;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sho on 2017/07/27.
 */

public class PlanDatabaseHelper extends SQLiteOpenHelper {

    static final private String DBNAME = "plan.sqlite";
    static final private int VERSION = 1;

    public PlanDatabaseHelper(Context context){super(context,DBNAME,null,VERSION);}

    @Override
    public void onOpen(SQLiteDatabase db){super.onOpen(db);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE plans ("+
        "date TEXT, time TEXT, doing TEXT, flag INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        db.execSQL("DROP TABLE IF EXISTS plans");
        onCreate(db);
    }
}
