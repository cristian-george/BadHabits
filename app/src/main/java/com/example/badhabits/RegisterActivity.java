package com.example.badhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    DBHelper myDB;
    TextView textView;
    Button registerButton;
    EditText usernameView;
    EditText emailView;
    EditText passwordView;
    EditText repeatPasswordView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDB = new DBHelper(RegisterActivity.this);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        usernameView = (EditText) findViewById(R.id.edtUsername);
        emailView = (EditText) findViewById(R.id.edtEmail);
        passwordView = (EditText) findViewById(R.id.edtPass);
        registerButton = (Button) findViewById(R.id.register_btn);
        repeatPasswordView = (EditText) findViewById(R.id.edtRepeatpass);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameView.getText().toString();
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();
                String repeatPassword = repeatPasswordView.getText().toString();
                UserModel userModel = new UserModel(username, email, password);
                if(!isUsernameValid(username)){
                    Toast.makeText(RegisterActivity.this, "Username should contain at least 4 characters!", Toast.LENGTH_SHORT).show();
                }
                else if(!isEmailValid(email)){
                    Toast.makeText(RegisterActivity.this, "Email is not valid!", Toast.LENGTH_SHORT).show();
                }
                else if(!isPasswordValid(password,repeatPassword)){
                    Toast.makeText(RegisterActivity.this, "Passwords aren't same or doesn't have 4 characters!", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean inUse = false;
                    ArrayList<UserModel> arrayList = myDB.getAllUsers();
                    for(UserModel var:arrayList){
                        if(var.getEmail().equals(email)) {
                            inUse = true;
                            Toast.makeText(RegisterActivity.this, "Email is already in use!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(!inUse) {
                        if (myDB.insertUser(userModel)) {
                            Toast.makeText(RegisterActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                            arrayList = myDB.getAllUsers();
                            for (UserModel var : arrayList) {
                                if (var.getEmail().equals(email) && var.getPassword().equals(password)) {
                                    LoginActivity.currentUserId = var.getId();
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            public boolean isPasswordValid(String password, String repeatPassword){
                if(!password.equals(repeatPassword) || password.length() <=4)
                    return false;
                return true;
            }

            public boolean isUsernameValid(String username){
                if(username.length() < 3)
                    return false;
                return true;
            }

            public boolean isEmailValid(String email){
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
            }


        });
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