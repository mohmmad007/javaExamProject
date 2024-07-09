package com.example.exampp_project.myfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {
    public MyEditText(@NonNull Context context) {
        super(context);
        init();
    }

    public MyEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        if (!isInEditMode()){
            Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"FontsFree-Net-ir_sans");
            this.setTypeface(typeface);
        }
    }
}
