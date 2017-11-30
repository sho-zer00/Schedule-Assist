package com.example.android.sample.sotuken.anketo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.android.sample.sotuken.R;

/**
 * Created by sho on 2017/11/07.
 */

public class QuizType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_type_menu);
    }

    public void openA(View view){
        Intent intent = new Intent(getApplication(),QuizAType.class);
        startActivity(intent);
    }

    public void openB(View view){
        Intent intent = new Intent(getApplication(),QuizBType.class);
        startActivity(intent);
    }

    public void openC(View view){
        Intent intent = new Intent(getApplication(),QuizCType.class);
        startActivity(intent);
    }
    public void openD(View view){
        Intent intent = new Intent(getApplication(),QuizDType.class);
        startActivity(intent);
    }
    public void openE(View view){
        Intent intent = new Intent(getApplication(),QuizEType.class);
        startActivity(intent);
    }
    public void openF(View view){
        Intent intent = new Intent(getApplication(),QuizFType.class);
        startActivity(intent);
    }
    public void openG(View view){
        Intent intent = new Intent(getApplication(),QuizGreatActivity.class);
        startActivity(intent);
    }


}
