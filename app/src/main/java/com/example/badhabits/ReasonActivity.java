package com.example.badhabits;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;

public class ReasonActivity extends AppCompatActivity {

    EditText badHabit;
    Button insertBadHabit;
    LocalDate selectedSpecifiedDate;
    DBHelper myDB;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        myDB = new DBHelper(ReasonActivity.this);

        badHabit = (EditText) findViewById(R.id.badhabit);
        insertBadHabit = (Button) findViewById(R.id.insertbadhabit);

        selectedSpecifiedDate = (LocalDate) getIntent().getExtras().get("selectedSpecifiedDate");

        insertBadHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.insertHabit(LoginActivity.currentUserId - 1, badHabit.getText().toString(), selectedSpecifiedDate);
            }
        });

    }
}