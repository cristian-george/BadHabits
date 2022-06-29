package com.example.badhabits.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.badhabits.helper.InputValidators;
import com.example.badhabits.R;
import com.example.badhabits.database.DBHelper;
import com.example.badhabits.models.UserModel;

import java.util.ArrayList;

public class InfoCardActivity extends AppCompatActivity {
    DBHelper dbHelper;
    Button btnUpdateUsername;
    Button btnUpdateEmail;
    Button btnUpdatePassword;

    Toolbar toolbar;

    private enum ButtonType {
        UPDATE_USERNAME,
        UPDATE_EMAIL,
        UPDATE_PASSWORD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card);

        dbHelper = new DBHelper(InfoCardActivity.this);

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        setButtonUpdateUsername();
        setButtonUpdateEmail();
        setButtonUpdatePassword();
    }

    private void setButtonUpdateUsername() {
        btnUpdateUsername = findViewById(R.id.btnChangeUsername);
        btnUpdateUsername.setOnClickListener(view -> showDialog(InfoCardActivity.this, "Change username", "New username: ", ButtonType.UPDATE_USERNAME));
    }

    private void setButtonUpdateEmail() {
        btnUpdateEmail = findViewById(R.id.btnChangeEmail);
        btnUpdateEmail.setOnClickListener(view -> showDialog(InfoCardActivity.this, "Change email", "New email: ", ButtonType.UPDATE_EMAIL));
    }

    private void setButtonUpdatePassword() {
        btnUpdatePassword = findViewById(R.id.btnChangePassword);
        btnUpdatePassword.setOnClickListener(view -> showDialog(InfoCardActivity.this, "Change password", "New password: ", ButtonType.UPDATE_PASSWORD));
    }

    private void changeData(String newData, String success, String fail, ButtonType buttonType) {
        if (newData != null) {
            int isUpdated = 0;
            switch (buttonType) {
                case UPDATE_USERNAME: {
                    if (InputValidators.isUsernameValid(newData))
                        isUpdated = dbHelper.updateUser(newData, null, null);
                    break;
                }
                case UPDATE_EMAIL: {
                    ArrayList<UserModel> users = dbHelper.getAllUsers();
                    if (InputValidators.isEmailValid(newData, users))
                        isUpdated = this.dbHelper.updateUser(null, newData, null);
                    break;
                }
                case UPDATE_PASSWORD: {
                    if (InputValidators.isPasswordValid(newData, newData))
                        isUpdated = dbHelper.updateUser(null, null, newData);
                    break;
                }
                default:
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
                .setPositiveButton("Submit", (dialog1, which) -> {
                    if (buttonType.equals(ButtonType.UPDATE_USERNAME)) {
                        String newUsername = String.valueOf(taskEditText.getText());
                        changeData(newUsername,
                                "Successfully changed your username!",
                                "Couldn't change your username!",
                                buttonType);
                    } else if (buttonType.equals(ButtonType.UPDATE_EMAIL)) {
                        String newEmail = String.valueOf(taskEditText.getText());
                        changeData(newEmail,
                                "Successfully changed your email!",
                                "Couldn't change your email!",
                                buttonType);
                    } else if (buttonType.equals(ButtonType.UPDATE_PASSWORD)) {
                        String newEmail = String.valueOf(taskEditText.getText());
                        changeData(newEmail,
                                "Successfully changed your password!",
                                "Couldn't change your password!",
                                buttonType);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));
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
            Intent intent = new Intent(InfoCardActivity.this, SelectColorActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}