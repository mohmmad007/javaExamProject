package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

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

        selectResultresponse();

        setUpNavigation();
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
