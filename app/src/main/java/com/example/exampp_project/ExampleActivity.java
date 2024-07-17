package com.example.exampp_project;
import android.os.Bundle;



import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Calendar;
import java.util.HashMap;

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonNext;
    DatePickerDialog datePickerDialog;

    AppCompatEditText startDate, name,examTime,expireDate,numQ;
    String Sname,SexpireTime,SstartTime,SexamTime,SnumQ;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        buttonNext=findViewById(R.id.NextDataButton);
        name=findViewById(R.id.exampleName);
        examTime=findViewById(R.id.examTime);
        startDate=findViewById(R.id.startDate);
        startDate.setOnClickListener(this);
        expireDate=findViewById(R.id.expireDate);
        expireDate.setOnClickListener(this);

        numQ=findViewById(R.id.numQ);


        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==startDate.getId() || v.getId()==expireDate.getId()){
            Log.i("800","clicked startDate");
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(ExampleActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String selectedDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                            if(v.getId()==startDate.getId()){
                                startDate.setText(selectedDate);
                            }else expireDate.setText(selectedDate);
                        }
                    }, year, month, day);

            datePickerDialog.show();
        }
        if(v.getId()==buttonNext.getId()){
            HashMap<String, String> fieldExam = new HashMap<>();
            Sname= String.valueOf(name.getText());
            SstartTime= String.valueOf(startDate.getText());
            SexpireTime= String.valueOf(expireDate.getText());
            SexamTime= String.valueOf(examTime.getText());
            SnumQ=String.valueOf(numQ.getText());
            fieldExam.put("Sname",Sname);
            fieldExam.put("SstartTime",SstartTime);
            fieldExam.put("SexpireTime",SexpireTime);
            fieldExam.put("SexamTime",SexamTime);
            fieldExam.put("InumQ", String.valueOf(SnumQ));
            boolean validation= Utils.validationField(fieldExam,this);
            boolean validationStartDate= Utils.validationDate(SstartTime,this);
            boolean validationExpireDate= Utils.validationDate(SexpireTime,this);

            if(validation && validationStartDate && validationExpireDate){
//                    String StartDateType=SstartTime.replace("/","-");
//                    String ExpireDateType=SstartTime.replace("/","-");
                    try (ExampleSaverActivity Eadd = new ExampleSaverActivity(this)) {
                        SQLiteDatabase db = Eadd.getWritableDatabase();
                        Intent intent2 = getIntent();
                        Integer AId = intent2.getIntExtra("AuthorList", -1);
                        boolean ExistRec= Eadd.hasADD(Sname,AId);
                        if(ExistRec){
                            AppCompatTextView ERRname=findViewById(R.id.EREname);
                            ERRname.setVisibility(View.VISIBLE);
                            ERRname.setText("قبلا آزمونی با این نام ذخیره کرده اید");
                            Log.i("azera","ERR");
                        }else {
                            Eadd.insertExampleBaseFields(SnumQ, Sname, SstartTime, SexpireTime, SexamTime, AId);
                            Log.i("azera","NOERR");
                            Intent i = new Intent(ExampleActivity.this, QuestionsActivity.class);
                            int InumQ= Integer.parseInt(SnumQ);
                            i.putExtra("numQ",InumQ);
                            startActivity(i);
                            finish();
                        }
                    }

            }




        }
    }
    @Override
    public void onBackPressed() {
        Intent intent2=new Intent(ExampleActivity.this,MainActivity.class);
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }
}
