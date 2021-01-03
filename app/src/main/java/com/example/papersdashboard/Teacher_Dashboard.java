package com.example.papersdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Teacher_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //initialize drawer
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    private DrawerLayout drawerLayout;
    ImageView iv_menu;
    ImageView addPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__dashboard);

//        addPaper= findViewById(R.id.img_addPaper);
//        sh= getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        editor = sh.edit();
//
//        String  s1 = sh.getString("role", "");
//        String s2="Professor";
//        if (s1.equals(s2)) {
//            Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();
//            addPaper.setVisibility(View.VISIBLE);
//        }
        //assign variables

        iv_menu =findViewById(R.id.iv_Tmenu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerLayout= findViewById(R.id.Teacher_drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        ApprovedFragment fragment1=new ApprovedFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container,fragment1,"");
        ft1.commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        addPaper = findViewById(R.id.img_addPaper);
        addPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher_Dashboard.this,Generate_paper.class);
                startActivity(intent);
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_tApproved:
                            ApprovedFragment fragment1 = new ApprovedFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.fragment_container, fragment1, "");
                            ft1.commit();
                            return true;

                        case R.id.nav_tPending:
                            PendingFragment fragment2 = new PendingFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.fragment_container, fragment2, "");
                            ft2.commit();
                            return true;
                        case R.id.nav_tRejected:
                            RejectedFragment fragment3 = new RejectedFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.fragment_container, fragment3, "");
                            ft3.commit();
                            return true;
                        case R.id.nav_tRequested:
                             fragment_requested fragment4 = new fragment_requested();
                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                            ft4.replace(R.id.fragment_container, fragment4, "");
                            ft4.commit();
                            return true;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            };
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
        return false;
    }

    public void ClickMenu(View view) {
    }
}