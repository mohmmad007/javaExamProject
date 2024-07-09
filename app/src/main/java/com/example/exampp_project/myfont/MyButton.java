package com.example.exampp_project.myfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {
    public MyButton(@NonNull Context context) {
        super(context);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        if (!isInEditMode()){
            Typeface typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/FontsFree-Net-ir_sans.ttf");
            this.setTypeface(typeface);
        }
    }
}
