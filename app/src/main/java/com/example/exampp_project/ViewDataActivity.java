package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
//    List<QuestionsFieldViewActivity> questionsFieldViewActivities;
    AdapterlistActivity adapter;
//    Adapter adapter;
    ExampleSaverActivity dbhelper;
//    ListView listView;
    AppCompatTextView examName;
    Button btnSave;
    List<ExampleFileds> exampleList=new ArrayList<>();
    Intent i;
    int AID=-1;
    String ename;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);
        Log.i("xcv","aaa");
        i=getIntent();
        if(i.hasExtra(ExampleFileds.KEY_AID)){
            AID=i.getIntExtra(ExampleFileds.KEY_AID,-1);
            ename=i.getStringExtra(ExampleFileds.KEY_NAME);
        }
        recyclerView=findViewById(R.id.recyclerview);
        dbhelper=new ExampleSaverActivity(this);
//        listView=findViewById(R.id.listview);
        examName=findViewById(R.id.examName);
        btnSave=findViewById(R.id.btnsave);
        btnSave.setOnClickListener(this);

        showExamName();
        prepareData();
        showData();
    }

    @SuppressLint("SetTextI18n")
    private void showExamName() {
        if(AID!=-1){
            examName.setText("لیست سوالات آزمون " + ename);

        }else {
            SQLiteDatabase db = dbhelper.getWritableDatabase();

            String Ename = dbhelper.getExamName();
            Log.i("namee2: ", Ename);
            examName.setText("لیست سوالات آزمون " + Ename);
        }

    }

    private void prepareData(){

        if(exampleList==null){
            exampleList=new ArrayList<>();
        }else{
            exampleList.clear();
        }
        exampleList=dbhelper.getAllQuestions(AID,ename);
        Log.i("xcv",exampleList.get(0).getQuestion());


    }
    private void showData(){
        boolean exam=false;
        adapter=new AdapterlistActivity(exampleList,false,ViewDataActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==btnSave.getId()){
            Toast.makeText(getApplicationContext(), "آزمون با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();

            Intent intent2=new Intent(ViewDataActivity.this,MainActivity.class);
            startActivity(intent2);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent2;
        if(AID==-1){

            intent2=new Intent(ViewDataActivity.this,QuestionsActivity.class);
        }else{
            intent2=new Intent(ViewDataActivity.this,MainActivity.class);

        }
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }
}
