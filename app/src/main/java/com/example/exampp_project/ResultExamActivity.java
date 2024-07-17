package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultExamActivity extends AppCompatActivity implements View.OnClickListener {
    Typeface myFont;
    AppCompatTextView itemNumE,itemTR,itemFR,itemBR,itemTimeEnd,itemPercentageTR,itemPercentageFR;
    AppCompatButton itemShowHistoryExams;
    HashMap<String, Double> resultlist=new HashMap<>();
    SQLiteDatabase db;
    SharedPreferences pref;
    Integer pAId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultexam);
        myFont = Typeface.createFromAsset(getAssets(), "fonts/FontsFree-Net-ir_sans.ttf");
        itemShowHistoryExams=findViewById(R.id.itemShowHistoryExams);
        itemShowHistoryExams.setOnClickListener(this);
        pref=getSharedPreferences("Author",MODE_PRIVATE);
        pAId =pref.getInt("authorId",-1);
        itemNumE=findViewById(R.id.itemNumE);
        itemTR=findViewById(R.id.itemTR);
        itemPercentageTR=findViewById(R.id.itemPercentageTR);
        itemPercentageFR=findViewById(R.id.itemPercentageFR);
        itemFR=findViewById(R.id.itemFR);
        itemBR=findViewById(R.id.itemBR);
        itemTimeEnd=findViewById(R.id.itemTimeEnd);
        buildChart(pAId);

        selectResultresponse();

        setUpNavigation();
    }

    private void buildChart(int pAId) {
        HashMap<String, Float> resultlist = new HashMap<>();
        try (ExampleSaverActivity selector = new ExampleSaverActivity(this)) {
            db = selector.getWritableDatabase();
            resultlist = selector.chartSelector(pAId);
        }

        BarChart chart = (BarChart) findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>(resultlist.keySet());
        for (int i = 0; i < labels.size(); i++) {
            entries.add(new BarEntry(i, resultlist.get(labels.get(i))));
        }
        BarDataSet barDataSet = new BarDataSet(entries, "امتیاز آزمون");
        barDataSet.setColor(Color.BLUE); // تنظیم رنگ ستون‌ها
        barDataSet.setValueTextColor(Color.RED); // تنظیم رنگ متن مقادیر

        BarData barData = new BarData(barDataSet);
        chart.setData(barData);

        // تنظیم لیبل‌های محور X
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f); // تنظیم گرانولاریتی محور X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // تنظیم موقعیت محور X
        xAxis.setDrawGridLines(false); // غیرفعال کردن خطوط کمکی محور X

        // تنظیم محور Y سمت چپ
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false); // غیرفعال کردن خطوط کمکی محور Y سمت چپ
        leftAxis.setAxisMinimum(0f); // تنظیم حداقل مقدار محور Y به 0
        leftAxis.setAxisMaximum(100f); // تنظیم حداکثر مقدار محور Y به 100
        leftAxis.setLabelCount(11, true); // تنظیم تعداد لیبل‌های محور Y به 11 (0 تا 100، ده تا ده تا)

        // تنظیم محور Y سمت راست
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false); // غیرفعال کردن خطوط کمکی محور Y سمت راست
        rightAxis.setAxisMinimum(0f); // تنظیم حداقل مقدار محور Y به 0
        rightAxis.setAxisMaximum(100f); // تنظیم حداکثر مقدار محور Y به 100
        rightAxis.setLabelCount(11, true); // تنظیم تعداد لیبل‌های محور Y به 11 (0 تا 100، ده تا ده تا)

        chart.setTouchEnabled(true); // فعال کردن تعاملات لمسی با نمودار
        chart.invalidate(); // رسم مجدد نمودار
    }
    @SuppressLint("SetTextI18n")
    private void selectResultresponse() {
        try (ExampleSaverActivity selector = new ExampleSaverActivity(this)) {
            db=selector.getWritableDatabase();
            resultlist=selector.selectNumResult(pAId);
            double DCountNumE=resultlist.get("countNumE");
            int ICountNumE = (int) DCountNumE;
            double DCountTR=resultlist.get("countNumTR");
            int ICountTR = (int) DCountTR;
            double DCountFR=resultlist.get("countNumFR");
            int ICountFR = (int) DCountFR;
            double DCountBR=resultlist.get("countNumBR");
            int ICountBR = (int) DCountBR;
            double DCountTime=resultlist.get("countNumTime");
            int ICountTime = (int) DCountTime;

            double PercentageTR=resultlist.get("countPercentageTR");
            BigDecimal bdTR = new BigDecimal(PercentageTR);
            BigDecimal roundedTR = bdTR.setScale(1, RoundingMode.HALF_UP);
            double PercentageFR=resultlist.get("countPercentageFR");
            BigDecimal bdFR = new BigDecimal(PercentageFR);
            BigDecimal roundedFR = bdFR.setScale(1, RoundingMode.HALF_UP);
            itemNumE.setText(String.valueOf(ICountNumE)+" آزمون داده شده");
            itemTR.setText(String.valueOf(ICountTR)+" سوال   صحیح");
            itemFR.setText(String.valueOf(ICountFR)+" سوال   غلط");
            itemBR.setText(String.valueOf(ICountBR)+" سوال پاسخ داده نشده");
            itemTimeEnd.setText(String.valueOf(ICountTime)+"دقیقه زمان صرف شده");
            itemPercentageTR.setText(String.valueOf(roundedTR)+" درصد پاسخ درست");
            itemPercentageFR.setText(String.valueOf(roundedFR)+" درصد پاسخ غلط");
        }
    }
    private void setUpNavigation() {
        try {
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            try {
                field.setAccessible(true);
                field.set(null, myFont);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==itemShowHistoryExams.getId()){
            Intent intent = new Intent(ResultExamActivity.this,ShowHistoryExamActivity.class);
            startActivity(intent);
            finish();

        }
    }
        @Override
        public void onBackPressed() {
            Intent intent2;
            intent2=new Intent(ResultExamActivity.this,AccountActivity.class);

            startActivity(intent2);
            finish();
            super.onBackPressed();
        }

}
