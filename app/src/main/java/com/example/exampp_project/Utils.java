package com.example.exampp_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class Utils {
    public static void acDelete(String Aname, int Apass, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("لطفا نام خود را وارد کنید:");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("برای ادامه کار رمز خود را وارد کنید");
        builder.setView(input);

        builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String enteredText = input.getText().toString();
                if(!enteredText.isEmpty()){
                    boolean acDeleted;
                    try (ExampleSaverActivity Adel = new ExampleSaverActivity(context)) {
                        SQLiteDatabase db = Adel.getWritableDatabase();
                        acDeleted=Adel.acDel(Aname,Apass,enteredText);
                    }
                    if (acDeleted){
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        SharedPreferences pref =context.getSharedPreferences("Author", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("authorId");
                        editor.remove("authorName");
                        editor.apply();

                        Toast.makeText(context,"با موفقیت حساب گاربری حذف گردید ",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"رمز عبور اشتباه است ",Toast.LENGTH_SHORT).show();

                    }

                }



            }
        });

        builder.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static boolean validationField(HashMap<String, String> fieldExam,Activity activity) {
        int c=0;
        for (String value : fieldExam.values()) {

            if(value.isEmpty()){
                c++;
            }
        }
        if(c==0){
            return true;
        }else{
            Toast.makeText(activity, " ابتدا تمامی فیلد هارا پر کنید ", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public static boolean validationDate(String Date, ExampleActivity exampleActivity) {
        boolean vaild=false;
        char delimiter = '/';
        int countStart=0;
        for (int i = 0; i < Date.length(); i++) {
            if (Date.charAt(i) == delimiter) {
                countStart++;
            }
        }
        if(countStart==2){
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH) + 1; // ایندکس‌ها از 0 شروع می‌شوند، بنابراین 1 را اضافه می‌کنیم
            int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

            String[] parts = Date.split("/");
            int inputYear = Integer.parseInt(parts[0]);
            int inputMonth = Integer.parseInt(parts[1]);
            int inputDay = Integer.parseInt(parts[2]);

            if(inputYear<=2000 || inputYear>=2100 || inputMonth<1 || inputYear>=13
                    || inputDay<1 || inputDay>=32){
                Toast.makeText(exampleActivity, "تاریخ صحیح نیست", Toast.LENGTH_SHORT).show();
            }// else if (inputYear < currentYear ||
//                (inputYear == currentYear && inputMonth < currentMonth) ||
//                (inputYear == currentYear && inputMonth == currentMonth && inputDay < currentDay)) {
//            Toast.makeText(exampleActivity, "تاریخ شروع صحیح نیست2", Toast.LENGTH_SHORT).show();
//
//        } else {
//        vaild=true;
//        }
        }else {
            Toast.makeText(exampleActivity, "تاریخ صحیح نیست", Toast.LENGTH_SHORT).show();
        }


        return vaild;
    }
}
