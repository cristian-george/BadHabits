package com.example.badhabits;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        listView = (ListView) findViewById(R.id.listView);
        dbHelper = new DBHelper(ProfileActivity.this);
        ArrayList<BadHabitModel> badHabitModels = dbHelper.getAllHabits();
        ArrayList<BadHabitModel> badHabitModelArrayList = new ArrayList<>();
        for(BadHabitModel var:badHabitModels){
            if(LoginActivity.currentUserId-1 == var.getUserId())
                badHabitModelArrayList.add(var);
        }

        BadHabitModelAdapter badHabitModelAdapter = new BadHabitModelAdapter(this,R.layout.adapter_view_layout,badHabitModelArrayList);
        listView.setAdapter(badHabitModelAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;

                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArrayList<BadHabitModel> auxArray = dbHelper.getAllHabits();
                                for(int index =0;index<auxArray.size();++index){
                                    if(LoginActivity.currentUserId-1 == auxArray.get(index).getUserId()) {
                                        if (badHabitModelArrayList.get(which_item).getHabit().equals(auxArray.get(index).getHabit())){
                                            dbHelper.deleteHabit(auxArray.get(index));
                                        }
                                    }
                                }
                                badHabitModelArrayList.remove(which_item);
                                badHabitModelAdapter.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("No",null)
                        .show();
                return true;
            }
        });
    }
}