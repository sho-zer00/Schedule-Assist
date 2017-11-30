package com.example.android.sample.sotuken.anketo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.sample.sotuken.R;

/**
 * Created by sho on 2017/11/08.
 */

public class QuizResult extends AppCompatActivity {

    private String type;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView type_result = (TextView)findViewById(R.id.type_result);
        Button result_button = (Button)findViewById(R.id.result_button);
        Button result_type = (Button)findViewById(R.id.result_type);

        Intent intent = getIntent();
        type = intent.getStringExtra("Type");
        Log.d("Type",type+"です。");

        type_result.setText(type);

        result_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.equals("Aタイプ")){
                            Intent intent1 = new Intent(getApplication(),QuizAType.class);
                            startActivity(intent1);
                        }
                        else if(type.equals("Bタイプ")){
                            Intent intent2 = new Intent(getApplication(),QuizBType.class);
                            startActivity(intent2);
                        }
                        else if(type.equals("Cタイプ")){
                            Intent intent3 = new Intent(getApplication(),QuizCType.class);
                            startActivity(intent3);
                        }
                        else if(type.equals("Dタイプ")){
                            Intent intent4 = new Intent(getApplication(),QuizDType.class);
                            startActivity(intent4);
                        }
                        else if(type.equals("Eタイプ")){
                            Intent intent5 = new Intent(getApplication(),QuizEType.class);
                            startActivity(intent5);
                        }
                        else if(type.equals("Fタイプ")){
                            Intent intent6 = new Intent(getApplication(),QuizFType.class);
                            startActivity(intent6);
                        }
                        else if(type.equals("Gタイプ")){
                            Intent intent7 = new Intent(getApplication(),QuizGreatActivity.class);
                            startActivity(intent7);
                        }
                        else {
                            Log.d("タイプ","なし");
                        }
                    }
                }
        );

        result_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),QuizType.class);
                startActivity(intent);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplication(),QuizMain.class);
            startActivity(intent);
            return super.onKeyDown(keyCode,event);
        }
        else{
            return false;
        }
    }

}
