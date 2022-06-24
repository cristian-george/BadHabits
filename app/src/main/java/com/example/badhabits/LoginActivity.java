package com.example.badhabits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    DBHelper myDB;
    public static int currentUserId = -1;
    TextView textView;
    EditText emailView;
    EditText passwordView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new DBHelper(LoginActivity.this);

        emailView = (EditText) findViewById(R.id.edtEmail);
        passwordView = (EditText) findViewById(R.id.edtPass);

        button = (Button) findViewById(R.id.Login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = passwordView.getText().toString();
                String email = emailView.getText().toString();
                ArrayList<UserModel> arrayList = myDB.getAllUsers();
                for (UserModel var : arrayList) {
                    if (var.getEmail().equals(email) && var.getPassword().equals(pass)) {
                        currentUserId = var.getId();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                if (currentUserId == -1) {
                    Toast.makeText(LoginActivity.this, "Username or password wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView = (TextView) findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}