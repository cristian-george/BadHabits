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
    Button btnUpdateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card);

        myDB = new DBHelper(InfoCardActivity.this);

        setButtonUpdateUsername();
        setButtonUpdateEmail();
    }

    private void setButtonUpdateUsername() {
        btnUpdateUsername = (Button) findViewById(R.id.btnChangeUsername);
        btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(InfoCardActivity.this, "Change username", "New username: ");
            }
        });
    }

    private void setButtonUpdateEmail() {
        btnUpdateEmail = (Button) findViewById(R.id.btnChangeEmail);
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(InfoCardActivity.this, "Change email", "New email: ");
            }
        });
    }

    private void changeData(String newData, String success, String fail, int caseCode) {
        if (newData != null) {
            int isUpdated;
            switch (caseCode) {
                case 1: {
                    isUpdated = myDB.updateUser(newData, null, null);
                    break;
                }
                case 2: {
                    isUpdated = myDB.updateUser(null, newData, null);
                    break;
                }
                default:
                    isUpdated = 0;
            }

            if (isUpdated != 0) {
                Toast.makeText(InfoCardActivity.this, success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InfoCardActivity.this, fail, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(InfoCardActivity.this, "Field is null!", Toast.LENGTH_SHORT).show();
        }
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
                        if (title.equals("Change username")) {
                            String newUsername = String.valueOf(taskEditText.getText());
                            changeData(newUsername, "Successfully changed your username!", "Couldn't change your username!", 1);
                        } else if (title.equals("Change email")) {
                            String newEmail = String.valueOf(taskEditText.getText());
                            changeData(newEmail, "Successfully changed your email!", "Couldn't change your email!", 2);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}