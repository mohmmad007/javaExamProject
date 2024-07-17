package com.example.exampp_project;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class RankActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ExampleSaverActivity dbhelper;
    AdapterRanking adapter;
    List<ResponseField> resRankList = new ArrayList<>();
    int IdCounter, countExam;
    List<ResponseField> examList = new ArrayList<>();
    AppCompatButton btnForwardRank, btnBackRank;
    AppCompatButton selectedButton = null; // متغیر برای نگهداری دکمه‌ی انتخاب‌شده

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        Log.i("RankSQL", "startAc");

        Intent intent = getIntent();
        IdCounter = intent.getIntExtra("IdECounter", -1);
        Log.i("swd", String.valueOf(IdCounter));
        countExam = intent.getIntExtra("countExam", -1);
        Log.i("RankSQL", String.valueOf(IdCounter));

        btnForwardRank = findViewById(R.id.btnForwardRank);
        btnBackRank = findViewById(R.id.btnBackRank);
        btnForwardRank.setOnClickListener(this);
        btnBackRank.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_Rank);
        dbhelper = new ExampleSaverActivity(this);
        ExamNameIndexing(resRankList, IdCounter);

        prepareData(IdCounter);
        showData();
    }

    private void prepareData(int idE) {
        if (resRankList == null) {
            resRankList = new ArrayList<>();
        } else {
            resRankList.clear();
        }
        resRankList = dbhelper.getUserRanking(idE);
        Log.i("RankSQL", "fin");
    }

    private void showData() {
        adapter = new AdapterRanking(resRankList, RankActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void ExamNameIndexing(List<ResponseField> resRankList, int IdE) {
        HorizontalScrollView horizontalScrollView = findViewById(R.id.nameScroler);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ExampleSaverActivity dbHelper = new ExampleSaverActivity(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        examList = dbHelper.getAllExam();

        for (int i = 0; i < examList.size(); i++) {
            AppCompatButton buttoneName = new AppCompatButton(this);
            buttoneName.setText(examList.get(i).getName());
            buttoneName.setTextSize(13);
            if (IdE == i+1) {
                buttoneName.setBackgroundResource(R.drawable.roundexamselectedname);
                selectedButton = buttoneName; // دکمه‌ی انتخاب‌شده را ذخیره کنید
            } else {
                buttoneName.setBackgroundResource(R.drawable.roundexamname);
            }
            buttoneName.setId(i + 1);

            int idco = examList.get(i).getId();
            buttoneName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int icd = buttoneName.getId();
                    prepareData(icd);
                    showData();
                    showExams(icd);
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            buttoneName.setLayoutParams(params);

            linearLayout.addView(buttoneName);
        }

        horizontalScrollView.addView(linearLayout);
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(RankActivity.this, MainActivity.class);
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnBackRank.getId()) {
            if (countExam <= IdCounter) {
                Toast.makeText(this, "رتبه بندی دیگری وجود ندارد", Toast.LENGTH_SHORT).show();
            } else {
                IdCounter++;
                prepareData(IdCounter);
                showData();
                showExams(IdCounter);
            }
        }
        if (v.getId() == btnForwardRank.getId()) {
            if (IdCounter <= 1) {
                Toast.makeText(this, "رتبه بندی دیگری وجود ندارد", Toast.LENGTH_SHORT).show();
            } else {
                IdCounter--;
                prepareData(IdCounter);
                showData();
                showExams(IdCounter);
            }
        }
    }
    private void showExams(int icd) {
        for (int i = 1; i <= examList.size(); i++) {
            AppCompatButton button = findViewById(i);
            if (icd == i) {
                button.setBackgroundResource(R.drawable.roundexamselectedname);
                selectedButton = button; // دکمه‌ی انتخاب‌شده را ذخیره کنید
            } else {
                button.setBackgroundResource(R.drawable.roundexamname);
            }
        }
    }
}
