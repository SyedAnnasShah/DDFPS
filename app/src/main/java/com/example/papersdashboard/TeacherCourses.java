package com.example.papersdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherCourses extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    ImageView iv_coursesmenu;
    RecyclerView recyclerView;
    TeacherCoursesAdapter adapter;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_courses);

        drawerLayout= findViewById(R.id.teacherfirst_drawer_layout);
        iv_coursesmenu=findViewById(R.id.iv_coursesmenu);
        id=getIntent().getIntExtra("id",0);
        recyclerView = findViewById(R.id.teachercourses_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allocatedcourses(id);
        iv_coursesmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view_courses);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void allocatedcourses(int  id) {
        Call<List<Courses>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCodesAndNames(id);
        call.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                if(response.isSuccessful()) {
                    List<Courses> res = response.body();
                    adapter=new TeacherCoursesAdapter(TeacherCourses.this,res);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    try {
                        Toast.makeText(TeacherCourses.this, "res no"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                Toast.makeText(TeacherCourses.this, "Failed to Retrieve any Course"+ t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void A_ClickMenu(View view) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}