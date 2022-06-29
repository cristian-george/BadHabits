package com.example.badhabits;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReasonActivity extends AppCompatActivity {

    EditText badHabit;
    Button insertBadHabit;
    LocalDate selectedSpecifiedDate;
    DBHelper myDB;
    String badHabitString;

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
                    badHabitString = badHabit.getText().toString();
                    boolean alreadyPlaced = false;
                    ArrayList<BadHabitModel> habits = myDB.getAllHabits();
                    for(BadHabitModel var : habits){
                        if(var.getUserId() == LoginActivity.currentUserId-1){
                            if(badHabitString.equals(var.getHabit())){
                                alreadyPlaced=true;
                                break;
                            }
                        }
                    }
                    if(alreadyPlaced){
                        Toast.makeText(ReasonActivity.this, "You already put this bad habit!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        myDB.insertHabit(LoginActivity.currentUserId - 1, badHabit.getText().toString(), selectedSpecifiedDate);
                        Toast.makeText(ReasonActivity.this, "You inserted your bad habit succesfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }