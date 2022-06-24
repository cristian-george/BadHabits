package com.example.badhabits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    DBHelper myDB;
    TextView textView;
    Button registerButton;
    EditText usernameView;
    EditText emailView;
    EditText passwordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDB = new DBHelper(RegisterActivity.this);
        textView = (TextView)findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        usernameView = (EditText) findViewById(R.id.edtUsername);
        emailView = (EditText)  findViewById(R.id.edtEmail);
        passwordView = (EditText) findViewById(R.id.edtPass);
        registerButton = (Button)findViewById(R.id.register_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameView.getText().toString();
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();
                UserModel userModel = new UserModel(username, email,password);
                if(myDB.insertUser(userModel)){
                    Toast.makeText(RegisterActivity.this, "Succsesfully!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}