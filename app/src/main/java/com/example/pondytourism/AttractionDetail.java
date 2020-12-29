package com.example.pondytourism;

import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class AttractionDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView imageView;
    Spinner languageSpinner;
    TextView attractionDetail;
    String[] languages;
    String language;
    String image;
    String location;
    FloatingActionButton locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        Bundle extra = getIntent().getExtras();

        languageSpinner = findViewById(R.id.languages);
        imageView = findViewById(R.id.attractionImage);
        attractionDetail = findViewById(R.id.attractionDetail);
        locate = findViewById(R.id.locate);

        languages = new String[]{"English", "Malayalam", "Telugu", "Tamil", "Hindi"};

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        languageSpinner.setAdapter(languageAdapter);
        languageSpinner.setOnItemSelectedListener(this);


        if (extra != null) {
            getSupportActionBar().setTitle(extra.getString("name"));
            language = extra.getString("language");
            image = extra.getString("image");
            location = extra.getString("coordinate");
            int resourceImage = getResources().getIdentifier(image, "drawable", this.getPackageName());
            imageView.setImageDrawable(getResources().getDrawable(resourceImage));
        }

        locate.setOnClickListener(v -> {
            JSONObject latlong;
            double lat = 0.0, lon = 0.0;
            try {
                latlong = new JSONObject(location);
                lat = latlong.getDouble("lat");
                lon = latlong.getDouble("long");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("latlong", lat + "" + lon);
            // Create a Uri from an intent string. Use the result to create an Intent.
            Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lon + "");

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        });
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