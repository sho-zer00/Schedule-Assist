package com.example.android.sample.sotuken;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by sho on 2017/10/04.
 */

public class PieChartSample extends AppCompatActivity {

    float rainfall[] = {98.8f,123.8f,161.6f,24.2f,52f,58.2f,35.4f,13.8f,78.4f,203.4f,240.2f,159.7f};
    String monthNames[] = {"Jan","Feb","Mar","Apl","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);

        setupPieChart();
    }

    private void setupPieChart(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0;i<rainfall.length;i++){
            pieEntries.add(new PieEntry(rainfall[i],monthNames[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"Rainfall for Vancouver");
        //以下のコードで色に変化を加えれる
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        //Get the Chart
        PieChart chart = (PieChart)findViewById(R.id.pie_chart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();
    }
}
