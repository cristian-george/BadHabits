package com.example.badhabits;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.badhabits.databinding.ActivityGlobalBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GlobalActivity extends AppCompatActivity {
    ActivityGlobalBinding binding;
    ArrayList<String> usersList;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGlobalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(androidx.appcompat.R.id.action_bar);
        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));

        initializeUserList();
        binding.fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new fetchData().start();
            }
        });
    }

    private void initializeUserList() {
        usersList = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        binding.userList.setAdapter(listAdapter);
    }

    class fetchData extends Thread {

        String data = "";

        @Override
        public void run() {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(GlobalActivity.this);
                    progressDialog.setMessage("Fetching data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });

            try {
                URL url = new URL("https://api.npoint.io/19c91d141d1a404c8a44");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }

                if (!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray users = jsonObject.getJSONArray("Users");
                    usersList.clear();
                    for (int index = 0; index < users.length(); ++index) {
                        JSONObject names = users.getJSONObject(index);
                        String name = names.getString("name");
                        usersList.add(name);
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        toolbar.setBackgroundColor(SelectColorActivity.getColor(this));
        getWindow().setStatusBarColor(SelectColorActivity.getColor(this));
    }
}