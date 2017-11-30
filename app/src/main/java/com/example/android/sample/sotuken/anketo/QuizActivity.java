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
 * Created by sho on 2017/11/07.
 */

public class QuizActivity extends AppCompatActivity {

    private TextView tv_num;
    private TextView que;
    private Button[] buttons;

    private Quiz quiz;

    int quiz_num = 0;

    int one_count = 0;
    int fifth_count = 0;
    int three_count = 0;
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
            quiz = (Quiz) intent.getSerializableExtra("Quiz");
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
                        one_count += 1;
                        Log.d("action","answer1");
                    }
                    else if(i == quiz.answer2) {
                        one_count += 2;
                        Log.d("action","answer2");
                    }
                    else if(i == quiz.answer3){
                        one_count += 3;
                        Log.d("action","answer3");
                    }
                    else if(i == quiz.answer4){
                        one_count += 4;
                        Log.d("action","answer4");
                    }
                }
            }
        }
        Log.d("one_count",one_count+"カウント");
        if(one_count == max){
            type = "Aタイプ";
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }


        if(quiz_num >= 3 && quiz_num <= 5) {
            for (int i = 0; i < buttons.length; i++) {
                if (view.getId() == buttons[i].getId()) {
                    if (i == quiz.answer1) {
                        three_count += 1;
                        Log.d("action","answer1");
                    }
                    else if(i == quiz.answer2) {
                        three_count += 2;
                        Log.d("action","answer2");
                    }
                    else if(i == quiz.answer3){
                        three_count += 3;
                        Log.d("action","answer3");
                    }
                    else if(i == quiz.answer4){
                        three_count += 4;
                        Log.d("action","answer4");
                    }
                }
            }
        }
        Log.d("three_count",three_count+"カウント");
        if(three_count == max){
            type = "Cタイプ";
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }


        if(quiz_num >= 6 && quiz_num <= 8) {
            for (int i = 0; i < buttons.length; i++) {
                if (view.getId() == buttons[i].getId()) {
                    if (i == quiz.answer1) {
                        fifth_count += 1;
                        Log.d("action","answer1");
                    }
                    else if(i == quiz.answer2) {
                        fifth_count += 2;
                        Log.d("action","answer2");
                    }
                    else if(i == quiz.answer3){
                        fifth_count += 3;
                        Log.d("action","answer3");
                    }
                    else if(i == quiz.answer4){
                        fifth_count += 4;
                        Log.d("action","answer4");
                    }
                }
            }
        }
        Log.d("two_count",fifth_count+"カウント");
        if(fifth_count == max){
            type = "Eタイプ";
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }

        num_max = one_count;
        type = "Aタイプ";
        if(num_max <= three_count){
            num_max = three_count;
            type = "Cタイプ";
        }
        if(num_max <= fifth_count){
            num_max = fifth_count;
            type = "Eタイプ";
        }

        Log.d("num_max",num_max+"です");


        Log.d("Type",type+"です。");
        quiz_num = quiz_num +1 ;
        Log.d("質問",quiz_num+"番目の質問が終わりました。");
        quiz = Quiz.getQuiz(quiz.q_num + 1);
        if (quiz != null) {
            show();
        } else {
            Intent intent = new Intent(getApplication(),QuizResult.class); // 結果の画面へ移動
            intent.putExtra("Type",type);
            startActivity(intent);
        }
    }
}
