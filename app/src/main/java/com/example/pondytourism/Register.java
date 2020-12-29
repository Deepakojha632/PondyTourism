package com.example.pondytourism;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.io.OutputStreamWriter;

public class Register extends AppCompatActivity implements View.OnClickListener {
    TextView openLogin;
    EditText name, email, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        openLogin = findViewById(R.id.openLogin);
        register = findViewById(R.id.register);

        register.setOnClickListener(this);
        openLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openLogin:
                startActivity(new Intent(Register.this, MainActivity.class));
                finishAffinity();
                break;

            case R.id.register:
                Log.i("registered", "true");
                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    JSONObject user = new JSONObject();
                    try {
                        user.put("name", "" + name.getText().toString().trim() + "");
                        user.put("email", "" + email.getText().toString().trim() + "");
                        user.put("password", "" + password.getText().toString() + "");

                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("user.json", Context.MODE_PRIVATE));
                        outputStreamWriter.write(user.toString());
                        outputStreamWriter.close();
                        Log.i("registered", "true");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

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
                            Log.i("users", users.toString(2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), file.getName(), Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Enter detail properly", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}