package com.example.android.sample.sotuken.anketo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.sample.sotuken.R;

/**
 * Created by sho on 2017/11/10.
 */

public class QuizFType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_type);
        Button button = (Button)findViewById(R.id.f_type_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),QuizDetailF.class);
                startActivity(intent);
            }
        });
    }
}
