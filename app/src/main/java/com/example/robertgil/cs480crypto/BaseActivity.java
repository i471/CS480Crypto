package com.example.robertgil.cs480crypto;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        final View rootView = (View) findViewById(android.R.id.content);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.cryptobackground, null);
        rootView.setBackground(drawable);
    }
}
