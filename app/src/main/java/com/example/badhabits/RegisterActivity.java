package com.example.badhabits;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;

    private Toolbar toolbar;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(RegisterActivity.this);
        TextView textView = findViewById(R.id.textView2);
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        usernameEditText = findViewById(R.id.edtUsername);
        emailEditText = findViewById(R.id.edtEmail);
        passwordEditText = findViewById(R.id.edtPass);
        repeatPasswordEditText = findViewById(R.id.edtRepeatpass);

        Button registerButton = findViewById(R.id.register_btn);
        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String repeatPassword = repeatPasswordEditText.getText().toString();

            ArrayList<UserModel> users = dbHelper.getAllUsers();
            registerValidator(username, email, password, repeatPassword, users);
        });
    }

    private void registerValidator(String username, String email, String password, String repeatPassword, ArrayList<UserModel> users) {
        if (!InputValidators.isUsernameValid(username)) {
            Toast.makeText(RegisterActivity.this, "Username should contain at least 4 characters!", Toast.LENGTH_SHORT).show();
        } else if (!InputValidators.isEmailValid(email, users)) {
            Toast.makeText(RegisterActivity.this, "Email is not valid or is already used!", Toast.LENGTH_SHORT).show();
        } else if (!InputValidators.isPasswordValid(password, repeatPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords aren't same or doesn't have at least 4 characters!", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(username, email, password);
        }
    }

    private void registerUser(String username, String email, String password) {
        UserModel userModel = new UserModel(username, email, password);
        if (dbHelper.insertUser(userModel)) {
            Toast.makeText(RegisterActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
            ArrayList<UserModel> users = dbHelper.getAllUsers();
            for (UserModel var : users) {
                if (var.getEmail().equals(email) && var.getPassword().equals(password)) {
                    LoginActivity.currentUserId = var.getId();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
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
            Intent intent = new Intent(RegisterActivity.this, SelectColorActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}