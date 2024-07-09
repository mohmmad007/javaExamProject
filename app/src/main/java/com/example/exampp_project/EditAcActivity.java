package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;

public class EditAcActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnSave;
    EditText authorName,authorPass;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editac);
        authorName=findViewById(R.id.authorName);
        authorPass=findViewById(R.id.authorPass);
        btnSave=findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(this);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {
        if(v.getId()==btnSave.getId()){
            if (!String.valueOf(authorName.getText()).isEmpty()&&
                    !String.valueOf(authorPass.getText()).isEmpty()) {
                String Aname=String.valueOf(authorName.getText()).trim();
                String Apass=String.valueOf(authorPass.getText()).trim();

                try (ExampleSaverActivity author = new ExampleSaverActivity(this)) {
                    SQLiteDatabase db = author.getWritableDatabase();
                    List<ExampleFileds> authorList=new ArrayList<>();
                    SharedPreferences pref;
                    pref = getSharedPreferences("Author", MODE_PRIVATE);
                    Integer pAId =pref.getInt("authorId",-1);

                    boolean isedit=author.editac(Aname,Apass,pAId);
                if(isedit){
                    Log.i("aserz","sasa");
                    Toast.makeText(getApplicationContext(),"ویرایش انجام شد",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(EditAcActivity.this, AccountActivity.class);
                    startActivity(intent2);
                    finish();
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=pref.edit();
                    editor.putString("authorName",Aname);
                    editor.putInt("authorId", Integer.parseInt(Apass));
                    editor.apply();
                }
                }


                }else{
                Toast.makeText(getApplicationContext(),"لطفا تمام فیلد هارا پر کنید",Toast.LENGTH_SHORT).show();

            }
            }

    }
}
