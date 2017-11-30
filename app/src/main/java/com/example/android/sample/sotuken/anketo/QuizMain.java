package com.example.android.sample.sotuken.anketo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.simpleline.SimpleLineDatabaseHelper;
import com.example.android.sample.sotuken.activity.EvaluationActivity;
import com.example.android.sample.sotuken.chat.ChatMain;

public class QuizMain extends AppCompatActivity {

    private SimpleLineDatabaseHelper helper;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        TextView level_result = (TextView)findViewById(R.id.level_result);
        TextView level_text = (TextView)findViewById(R.id.level4_text);
        Button button = (Button)findViewById(R.id.button_quiz);
        Button button1 = (Button)findViewById(R.id.button_level);

        //アンケートの初期化
        Quiz.init();
        Quiz02.init();
        Quiz03.init();
        //SimpleLineDatabaseHelperからachievement,達成度を取り出して、達成度レベルを出す
        helper = new SimpleLineDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String qu = "SELECT achieve FROM achievement ORDER BY _id DESC LIMIT 1 ";
        Cursor cs = db.rawQuery(qu, null);
        cs.moveToFirst();
        int ave = cs.getInt(cs.getColumnIndex("achieve"));
        Log.d("達成度",ave+"です");

        //達成度に応じてレベル分け
        if(ave <= 40){
            level = "レベル1";
            level_text.setVisibility(View.VISIBLE);
            level_text.setText("あなたの今のスケジュール管理の仕方を大きく改善する必要があります。\n\n" +
                    "ですので、あなたの計画達成度レベルに合わせたアンケートに答えていただきたいと思います。\n\n"+
                    "この結果から、あなたがどういったタイプのスケジュール管理ができていない人かがわかります。\n\n"+
                    "そのタイプに合わせたスケジュールの立て方をアドバイスしていこうと思いますので正直に答えてください。\n\n"
            );
        }
        else if(ave > 40 && ave <= 70){
            level = "レベル2";
            level_text.setVisibility(View.VISIBLE);
            level_text.setText("あなたの今のスケジュール管理の仕方を改善する必要があります。\n\n" +
                    "ですので、あなたの計画達成度レベルに合わせたアンケートに答えていただきたいと思います。\n\n"+
                    "この結果から、あなたがどういったタイプのスケジュール管理ができていない人かがわかります。\n\n"+
                    "そのタイプに合わせたスケジュールの立て方をアドバイスしていこうと思いますので正直に答えてください。\n\n"
            );
        }
        else if(ave > 70 && ave <= 85){
            level = "レベル3";
            level_text.setVisibility(View.VISIBLE);
            level_text.setText("スケジュール管理がきちんとできていると思いますが、\n\n" +
                    "もう少しだけ今のスケジュール管理の仕方を少し見直す必要があります。\n\n" +
                    "ですので、あなたの計画達成度レベルに合わせたアンケートに答えていただきたいと思います。\n\n"+
                    "この結果から、あなたがどういったタイプのスケジュール管理ができていない人かがわかります。\n\n"+
                    "そのタイプに合わせたスケジュールの立て方をアドバイスしていこうと思いますので正直に答えてください。\n\n"
            );
        }
        else {
            level = "レベル4";
            level_text.setVisibility(View.VISIBLE);
        }
        Log.d("レベル",level);

        level_result.setText(level);

        //ボタンを押したときのイベント
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (level){
                            case "レベル1":
                                Intent intent1 = new Intent(getApplication(),QuizActivity.class);
                                intent1.putExtra("Quiz",Quiz.getQuiz(0));
                                startActivity(intent1);
                                break;
                            case "レベル2":
                                Intent intent2 = new Intent(getApplication(),Quiz02Activity.class);
                                intent2.putExtra("Quiz02",Quiz02.getQuiz02(0));
                                startActivity(intent2);
                                break;
                            case "レベル3":
                                Intent intent3 = new Intent(getApplication(),Quiz03Activity.class);
                                intent3.putExtra("Quiz03",Quiz03.getQuiz03(0));
                                startActivity(intent3);
                                break;
                            case "レベル4":
                                //Toast.makeText(QuizMain.this,"レベル4です。", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
        );

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),QuizLevel.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.chat:
                Intent intent = new Intent(getApplication(),ChatMain.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplication(),EvaluationActivity.class);
            startActivity(intent);
            return super.onKeyDown(keyCode,event);
        }
        else{
            return false;
        }
    }

}
