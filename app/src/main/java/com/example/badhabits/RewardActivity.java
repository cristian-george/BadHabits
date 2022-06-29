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
    DBHelper myDB = new DBHelper(RewardActivity.this);
    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutManager;
    /*String[] programNameList = {"C", "C++", "Java", "Android", "HTML5", "CSS3", "JavaScript", "jQuery", "Bootstrap", "PHP",
            "MySQL", "CodeIgniter", "React", "NodeJS", "AngularJS", "PostgreSQL", "Python", "C#", "Wordpress", "GitHub"};*/
    String[] programDescriptionList = {"C Description", "C++ Description", "Java Description",
            "Android Description", "HTML5 Description",
            "CSS3 Description", "JavaScript Description", "jQuery Description",
            "Bootstrap Description", "PHP Description", "MySQL Description",
            "CodeIgniter Description", "React Description", "NodeJS Description",
            "AngularJS Description", "PostgreSQL Description", "Python Description",
            "C# Description", "Wordpress Description", "GitHub Description"};
    // Define an integer array to hold the image recourse ids
    int[] programImages = {R.drawable.c, R.drawable.cplusplus,
            R.drawable.java, R.drawable.android, R.drawable.html5,
            R.drawable.css3, R.drawable.javascript, R.drawable.jquery,
            R.drawable.bootstrap, R.drawable.php, R.drawable.mysql,
            R.drawable.codeigniter, R.drawable.react, R.drawable.nodejs,
            R.drawable.angularjs, R.drawable.postgresql, R.drawable.python,
            R.drawable.csharp, R.drawable.wordpress, R.drawable.github};

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<UserModel> users = myDB.getAllUsers();
        ArrayList<BadHabitModel> habitModels = myDB.getAllHabits();
        Collections.sort(habitModels, new Sorting());
        setContentView(R.layout.activity_reward);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));
        String[] programNameList = new String[habitModels.size()];
        for(int index=0;index<programNameList.length;++index)
        {
            programNameList[index] = (users.get(habitModels.get(index).getUserId()).getUsername());
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
        return y-x;
    }
}