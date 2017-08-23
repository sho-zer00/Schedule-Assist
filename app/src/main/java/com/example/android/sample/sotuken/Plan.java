package com.example.android.sample.sotuken;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sho on 2017/07/26.
 */

public class Plan extends AppCompatActivity {

    private PlanDatabaseHelper helper = null;
    private EditText txtDate = null;
    private EditText txtTime = null;
    private EditText txtPlan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_layout);

        helper = new PlanDatabaseHelper(this);//ヘルパーの準備
        txtDate = (EditText)findViewById(R.id.txtDate);
        txtTime = (EditText)findViewById(R.id.txtTime);
        txtPlan = (EditText)findViewById(R.id.txtPlan);
    }

    public void date_onClick(View view) {
        DialogFragment dialog = new PlanDateFragment();
        dialog.show(getFragmentManager(), "dialog_date");
    }

    public void time_onClick(View view){
        DialogFragment dialog = new PlanTimeFragment();
        dialog.show(getFragmentManager(), "dialog_time");
    }

    public void do_Button(View view){
        DialogFragment dialog = new PlanDoFragment();
        dialog.show(getFragmentManager(), "dialog_radio");
    }

    public void input_Button(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("date",txtDate.getText().toString());
            cv.put("time",txtTime.getText().toString());
            cv.put("doing",txtPlan.getText().toString());
            cv.put("flag",0);
            db.insertOrThrow("plans",null,cv);
            Toast.makeText(this,"データの登録に成功しました",
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

}