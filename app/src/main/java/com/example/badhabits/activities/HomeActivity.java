package com.example.badhabits.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.badhabits.R;
import com.example.badhabits.database.DBHelper;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    DBHelper myDB;
    CardView infoCardView;
    CardView calendarCardView;
    CardView globalCardView;
    Toolbar toolbar;
    CardView rewardCard;
    CardView profileCard;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDB = new DBHelper(HomeActivity.this);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome back, " +
                myDB.getAllUsers().get(LoginActivity.currentUserId - 1).getUsername() + "!");
        infoCardView = (CardView) findViewById(R.id.infoCard);
        infoCardView.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, InfoCardActivity.class);
            startActivity(intent);
        });

        calendarCardView = (CardView) findViewById(R.id.infoCalendar);
        calendarCardView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        globalCardView = (CardView) findViewById(R.id.globalCard);
        globalCardView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, GlobalActivity.class);
            startActivity(intent);
        });

        rewardCard = (CardView) findViewById(R.id.rewardsCard);
        rewardCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RewardActivity.class);
            startActivity(intent);
        });

        profileCard = (CardView) findViewById(R.id.homecard);
        profileCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome back, " +
                myDB.getAllUsers().get(LoginActivity.currentUserId - 1).getUsername() + "!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(HomeActivity.this, SelectColorActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}