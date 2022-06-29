package com.example.badhabits.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.badhabits.R;

public class SelectColorActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button redColor;
    Button greenColor;
    Button purpleColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);

        redColor = findViewById(R.id.btnRed);
        greenColor = findViewById(R.id.btnGreen);
        purpleColor = findViewById(R.id.btnPurple);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(getColor(this));
        getWindow().setStatusBarColor(getColor(this));

        redColor.setOnClickListener(view -> {
            toolbar.setBackgroundColor(getResources().getColor(R.color.red));
            getWindow().setStatusBarColor(getResources().getColor(R.color.red));
            storeColor(SelectColorActivity.this, getResources().getColor(R.color.red));
        });

        greenColor.setOnClickListener(view -> {
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
            getWindow().setStatusBarColor(getResources().getColor(R.color.green));
            storeColor(SelectColorActivity.this, getResources().getColor(R.color.green));
        });

        purpleColor.setOnClickListener(view -> {
            toolbar.setBackgroundColor(getResources().getColor(R.color.purple_500));
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
            storeColor(SelectColorActivity.this, getResources().getColor(R.color.purple_500));
        });
    }

    public static void storeColor(Context context, int color) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("color", color);
        editor.apply();
    }

    public static int getColor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        return sharedPreferences.getInt("color", context.getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
    }
}