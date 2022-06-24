package com.example.badhabits;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoCardActivity extends AppCompatActivity {
    DBHelper myDB;
    Button btnUpdateUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card);

        myDB = new DBHelper(InfoCardActivity.this);

        SetButtonUpdateUsername();
    }

    private void SetButtonUpdateUsername() {
        btnUpdateUsername = (Button) findViewById(R.id.btnChangeUsername);
        btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(InfoCardActivity.this, "Change username", "New username: ");
            }
        });
    }

    private void showDialog(Context c, String title, String message) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setView(taskEditText)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUsername = String.valueOf(taskEditText.getText());
                        if (newUsername != null) {
                            if (myDB.updateUser(newUsername, null, null)) {
                                Toast.makeText(InfoCardActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(InfoCardActivity.this, "Username is null!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}