package com.example.android.sample.sotuken;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sho on 2017/08/02.
 */

public class CheckDataAdapter {

    private SQLiteDatabase db;
    static private PlanDatabaseHelper helper;

    //コンストラクタ
    public CheckDataAdapter(Context context){
        helper = new PlanDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    //全リストを取得
    public Cursor getAllList(){
        String orderStr = "date asc, time asc";
        return db.query(PlanDatabaseHelper.TABLE_NAME,null,null,null,null,null,orderStr);
    }

}
