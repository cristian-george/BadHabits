package com.example.badhabits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    DBHelper myDB;
    TextView textView;
    CardView infoCardView;

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

        infoCardView = (CardView) findViewById(R.id.infoCard);
        infoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InfoCardActivity.class);
                startActivity(intent);
            }
        });
    }
}