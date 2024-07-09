package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GenQ {
    private Activity activity;
    long IdQ;
    int numF=0;
    EditText question;
    EditText TR;
    EditText FR1;
    EditText FR2;
    EditText FR3;

    public GenQ(Activity activity, long IdQ, EditText question, EditText TR, EditText FR1, EditText FR2, EditText FR3 ,int numQ,boolean I) {
        Log.i("database","dada1: "+IdQ);

        this.activity = activity;
        this.IdQ=IdQ;
        this.question=question;
        this.TR=TR;
        this.FR1=FR1;
        this.FR2=FR2;
        this.FR3=FR3;
        String Squestion=String.valueOf(question.getText()).trim();
        String STR=String.valueOf(TR.getText()).trim();
        String SFR2=String.valueOf(FR1.getText()).trim();
        String SFR1=String.valueOf(FR2.getText()).trim();
        String SFR3=String.valueOf(FR3.getText()).trim();
        Log.i("database", "start insert");


        if(!Squestion.equals("")&&!STR.equals("")&&!SFR1.equals("")
                &&!SFR3.equals("")&&!SFR2.equals("")) {
            Log.i("xcy",Squestion);
            if(I) {
                inserting(activity, question, TR, FR1, FR2, FR3,numQ);

            }else {

                try (ExampleSaverActivity Eadd = new ExampleSaverActivity(activity)) {
                SQLiteDatabase db = Eadd.getWritableDatabase();
                Eadd.updateExampleFields(IdQ,Squestion, STR, SFR1, SFR2, SFR3);
                Log.i("database", "c.c.:2"+String.valueOf(question.getText()));

                    Toast.makeText(activity, "آپدیت شد", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(activity,ViewDataActivity.class);
                activity.startActivity(i);

                }

            }
        }else{
            Toast.makeText(activity, " ابتدا تمامی فیلد هارا پر کنید ", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void inserting(Activity activity, EditText question, EditText TR, EditText FR1, EditText FR2, EditText FR3,int numQ) {
        try (ExampleSaverActivity rowadd = new ExampleSaverActivity(activity)) {
            SQLiteDatabase db=rowadd.getWritableDatabase();

            rowadd.insertExampleFileds(question.getText(), TR.getText(), FR1.getText(), FR2.getText(), FR3.getText());
        }

        Button btnupdate=activity.findViewById(R.id.btnupdate);
        Button btnplus=activity.findViewById(R.id.btnGenQ);


        btnupdate.setText("نمایش سوالات و ذخیره("+numQ+" سوال باقی مانده است)");
        if(numQ==0){
            btnupdate.setEnabled(true);
            btnupdate.setText("نمایش سوالات و ذخیره");
            btnplus.setEnabled(false);
        }
        question();
        Tresponse();
        Fresponse();
    }

    public void question(){

        question.setText("");
    }
    public void Tresponse(){
        TR.setText("");

    }
    public void Fresponse(){
        FR1.setText("");
        FR2.setText("");
        FR3.setText("");
    }

}
