package com.example.exampp_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowexamActivity extends AppCompatActivity{
    RecyclerView recyclerView;
//    List<QuestionsFieldViewActivity> questionsFieldViewActivities;
    AdapterShowExam adapter;
//    Adapter adapter;
    ExampleSaverActivity dbhelper;
//    ListView listView;
    AppCompatTextView examName;
    Button btnSave;
    List<ExampleFileds> exampleList=new ArrayList<>();
    Intent i;
    int AID=-1;
    String ename;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        i=getIntent();
        if(i.hasExtra(ExampleFileds.KEY_AID)){
            AID=i.getIntExtra(ExampleFileds.KEY_AID,-1);
            ename=i.getStringExtra(ExampleFileds.KEY_NAME);
        }
        recyclerView=findViewById(R.id.examrec);
        dbhelper=new ExampleSaverActivity(this);
//        listView=findViewById(R.id.listview);
        examName=findViewById(R.id.examName);
//
        Log.i("before_pre","sdsadsad");
        prepareData();
        Log.i("after_pre","sdsadsad");
        showExams();
        Log.i("after_show","sdsadsad");

    }


    private void prepareData(){
        if(exampleList==null){
            exampleList=new ArrayList<>();
        }else{
            exampleList.clear();
        }

            LocalDate today = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                today = LocalDate.now();
                String inputDateStr = String.valueOf(today);
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date inputDate = inputFormat.parse(inputDateStr);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String outputDateStr = outputFormat.format(inputDate);
                    Log.i("today", String.valueOf(today));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    exampleList = dbhelper.getActiveExams(today);
                    Log.i("xcv", exampleList.get(0).getName());
                    Log.i("xcv", "a");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
    }
    private void showExams(){
        adapter=new AdapterShowExam(exampleList, ShowexamActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        Log.i("SALAM","KHODAFEZ");
    }
}
