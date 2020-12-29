package com.example.pondytourism;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login;

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


        login.setOnClickListener(v -> {
            if (email.getText().toString().equals("Rakesh") && password.getText().toString().equals("123456")) {
                editor.putBoolean("loggedin", true);
                editor.apply();
                startActivity(new Intent(MainActivity.this, Dashboard.class));
                finishAffinity();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect Credential", Toast.LENGTH_SHORT).show();
            }
        });
    }
}