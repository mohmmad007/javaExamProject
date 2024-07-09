package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonGenQ,btnupdate;
    EditText question,TR,FR1,FR2,FR3;
    long QIdE;
//    String Sname,SexpireTime,SstartTime,SexamTime;
    boolean editable=false;
    int numQ;
    long c= -1;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qusetions);
        Intent intentNumQ=getIntent();
        numQ=intentNumQ.getIntExtra("numQ",-1);
        question=findViewById(R.id.question);
        TR=findViewById(R.id.TR);
        FR1=findViewById(R.id.FR1);

        FR2=findViewById(R.id.FR2);
        FR3=findViewById(R.id.FR3);
        Intent intent=getIntent();
        if(intent.hasExtra(ExampleFileds.KEY_QUSET)) {
            editable=true;
//            Log.i("intent",intent.getStringExtra(ExampleFileds.KEY_QUSET));
            QIdE = intent.getLongExtra(ExampleFileds.KEY_IDq,0);
            Log.i("database","dada2: "+intent.getStringExtra(ExampleFileds.KEY_IDq));
            String questionE = intent.getStringExtra(ExampleFileds.KEY_QUSET);
            String TRE = intent.getStringExtra(ExampleFileds.KEY_TRESPONSE);
            String FR1E = intent.getStringExtra(ExampleFileds.KEY_FRESPONSE1);
            String FR2E = intent.getStringExtra(ExampleFileds.KEY_FRESPONSE2);
            String FR3E = intent.getStringExtra(ExampleFileds.KEY_FRESPONSE3);
            question.setText(questionE);
            TR.setText(TRE);
            FR1.setText(FR1E);
            FR2.setText(FR2E);
            FR3.setText(FR3E);
        }
//        SstartTime = getIntent().getStringExtra("startDate");
//        Sname = getIntent().getStringExtra("name");
//        SexpireTime = getIntent().getStringExtra("expireDate");
//        SexamTime = getIntent().getStringExtra("examTime");

        btnupdate=findViewById(R.id.btnupdate);
        btnupdate.setText("نمایش سوالات و ذخیره("+numQ+" سوال باقی مانده است)");
        btnupdate.setOnClickListener(this);
        buttonGenQ=findViewById(R.id.btnGenQ);
        if(editable){
            buttonGenQ.setText("EDIT");
        }
        buttonGenQ.setOnClickListener(this);
        Log.i("em","bbb");


    }



    @Override
    public void onClick(View v) {
        if (v.getId()==buttonGenQ.getId()){
            if(editable){
                Button btnupdate=findViewById(R.id.btnupdate);
                btnupdate.setVisibility(View.GONE);
                GenQ genQ = new GenQ(this,QIdE, question, TR, FR1, FR2, FR3,-1,false);
                Log.i("database", "c.c.:"+String.valueOf(question.getText()));

            }else {
                numQ--;
//                c++;
                String ideR = ExampleFileds.KEY_IDeR;

                GenQ genQ = new GenQ(this, c, question, TR, FR1, FR2, FR3,numQ,true);
//            genQ.genQ((ViewGroup) findViewById(R.id.activity_question));
                Log.i("xcv3", ExampleFileds.KEY_IDeR);
            }
        }

        if(v.getId()==btnupdate.getId()){
            Intent i = new Intent(QuestionsActivity.this,ViewDataActivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "آزمون ذخیره شد", Toast.LENGTH_SHORT).show();
        Intent intent2=new Intent(QuestionsActivity.this,MainActivity.class);
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }
}
