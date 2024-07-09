package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
//    SharedPreferences prefReg;
    Button btnlogin,btnreg;
    EditText authorName,authorPass;
    SharedPreferences pref;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_login);
//        prefReg=getSharedPreferences("listUsers",MODE_PRIVATE);
        btnlogin=findViewById(R.id.buttonLogin);
        pref=getSharedPreferences("Author",MODE_PRIVATE);
        btnreg=findViewById(R.id.buttonRegister);
        authorName=findViewById(R.id.authorName);
        authorPass=findViewById(R.id.authorPass);
        btnreg.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!String.valueOf(authorName.getText()).isEmpty()&&
                !String.valueOf(authorPass.getText()).isEmpty()){
            String Aname=String.valueOf(authorName.getText()).trim();
            String Apass=String.valueOf(authorPass.getText()).trim();

            try (ExampleSaverActivity author = new ExampleSaverActivity(this)) {
                SQLiteDatabase db = author.getWritableDatabase();
                boolean isauthor = author.ISAuthor(Aname, Apass);

                if (v.getId() == btnlogin.getId()) {
                    if(isauthor){
                        Log.i("xcy", "loger");

                        List<ExampleFileds> authorList=new ArrayList<>();
                        Log.i("xcy", "loger1");

                        authorList=author.getuser(Aname,Apass);
//                        List<String> authorList;
//                        authorList=author.getuser(Aname,Apass);
                        if(authorList==null){
                            Toast.makeText(getApplicationContext(), "رمز ورود نادرست است", Toast.LENGTH_SHORT).show();

                        }else {
                            Object object=authorList.get(0).getAId();
                            Log.i("xcvz","asa: "+ object.getClass().getSimpleName());
                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=pref.edit();
                            editor.putString("authorName",authorList.get(0).getAname());
                            editor.putInt("authorId", authorList.get(0).getAId());
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "خوش آمدید", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("AuthorList", authorList.get(0).getAId());
                            startActivity(i);
                            finish();

                            Log.i("xcv", String.valueOf(authorList.get(0).getAId()));
                            Log.i("xcv", authorList.get(0).getAname());
                            Log.i("xcv", String.valueOf(authorList.get(0).getApassword()));
                        }


                    }else {
                        Toast.makeText(getApplicationContext(), "چنین نام کاربری وجود ندارد", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "log", Toast.LENGTH_SHORT).show();
                }
                if (v.getId() == btnreg.getId()) {
                    if (isauthor) {
                        Toast.makeText(getApplicationContext(), "این نام کاربری وجود دارد ", Toast.LENGTH_SHORT).show();
                    } else {
                            author.insertAuthor(Aname, Apass);
                        List<ExampleFileds> authorList=new ArrayList<>();
                        authorList=author.getuser(Aname,Apass);
                        Log.i("xcvz", String.valueOf(authorList.get(0).getAId()));
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=pref.edit();
                        editor.putString("authorName",authorList.get(0).getAname());
                        editor.putInt("authorId", authorList.get(0).getAId());
                        editor.apply();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("AuthorList", authorList.get(0).getAId());
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), "ثبت نام با موفقیت انجام شد ", Toast.LENGTH_SHORT).show();

                    }

                }
                pref = getSharedPreferences("Author", MODE_PRIVATE);




            }

        }else{
            Toast.makeText(getApplicationContext(),"لطفا تمام فیلد هارا پر کنید",Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent2=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent2);
        finish();
        super.onBackPressed();
    }
}
