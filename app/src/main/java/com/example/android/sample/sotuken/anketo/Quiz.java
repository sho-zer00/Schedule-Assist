package com.example.android.sample.sotuken.anketo;

import java.io.Serializable;

/**
 * Created by sho on 2017/11/07.
 */

public class Quiz implements Serializable {
    int q_num;//問題番号
    String q_string;//第◯問というString
    String question;//アンケートの質問
    String[] choices = new String[4];//選択肢
    int answer1;
    int answer2;
    int answer3;
    int answer4;

    private static  Quiz[] quizzes = new Quiz[15]; //クイズの配列

    private Quiz(int q_num, String q_string, String question, String[] choices,int answer1, int answer2,int answer3, int answer4){
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
        quizzes[0] = new Quiz(0, "第1問", "やることをやっている最中に〇〇してしまった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[1] = new Quiz(1, "第2問", "早く〇〇しなくてはと思うけど中々始められなかった",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[2] = new Quiz(2, "第3問", "スマホやテレビをみていたら、時間が経っていた。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        //quizzes[3] = new Quiz(3, "第4問", "一つのやることを終えて満足してしまい、やることを先延ばしにしてしまった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        //quizzes[4] = new Quiz(4, "第5問", "一休みしてしまったら、やる気が起きなくなった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        //quizzes[5] = new Quiz(5, "第6問", "気づいたらやっていない課題が多くなってしまった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[3] = new Quiz(3, "第4問", "やり始めるのが億劫だと感じたことがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[4] = new Quiz(4, "第5問", "手に取る気にもならなかったことがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[5] = new Quiz(5, "第6問", "中々やっていることが続かないことがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[6] = new Quiz(6, "第7問", "今日はなんだか集中力が低いなあと思う日が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[7] = new Quiz(7, "第8問", "やる気が出ない時が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[8] = new Quiz(8, "第9問", "調子が出なくてやる気にならないなあと思う日が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);

        /*
        quizzes[9] = new Quiz(9, "第10問", "できた試しがない、、と手を出さなかったことがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[10] = new Quiz(10, "第11問", "どうせできないとやる前から決めつけることがある。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[11] = new Quiz(11, "第12問", "ものすごいネガティブ思考である。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[12] = new Quiz(12, "第13問", "今日はなんだか集中力が低いなあと思う日が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[13] = new Quiz(13, "第14問", "やる気が出ない時が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        quizzes[14] = new Quiz(14, "第15問", "調子が出なくてやる気にならないなあと思う日が多かった。",new String[]{"1", "2", "3", "4"},0,1,2,3);
        */
        }

    // 問題を取得
    public static Quiz getQuiz(int num) {
        if (num >= quizzes.length) {
            return null;
        }
        return quizzes[num];
    }
}
