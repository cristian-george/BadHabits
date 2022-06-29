package com.example.badhabits;

import static java.time.temporal.ChronoUnit.DAYS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RewardActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(RewardActivity.this);
    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutManager;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<UserModel> users = dbHelper.getAllUsers();
        ArrayList<BadHabitModel> habitModels = dbHelper.getAllHabits();
        Collections.sort(habitModels, new Sorting());
        setContentView(R.layout.activity_reward);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        String[] programNameList = new String[habitModels.size()];
        String[] programDescriptionList = new String[habitModels.size()];
        int[] programImages = new int[habitModels.size()];

        LocalDate localDate = LocalDate.now();
        for (int index = 0; index < programDescriptionList.length; ++index) {

            BadHabitModel habitModel = habitModels.get(index);
            long days = DAYS.between(habitModel.getDate(), localDate);

            programNameList[index] = "\n";
            programNameList[index] += (users.get(habitModel.getUserId()).getUsername());

            programDescriptionList[index] = habitModel.getHabit();
            programDescriptionList[index] += "\n";
            programDescriptionList[index] += String.valueOf(days);
            programDescriptionList[index] += " days ago";

            if (days >= 30 && days < 60) {
                programImages[index] = R.drawable.bronze_medal;
            } else if (days >= 60 && days < 90) {
                programImages[index] = R.drawable.silver_medal;
            } else if (days >= 90) {
                programImages[index] = R.drawable.gold_medal;
            } else {
                programImages[index] = R.drawable.empty_medal2;
            }
        }

        recyclerView = findViewById(R.id.rvProgram);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        programAdapter = new ProgramAdapter(this, programNameList, programDescriptionList, programImages);
        recyclerView.setAdapter(programAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));
    }
}

class Sorting implements Comparator<BadHabitModel> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(BadHabitModel o1, BadHabitModel o2) {
        LocalDate localDate = LocalDate.now();
        int x = (int) DAYS.between(localDate, o2.date);
        int y = (int) DAYS.between(localDate, o1.date);
        return y - x;
    }
}