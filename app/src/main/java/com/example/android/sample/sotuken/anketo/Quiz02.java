package com.example.android.sample.sotuken.anketo;

import java.io.Serializable;

/**
 * Created by sho on 2017/11/09.
 */

public class Quiz02 implements Serializable{
    int q_num;//問題番号
    String q_string;//第◯問というString
    String question;//アンケートの質問
    String[] choices = new String[4];//選択肢
    int answer1;
    int answer2;
    int answer3;
    int answer4;

    private static  Quiz02[] quizzes = new Quiz02[9]; //クイズの配列

    private Quiz02(int q_num, String q_string, String question, String[] choices,int answer1, int answer2,int answer3, int answer4){
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
        quizzes[0] = new Quiz02(0, "第1問", "できた試しがない、、と手を出さなかったことがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[1] = new Quiz02(1, "第2問", "どうせできないとやる前から決めつけ計画に取り入れなかったがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[2] = new Quiz02(2, "第3問", "ものすごいネガティブ思考である。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[3] = new Quiz02(3, "第4問", "今日はなんだか集中力が低いなあと思う日が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[4] = new Quiz02(4, "第5問", "やる気が出ない時が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[5] = new Quiz02(5, "第6問", "調子が出なくてやる気にならないなあと思う日が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[6] = new Quiz02(6, "第7問", "一つのやることを終えて満足してしまい、やることを先延ばしにしてしまった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[7] = new Quiz02(7, "第8問", "一休みしてしまったら、やる気が起きなくなった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[8] = new Quiz02(8, "第9問", "気づいたらやっていない課題が多くなってしまった。",new String[]{"1", "2", "3", "4"},0,1,2,3);

    }

    // 問題を取得
    public static Quiz02 getQuiz02(int num) {
        if (num >= quizzes.length) {
            return null;
        }
        return quizzes[num];
    }

}
