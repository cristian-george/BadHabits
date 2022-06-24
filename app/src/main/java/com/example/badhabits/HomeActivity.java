package com.example.badhabits;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    DBHelper myDB;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDB = new DBHelper(HomeActivity.this);
        ArrayList<UserModel> arrayList = myDB.getAllUsers();
        String currentUsername = arrayList.get(LoginActivity.currentUserId - 1).getUsername();
        textView = (TextView) findViewById(R.id.title_view);
        String aux = "Welcome back, " + currentUsername;
        textView.setText(aux);
    }
}