package com.example.badhabits;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReasonActivity extends AppCompatActivity {
    EditText badHabit;
    Button insertBadHabit;
    LocalDate selectedSpecifiedDate;
    DBHelper dbHelper;
    String badHabitString;

    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        dbHelper = new DBHelper(ReasonActivity.this);

        badHabit = findViewById(R.id.badhabit);
        insertBadHabit = findViewById(R.id.insertbadhabit);

        selectedSpecifiedDate = (LocalDate) getIntent().getExtras().get("selectedSpecifiedDate");

        insertBadHabit.setOnClickListener(v -> {
            badHabitString = badHabit.getText().toString();
            boolean alreadyPlaced = false;
            ArrayList<BadHabitModel> habits = dbHelper.getAllHabits();
            for (BadHabitModel var : habits) {
                if (var.getUserId() == LoginActivity.currentUserId - 1) {
                    if (badHabitString.equals(var.getHabit())) {
                        alreadyPlaced = true;
                        break;
                    }
                }
            }
            if (alreadyPlaced) {
                Toast.makeText(ReasonActivity.this, "You've already put this bad habit!", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insertHabit(LoginActivity.currentUserId - 1, badHabit.getText().toString(), selectedSpecifiedDate);
                Toast.makeText(ReasonActivity.this, "You've inserted your bad habit successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));
    }
}