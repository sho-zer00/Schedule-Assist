package com.example.android.sample.sotuken.anketo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.sample.sotuken.R;

/**
 * Created by sho on 2017/11/11.
 */

public class Quiz03Activity extends AppCompatActivity {

    private TextView tv_num;
    private TextView que;
    private Button[] buttons;

    private Quiz03 quiz;

    int quiz_num = 0;

    int two_count = 0;
    int six_count = 0;
    int g_count = 6;
    int num_max = 0;
    int max = 12;
    String type ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //画面上のウィジェットを取得
        tv_num = (TextView) findViewById(R.id.q_string);
        que = (TextView)findViewById(R.id.question);
        buttons = new Button[4];
        buttons[0] = (Button) findViewById(R.id.btn1);
        buttons[1] = (Button) findViewById(R.id.btn2);
        buttons[2] = (Button) findViewById(R.id.btn3);
        buttons[3] = (Button) findViewById(R.id.btn4);

        // データを受け取る
        Intent intent = getIntent();
        if (intent != null) {
            quiz = (Quiz03) intent.getSerializableExtra("Quiz03");
            show();
        }
    }

    // 表示に反映させる
    void show() {
        if (quiz != null) {
            tv_num.setText(quiz.q_string);
            que.setText(quiz.question);
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText(quiz.choices[i]);
            }
        }
    }

    //アンケートの結果の答えの反映の仕方
    public void answer(View view){
        //ボタンの値を足していく
        if(quiz_num <= 2) {
            for (int i = 0; i < buttons.length; i++) {
                if (view.getId() == buttons[i].getId()) {
                    if (i == quiz.answer1) {
                        two_count += 1;
                        Log.d("action","answer1");
                    }
                    else if(i == quiz.answer2) {
                        two_count += 2;
                        Log.d("action","answer2");
                    }
                    else if(i == quiz.answer3){
                        two_count += 3;
                        Log.d("action","answer3");
                    }
                    else if(i == quiz.answer4){
                        two_count += 4;
                        Log.d("action","answer4");
                    }
                }
            }
        }
        Log.d("one_count",two_count+"カウント");
        if(two_count == max){
            type = "Bタイプ";
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }

        if(quiz_num >= 3 && quiz_num <= 5) {
            for (int i = 0; i < buttons.length; i++) {
                if (view.getId() == buttons[i].getId()) {
                    if (i == quiz.answer1) {
                        six_count += 1;
                        Log.d("action","answer1");
                    }
                    else if(i == quiz.answer2) {
                        six_count += 2;
                        Log.d("action","answer2");
                    }
                    else if(i == quiz.answer3){
                        six_count += 3;
                        Log.d("action","answer3");
                    }
                    else if(i == quiz.answer4){
                        six_count += 4;
                        Log.d("action","answer4");
                    }
                }
            }
        }
        Log.d("two_count",six_count+"カウント");
        if(six_count == max){
            type = "Fタイプ";
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }



        num_max = two_count;
        type = "Bタイプ";

        if(num_max <= six_count){
            num_max = six_count;
            type = "Fタイプ";
        }
        if(num_max <= g_count){
            type = "Gタイプ";
        }

        Log.d("num_max",num_max+"です");


        Log.d("Type",type+"です。");
        quiz_num = quiz_num +1 ;
        Log.d("質問",quiz_num+"番目の質問が終わりました。");
        quiz = Quiz03.getQuiz03(quiz.q_num + 1);
        if (quiz != null) {
            show();
        } else {
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }
    }
}
