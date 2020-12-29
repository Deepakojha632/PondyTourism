package com.example.pondytourism;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView openRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("loggedin")) {
            if (preferences.getBoolean("loggedin", false)) {
                startActivity(new Intent(MainActivity.this, Dashboard.class));
                finishAffinity();
            }
        }
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);
        openRegister = findViewById(R.id.openRegister);

        openRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Register.class));
            finishAffinity();
        });

        login.setOnClickListener(v -> {
            File file = new File(this.getFilesDir(), "user.json");
            if (file.exists()) {
                String ret = "";

                try {
                    InputStream inputStream = new FileInputStream(file);

                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                } catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }

                try {
                    JSONObject users = new JSONObject(ret);
                    if (email.getText().toString().trim().equals(users.getString("email")) && password.getText().toString().trim().equals(users.getString("password"))) {
                        editor.putBoolean("loggedin", true);
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Credential", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("users", users.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else
                Toast.makeText(getApplicationContext(), "No users found", Toast.LENGTH_SHORT).show();
        });
    }
}