package com.anyholo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IntroPopupDialog extends Dialog {
    TextView introText;
    String content;
    public IntroPopupDialog(@NonNull Context context,String content) {
        super(context);
        this.content=content;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro_popup_dialog);
        introText = findViewById(R.id.popup_intro_text);
        introText.setMovementMethod(new ScrollingMovementMethod());
        introText.setText(content);
    }
}
