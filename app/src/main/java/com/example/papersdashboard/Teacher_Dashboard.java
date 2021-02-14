package com.example.papersdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Teacher_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //initialize drawer
    SharedPreferences sh;
    SharedPreferences sharedPreferences;
    TextView tv_username;
    SharedPreferences.Editor editor;
    private DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    TeacherDashboardAdapter adapter;
    ImageView iv_menu;
    ImageView addPaper;
    int id;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__dashboard);
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        recyclerView = findViewById(R.id.recycler_teachercourse);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout= findViewById(R.id.Teacher_drawer_layout);
        View headerView = navigationView.getHeaderView(0);
        tv_username = (TextView) headerView.findViewById(R.id.tv_drawerusername);
        username= sharedPreferences.getString("name","");
        username=username.toUpperCase();
        tv_username.setText(username);
        id= getIntent().getIntExtra("id",0);


        iv_menu =findViewById(R.id.iv_Tmenu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        Intent i= getIntent();
        String code= i.getStringExtra("c");
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        setCourses();

//        fragment_due fragment1=new fragment_due();
//        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
//        ft1.replace(R.id.fragment_container,fragment1,"");
//        fragment1.setArguments(bundle);
//        ft1.commit();



        addPaper = findViewById(R.id.img_addPaper);
        addPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher_Dashboard.this,teachercard.class);
                startActivity(intent);
            }
        });
    }

    private void setCourses() {
        Call<List<Papers>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getPapersForTeacher(id);
        call.enqueue(new Callback<List<Papers>>() {
            @Override
            public void onResponse(Call<List<Papers>> call, Response<List<Papers>> response) {
                if(response.isSuccessful()) {
                    List<Papers> res = response.body();
                    adapter=new TeacherDashboardAdapter(Teacher_Dashboard.this,res);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(Teacher_Dashboard.this, "Nothing found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Papers>> call, Throwable t) {
                Toast.makeText(Teacher_Dashboard.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }

    //nav view bottom accepted rejected etc
//    BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
//    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.nav_tApproved:
//                            ApprovedFragment fragment1 = new ApprovedFragment();
//                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                            ft1.replace(R.id.fragment_container, fragment1, "");
//                            ft1.commit();
//                            return true;
//                        case R.id.nav_tDue:
//                            fragment_due fragment2 = new fragment_due();
//                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
//                            ft2.replace(R.id.fragment_container, fragment2, "");
//                            ft2.commit();
//                            return true;
//                        case R.id.nav_tRejected:
//                            RejectedFragment fragment3 = new RejectedFragment();
//                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
//                            ft3.replace(R.id.fragment_container, fragment3, "");
//                            ft3.commit();
//                            return true;
//                        case R.id.nav_tRequested:
//                             fragment_requested fragment4 = new fragment_requested();
//                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
//                            ft4.replace(R.id.fragment_container, fragment4, "");
//                            ft4.commit();
//                            return true;
//                    }
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                    return false;
//                }
//            };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_logout: {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Teacher_Dashboard.this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void ClickMenu(View view) {
    }
}