package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Typeface myFont;
    ImageView imageViewPlusExample;
    ImageView imageMenu;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    SharedPreferences pref;
    AppCompatButton btnRanking;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        myFont = Typeface.createFromAsset(getAssets(), "fonts/FontsFree-Net-ir_sans.ttf");
        setUpNavigation();
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ExampleSaverActivity e=new ExampleSaverActivity(this);
        SQLiteDatabase db=e.getWritableDatabase();
        e.a();
//        Log.i("1log000", String.valueOf(true));
        changeStatusBarColor();
        btnRanking=findViewById(R.id.btnRanking);
        btnRanking.setOnClickListener(this);
        pref = getSharedPreferences("Author", MODE_PRIVATE);
        Integer pAId =pref.getInt("authorId",-1);
        String Aname=pref.getString("authorName","NOT_FOUND");
        Log.i("700", String.valueOf(pref.getInt("authorId",-1)));
        if (pAId!=-1){
            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            MenuItem loginItem = ((Menu) menu).findItem(R.id.login);
            loginItem.setTitle("خروج");
            Log.i("menuItem","assas");
            if(!Aname.equals("NOT_FOUND")){
                Log.i("aasa","start2:"+Aname);
                TextView textView=findViewById(R.id.authorText);
                textView.setVisibility(View.VISIBLE);
                textView.setText(" خوش اومدی "+Aname);
            }
               }else{
            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            MenuItem loginItem = ((Menu) menu).findItem(R.id.login);
            loginItem.setTitle("ورود");
            TextView textView=findViewById(R.id.authorText);
            textView.setVisibility(View.GONE);
            textView.setText("");
            menu.removeItem(R.id.account);


//            loginItem.setTitle("خروج");

        }
        menunav();




        imageViewPlusExample=findViewById(R.id.imagePlus);
        imageViewPlusExample.setOnClickListener(this);



    }

    private void menunav() {
        imageMenu=findViewById(R.id.img_toolbar_menu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        imageMenu.setOnClickListener(this);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.login){
                    pref = getSharedPreferences("Author", MODE_PRIVATE);
                    Integer pAId =pref.getInt("authorId",-1);
                    if (pAId == -1) {
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("authorId");
                        editor.remove("authorName");
                        editor.apply();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
                if(id==R.id.yourExamMenu){
                    pref = getSharedPreferences("Author", MODE_PRIVATE);
                    Integer pAId =pref.getInt("authorId",-1);
                    if (pAId != -1) {
                        Intent intent2=new Intent(MainActivity.this,ExamListActivity.class);
                        Log.i("azera999", String.valueOf(pref.getInt("authorId",-1)));
                        intent2.putExtra("AuthorList",pAId);
                        startActivity(intent2);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "ابتدا وارد حساب کاربری خود بشوید", Toast.LENGTH_SHORT).show();
                    }
                }if(id==R.id.Exams){
                    Intent intent = new Intent(MainActivity.this,ShowexamActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(id==R.id.account){
                    Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                    startActivity(intent);
                    finish();
                }

                    return false;
            }

        });
    }
    private void changeStatusBarColor(){
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        ((Window) window).setStatusBarColor(Color.TRANSPARENT);
    }

    private void setUpNavigation() {
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
    @SuppressLint("RtlHardcoded")
    @Override
    public void onClick(View v) {

        if (v.getId()==imageViewPlusExample.getId()){
            pref = getSharedPreferences("Author", MODE_PRIVATE);
            Integer pAId =pref.getInt("authorId",-1);
            if(pAId!=-1){

                Intent i = new Intent(MainActivity.this,ExampleActivity.class);
                i.putExtra("AuthorList",pAId);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId()==imageMenu.getId()){
            Toast.makeText(getApplicationContext(), "a", Toast.LENGTH_SHORT).show();
            if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(Gravity.LEFT);

            }else{
                drawerLayout.openDrawer(Gravity.LEFT);

            }


        }
        if(v.getId()==btnRanking.getId()){
            ExampleSaverActivity dbHelper=new ExampleSaverActivity(this);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            List<ResponseField> examList=dbHelper.getAllExam();
            Intent i = new Intent(MainActivity.this,RankActivity.class);
            i.putExtra("IdECounter",1);
            i.putExtra("countExam",examList.size());
            startActivity(i);
            finish();


        }

    }
}