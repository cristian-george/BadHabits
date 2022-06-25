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

    private enum ButtonType {
        NONE,
        UPDATE_USERNAME,
        UPDATE_EMAIL,
        UPDATE_PASSWORD,
        DELETE_ACCOUNT
    }

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
                showDialog(InfoCardActivity.this, "Change username", "New username: ", ButtonType.UPDATE_USERNAME);
            }
        });
    }

    private void setButtonUpdateEmail() {
        btnUpdateEmail = (Button) findViewById(R.id.btnChangeEmail);
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(InfoCardActivity.this, "Change email", "New email: ", ButtonType.UPDATE_EMAIL);
            }
        });
    }

    private void changeData(String newData, String success, String fail, ButtonType buttonType) {
        if (newData != null) {
            int isUpdated;
            switch (buttonType) {
                case UPDATE_USERNAME: {
                    isUpdated = myDB.updateUser(newData, null, null);
                    break;
                }
                case UPDATE_EMAIL: {
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

    private void showDialog(Context c, String title, String message, ButtonType buttonType) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setView(taskEditText)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (buttonType.equals(ButtonType.UPDATE_USERNAME)) {
                            String newUsername = String.valueOf(taskEditText.getText());
                            changeData(newUsername,
                                    "Successfully changed your username!",
                                    "Couldn't change your username!",
                                    ButtonType.UPDATE_USERNAME);
                        } else if (buttonType.equals(ButtonType.UPDATE_EMAIL)) {
                            String newEmail = String.valueOf(taskEditText.getText());
                            changeData(newEmail,
                                    "Successfully changed your email!",
                                    "Couldn't change your email!",
                                    ButtonType.UPDATE_EMAIL);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}