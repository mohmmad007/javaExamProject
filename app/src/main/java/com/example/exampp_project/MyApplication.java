package com.example.exampp_project;

import android.app.Application;
import android.graphics.Typeface;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "FontsFree-Net-ir_sans.ttf");

            // فایل فونت با موفقیت بارگذاری شده است
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("FontsFree-Net-ir_sans.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
//    } else {
            // فایل فونتی پیدا نشد
//        }
    }

}
