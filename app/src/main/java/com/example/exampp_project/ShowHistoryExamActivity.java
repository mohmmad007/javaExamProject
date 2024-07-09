package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

public class ShowHistoryExamActivity extends AppCompatActivity {
    AdapterShowHistory adapter;
    RecyclerView recyclerView;
    List<ResponseField> responseList=new ArrayList<>();
    ExampleSaverActivity dbhelper;
    SharedPreferences pref;
    Integer pAId;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history_exam);
        recyclerView=findViewById(R.id.recyclerShowHistory);
        dbhelper=new ExampleSaverActivity(this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        pref = getSharedPreferences("Author", MODE_PRIVATE);
        pAId =pref.getInt("authorId",-1);

        prepareData();
        showData();

    }

    private void prepareData(){

        if(responseList==null){
            responseList=new ArrayList<>();
        }else{
            responseList.clear();
        }

        Log.i("700","asa");

        responseList=dbhelper.getExamHistory(pAId);
        Log.i("700",responseList.get(0).getName());
        Log.i("700",responseList.get(1).getName());
        Log.i("700",responseList.get(2).getName());


    }

    private void showData(){
        adapter=new AdapterShowHistory(responseList,ShowHistoryExamActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent2;
            intent2=new Intent(ShowHistoryExamActivity.this,ResultExamActivity.class);

        startActivity(intent2);
        finish();
        super.onBackPressed();
    }

}
