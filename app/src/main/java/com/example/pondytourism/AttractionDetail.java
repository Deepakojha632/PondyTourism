package com.example.pondytourism;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AttractionDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView imageView;
    Spinner languageSpinner;
    TextView attractionDetail;
    String[] languages;
    String language;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        Bundle extra = getIntent().getExtras();

        languageSpinner = findViewById(R.id.languages);
        imageView = findViewById(R.id.attractionImage);
        attractionDetail = findViewById(R.id.attractionDetail);

        languages = new String[]{"English", "Malayalam", "Telugu", "Tamil", "Hindi"};

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        languageSpinner.setAdapter(languageAdapter);
        languageSpinner.setOnItemSelectedListener(this);


        if (extra != null) {
            getSupportActionBar().setTitle(extra.getString("name"));
            language = extra.getString("language");
            image = extra.getString("image");
            int resourceImage = getResources().getIdentifier(image, "drawable", this.getPackageName());
            imageView.setImageDrawable(getResources().getDrawable(resourceImage));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString().toLowerCase();
        //Log.i("language", language);
        try {
            JSONObject lang = new JSONObject(language);
            attractionDetail.setText(lang.getString(item));
            Log.i("language", lang.getString(item));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Language not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        attractionDetail.setText(languages[0]);
    }

}