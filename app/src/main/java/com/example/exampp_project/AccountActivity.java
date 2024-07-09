package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.lang.reflect.Field;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton acdel,aced,examCompel;
    @SuppressLint("MissingInflatedId")
    SharedPreferences pref;
    String pAname;
    Integer pAId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        pref = getSharedPreferences("Author", MODE_PRIVATE);
        pAId =pref.getInt("authorId",-1);
        pAname=pref.getString("authorName","NOT_FOUND");
        setUpNavigation();

        acdel=findViewById(R.id.acdel);
        acdel.setOnClickListener(this);

        aced=findViewById(R.id.acedit);
        aced.setOnClickListener(this);

        examCompel=findViewById(R.id.examComplte);
        examCompel.setOnClickListener(this);

    }
    private void setUpNavigation() {
        Typeface myFont;
        myFont = Typeface.createFromAsset(getAssets(), "fonts/FontsFree-Net-ir_sans.ttf");

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
        if(v.getId()==aced.getId()) {
            Intent intent2 = new Intent(AccountActivity.this, EditAcActivity.class);
            startActivity(intent2);
            finish();
        }if(v.getId()==acdel.getId()){
            Utils.acDelete(pAname,pAId,this);
        }if(v.getId()==examCompel.getId()){
            Intent intent2 = new Intent(AccountActivity.this, ResultExamActivity.class);
            startActivity(intent2);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent2=new Intent(AccountActivity.this,MainActivity.class);
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }

}
