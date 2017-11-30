package com.example.android.sample.sotuken.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.R;

/**
 * Created by sho on 2017/07/26.
 */

public class Check extends AppCompatActivity {

    private Button check_button1 = null;
    private Button check_button2 = null;
    private PlanDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);

        check_button1 = (Button)findViewById(R.id.check_button1);
        check_button2 = (Button)findViewById(R.id.check_button2);




        check_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CheckPlanActivity.class);
                startActivity(intent);
            }
        });

        check_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),EvaluationActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            return super.onKeyDown(keyCode, event);
        } else {
            return false;
        }
    }
}
