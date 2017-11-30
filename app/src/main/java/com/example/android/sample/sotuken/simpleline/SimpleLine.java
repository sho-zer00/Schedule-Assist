package com.example.android.sample.sotuken.simpleline;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sample.sotuken.CheckPlanDateSort;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.activity.EvaluationActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Created by sho on 2017/10/11.
 */

public class SimpleLine extends AppCompatActivity {

    private SimpleLineDatabaseHelper helper;
    private PlanDatabaseHelper helper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);

        LineChart mChart = (LineChart)findViewById(R.id.linechart);

        //データの数を制限していく
        //recodeCountが6以上なら、１個消す
        //別のデータベースを用意して、そちらでは詳しい詳細データをだす
        //例文 Cursor cs = db2.query(AlarmDatabaseHelper.TABLE_NAME, new String[]{"min(date)"}, null, null, null, null, null);
        //例文 db2.delete(helper2.TABLE_NAME,"date = ? ",params);
        //例文 long recodeCount = DatabaseUtils.queryNumEntries(db2,AlarmDatabaseHelper.TABLE_NAME,null,null);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        //達成率データベースに接続
        helper = new SimpleLineDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        long recodeCount = DatabaseUtils.queryNumEntries(db,SimpleLineDatabaseHelper.TABLE_NAME,null,null);
        /*
        Log.d("recordCount",":"+recodeCount);
        if(recodeCount > 6){
            db.delete(helper.TABLE_NAME,"_id = ?",new String[]{"min(_id)"});
            Log.d("delete","削除されました");
        }
        */
        Cursor cs = db.query(SimpleLineDatabaseHelper.TABLE_NAME,null,null,null,null,null,"date asc",null);

        int count = 0;
        //グラフ用データに格納していく
        ArrayList<Entry> entries = new ArrayList<>();
        if(cs.moveToFirst()){
            do{
                int a = cs.getInt(cs.getColumnIndex("achieve"));
                String date = cs.getString(cs.getColumnIndex("day"));
                float data = (float)a;
                Log.d("data","格納されたデータ :"+data+" Num :"+count+ "Date :"+date);
                entries.add(new Entry(count,data));
                count++;
            }while (cs.moveToNext());
        }
        //データをセット
        LineDataSet set1 = new LineDataSet(entries,"achieve");
        //ラベル
        set1.setFillAlpha(110);

        set1.setColor(Color.RED);

        ArrayList<ILineDataSet>dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        mChart.setData(data);

        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.line_graph:
                break;
            case R.id.line_detail:
                Intent intent = new Intent(getApplication(),GraphDetailActivity.class);
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
