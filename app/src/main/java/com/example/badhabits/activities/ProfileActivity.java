package com.example.badhabits.activities;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.badhabits.R;
import com.example.badhabits.adapters.BadHabitModelAdapter;
import com.example.badhabits.database.DBHelper;
import com.example.badhabits.models.BadHabitModel;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;

    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        listView = (ListView) findViewById(R.id.listView);
        dbHelper = new DBHelper(ProfileActivity.this);
        ArrayList<BadHabitModel> badHabitModels = dbHelper.getAllHabits();
        ArrayList<BadHabitModel> badHabitModelArrayList = new ArrayList<>();
        for (BadHabitModel var : badHabitModels) {
            if (LoginActivity.currentUserId - 1 == var.getUserId())
                badHabitModelArrayList.add(var);
        }

        BadHabitModelAdapter badHabitModelAdapter = new BadHabitModelAdapter(this, R.layout.adapter_view_layout, badHabitModelArrayList);
        listView.setAdapter(badHabitModelAdapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            final int which_item = position;

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this item?")
                    .setNegativeButton("Yes", (dialog, which) -> {
                        ArrayList<BadHabitModel> auxArray = dbHelper.getAllHabits();
                        for (int index = 0; index < auxArray.size(); ++index) {
                            if (LoginActivity.currentUserId - 1 == auxArray.get(index).getUserId()) {
                                if (badHabitModelArrayList.get(which_item).getHabit().equals(auxArray.get(index).getHabit())) {
                                    dbHelper.deleteHabit(auxArray.get(index));
                                }
                            }
                        }
                        badHabitModelArrayList.remove(which_item);
                        badHabitModelAdapter.notifyDataSetChanged();
                    })
                    .setPositiveButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));
    }
}