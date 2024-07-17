package com.example.exampp_project;

import static com.example.exampp_project.R.drawable.baseline_check_circle_24;
import static com.example.exampp_project.R.drawable.baseline_close_24;
import static com.example.exampp_project.R.drawable.baseline_question_mark_24;
import static com.example.exampp_project.R.drawable.baseline_remove_circle_24;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailExamHistoryActivity extends AppCompatActivity {
    GridLayout gridLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_exam_history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        // در داخل یک Activity یا Fragment
        AppCompatTextView baseDetailItemNumQ = findViewById(R.id.baseDetailItemNumQ);
        AppCompatTextView baseDetailItemTime = findViewById(R.id.baseDetailItemTime);
        AppCompatTextView baseDetailItemAuthor = findViewById(R.id.baseDetailItemAuthor);
        AppCompatTextView baseDetailItemTR = findViewById(R.id.baseDetailItemTR);
        AppCompatTextView baseDetailItemFR = findViewById(R.id.baseDetailItemFR);
        AppCompatTextView baseDetailItemBR = findViewById(R.id.baseDetailItemBR);
        AppCompatTextView baseDetailItemTimeEnd = findViewById(R.id.baseDetailItemTimeEnd);
        Log.i("9log00","shower");

        Intent getter=getIntent();
        int numQ=getter.getIntExtra("numQ",-1);
        int Time=getter.getIntExtra("Time",-1);
        String Author=getter.getStringExtra("author");
        int TR=getter.getIntExtra("TR",-1);
        int FR=getter.getIntExtra("FR",-1);
        int BR=getter.getIntExtra("BR",-1);
        int TimeEnd=getter.getIntExtra("TimeEnd",-1);
//        String validationQ=getter.getStringExtra("validationQ");
        Log.i("9log00", String.valueOf(numQ));
//        String[] numbers = validationQ.replace("$", "").replace("[", "").replace("]", "").split(","); // استفاده از replace برای جایگزینی        List<Integer> integerList = new ArrayList<>();
//        List<Integer> integerList = new ArrayList<>();
//        for (String num : numbers) {
//                Log.i("9log00", num);
//              integerList.add(Integer.parseInt(num)); // تبدیل به عدد و اضافه کردن به لیست
//        }
//        generator(integerList);
//        Log.i("9log00", String.valueOf(Time));
//        Log.i("1log000", integerList.toString());
//        Log.i("9log00", String.valueOf(Author));
//        Log.i("9log00", String.valueOf(TR));
//        Log.i("9log00", String.valueOf(FR));
//        Log.i("9log00", String.valueOf(BR));
//        Log.i("9log00", String.valueOf(TimeEnd));
        baseDetailItemNumQ.setText("تعداد سوال: "+numQ);
        baseDetailItemTime.setText("زمان: "+Time);
        baseDetailItemAuthor.setText("نویسنده: "+Author);
        baseDetailItemTR.setText("پاسخ صحیح: "+TR);
        baseDetailItemFR.setText("پاسخ غلط: "+FR);
        baseDetailItemBR.setText("پاسخ داده نشده: "+BR);
        baseDetailItemTimeEnd.setText("زمان صرف شده:"+TimeEnd);

    }
//
//    private void generator(List<Integer> integerList) {
//        gridLayout=findViewById(R.id.baseDetailGridLayout);
//        double DCountRow = Math.ceil((double) integerList.size() / 5);
//        int ICountRow = (int) DCountRow;
//        Log.i("1log00", "ds: "+String.valueOf(ICountRow));
//        gridLayout.setRowCount(ICountRow);
//        gridLayout.setColumnCount(5);
//
//        for (int i = 0; i < integerList.size(); i++) {
//            TextView textView = new TextView(this);
////            textView.setText(String.valueOf(integerList.get(i)));
//            textView.setTextColor(Color.WHITE);
//            if(integerList.get(i)==0){
//
//                textView.setBackground(ContextCompat.getDrawable(this, baseline_close_24));
//            } else if (integerList.get(i)==1) {
//                textView.setBackground(ContextCompat.getDrawable(this,baseline_check_circle_24));
//
//            }else{
//                textView.setBackground(ContextCompat.getDrawable(this, baseline_remove_circle_24));
//
//            }
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//            params.setMargins(30, 50, 30, 50); // تنظیم مارجین
//
////            params.rowSpec = GridLayout.spec(1/5);
////            params.columnSpec = GridLayout.spec(1%5);
//
//
//            gridLayout.addView(textView, params);
//        }
//
//    }

    @Override
    public void onBackPressed() {
        Intent intent2;

            intent2=new Intent(DetailExamHistoryActivity.this,ShowHistoryExamActivity.class);

        startActivity(intent2);
        finish();
        super.onBackPressed();
    }
}