package com.example.android.sample.sotuken;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sho on 2017/07/26.
 */

public class Check extends AppCompatActivity {

    private Button check_button1 = null;
    private Button check_button2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);

        check_button1 = (Button)findViewById(R.id.check_button1);
        check_button2 = (Button)findViewById(R.id.check_button2);

        check_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
