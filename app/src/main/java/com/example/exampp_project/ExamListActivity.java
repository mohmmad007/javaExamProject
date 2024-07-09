package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExamListActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<ExampleFileds> authorList=new ArrayList<>();
    ExampleSaverActivity dbhelper;
    AdapterlistActivity adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
        Log.i("bbv","e1");
        dbhelper=new ExampleSaverActivity(this);

        recyclerView=findViewById(R.id.recyclerviewExams);
        Intent i=getIntent();
        Integer AId= i.getIntExtra("AuthorList",-1);
        Log.i("bbv","e2");

        prepareData(AId);
        showData();

    }
    private void prepareData(Integer Aid){
        if(authorList==null){
            authorList=new ArrayList<>();
        }else{

            authorList.clear();
        }
        Log.i("bbv","e2.1:"+Aid);
        authorList=dbhelper.getAuthorListExams(Aid);
        Log.i("bbv","e2.1.1");

//        Log.i("xcv",authorList.get(0).getQuestion());


    }

    private void showData(){
        Log.i("bbv","e3");

        boolean exam=true;
        adapter=new AdapterlistActivity(authorList,exam,ExamListActivity.this);
        Log.i("bbv","e4");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        Log.i("bbv","e5");

    }
    @Override
    public void onBackPressed() {
        Intent intent2=new Intent(ExamListActivity.this,MainActivity.class);
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }


}

