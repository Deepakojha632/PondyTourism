package com.example.pondytourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class Dashboard extends AppCompatActivity implements ItemClickListener {
    RecyclerView categoryList;
    List<String> category;
    CategoryAdapter categoryAdapter;
    RelativeLayout aboutWindow;
    FloatingActionButton closeAbout, addAttraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        getSupportActionBar().setTitle("Dashboard");

        categoryList = findViewById(R.id.attractions);
        aboutWindow = findViewById(R.id.aboutUS);
        closeAbout = findViewById(R.id.closeAbout);
        addAttraction = findViewById(R.id.addAttraction);


        category = Arrays.asList(getResources().getStringArray(R.array.categories));
        categoryAdapter = new CategoryAdapter(Dashboard.this, category);
        categoryAdapter.setClickListener(this);
        categoryList.setAdapter(categoryAdapter);
        categoryList.setHasFixedSize(true);
        categoryList.setLayoutManager(new GridLayoutManager(Dashboard.this, 2));


        closeAbout.setOnClickListener(v -> {
            aboutWindow.setVisibility(View.GONE);
            addAttraction.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about_us:
                aboutWindow.setVisibility(View.VISIBLE);
                addAttraction.setVisibility(View.GONE);
                break;
            case R.id.action_logout:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int position) {
        startActivity(new Intent(Dashboard.this, Attractions.class).putExtra("category", category.get(position)));
    }
}