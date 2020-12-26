package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class director_dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    CoursesAdapter adapter;
    ImageView iv_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_dashboard);
        drawerLayout= findViewById(R.id.director_drawer_layout);
        iv_menu=(ImageView)findViewById(R.id.iv_Dmenu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        recyclerView =findViewById(R.id.director_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setDCourses();
    }

    private void setDCourses() {
        Call<List<course>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDCourses();
        call.enqueue(new Callback<List<course>>() {
            @Override
            public void onResponse(Call<List<course>> call, Response<List<course>> response) {
                if(response.isSuccessful()) {
                    List<course> res = response.body();
                    Toast.makeText(director_dashboard.this, "res"+res.size(), Toast.LENGTH_SHORT).show();
                    adapter=new CoursesAdapter(director_dashboard.this,res);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(director_dashboard.this, "Invalid Crediential", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<course>> call, Throwable t) {
                Toast.makeText(director_dashboard.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }

    public void D_ClickMenu(View view) {
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}