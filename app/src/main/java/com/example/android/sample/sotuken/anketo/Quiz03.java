package com.example.android.sample.sotuken.anketo;

import java.io.Serializable;

/**
 * Created by sho on 2017/11/11.
 */

public class Quiz03 implements Serializable {

    int q_num;//問題番号
    String q_string;//第◯問というString
    String question;//アンケートの質問
    String[] choices = new String[4];//選択肢
    int answer1;
    int answer2;
    int answer3;
    int answer4;

    private static  Quiz03[] quizzes = new Quiz03[9]; //クイズの配列

    private Quiz03(int q_num, String q_string, String question, String[] choices,int answer1, int answer2,int answer3, int answer4){
        this.q_num = q_num;
        this.q_string = q_string;
        this.question = question;
        this.choices = choices;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }
    // 問題の登録
    public static void init() {
        quizzes[0] = new Quiz03(0, "第1問", "一つのやることを終えて満足してしまい、やることを先延ばしにしてしまった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[1] = new Quiz03(1, "第2問", "一休みしてしまったら、やる気が起きなくなった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[2] = new Quiz03(2, "第3問", "気づいたらやっていない課題が多くなってしまった",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[3] = new Quiz03(3, "第4問", "新しいことを始めた際、形から入る方だ。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[4] = new Quiz03(4, "第5問", "なんだか疲れていてやらなければいけない時に力が入らなかった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[5] = new Quiz03(5, "第6問", "翌日大事な用事がある時に限って眠れないことがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        //quizzes[6] = new Quiz03(6, "第7問", "",new String[]{"1", "2", "3", "4"},0,1,2,3);
        //quizzes[7] = new Quiz03(7, "第8問", "",new String[]{"1", "2", "3", "4"},0,1,2,3);
        //quizzes[8] = new Quiz03(8, "第9問", "",new String[]{"1", "2", "3", "4"},0,1,2,3);

    }

    // 問題を取得
    public static Quiz03 getQuiz03(int num) {
        if (num >= quizzes.length) {
            return null;
        }
        return quizzes[num];
    }
}
