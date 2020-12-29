package com.example.pondytourism;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Attractions extends AppCompatActivity implements ItemClickListener {
    RecyclerView attractionRecycler;
    List<String> attractionList;
    List<String> attractionDetailList;
    List<String> languages;
    List<String> coordinates;
    List<String> images;
    AttractionAdapter attractionAdapter;
    String categoryString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        if (getIntent().getExtras() != null)
            categoryString = getIntent().getExtras().getString("category");

        getSupportActionBar().setTitle(categoryString);
        Log.i("category", categoryString);
        attractionList = new ArrayList<>();
        attractionDetailList = new ArrayList<>();
        languages = new ArrayList<>();
        coordinates = new ArrayList<>();
        images = new ArrayList<>();

        try {
            populateData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        attractionRecycler = findViewById(R.id.attractionList);
        attractionAdapter = new AttractionAdapter(Attractions.this, attractionList, attractionDetailList, images);
        attractionAdapter.setClickListener(this);
        attractionRecycler.setAdapter(attractionAdapter);
        attractionRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    void populateData() throws JSONException {
        String jsonData = loadJSONFromAsset();
        JSONObject reader = new JSONObject(jsonData);

        JSONObject category = reader.getJSONObject("category");
        JSONArray catArray = category.getJSONArray(categoryString);
        Log.i("attractionList", catArray.toString());

        for (int i = 0; i < catArray.length(); i++) {
            JSONObject r = catArray.getJSONObject(i);
            Log.i("attractionList", r.toString(1));
            attractionList.add(r.getString("name"));
            images.add(r.getString("image"));
            JSONObject detail = r.getJSONObject("languages");
            attractionDetailList.add(detail.getString("english"));
            languages.add(detail.toString());
            coordinates.add(r.getString("coordinate"));
//            Log.i("languages", languages.toString());
//            Log.i("locations", r.getString("coordinate"));
        }

        Log.i("attractionList", attractionList.toString());

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("data2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onItemClick(View v, int position) {
        String name = attractionList.get(position);
        String detail = attractionDetailList.get(position);
        String language = languages.get(position);
        String image = images.get(position);
        String coordinate = coordinates.get(position);

        Intent previewIntent = new Intent(Attractions.this, AttractionDetail.class);
        previewIntent.putExtra("name", name);
        previewIntent.putExtra("image", image);
        previewIntent.putExtra("detail", detail);
        previewIntent.putExtra("language", language);
        previewIntent.putExtra("coordinate", coordinate);
        startActivity(previewIntent);
    }
}