package com.example.papersdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class director_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView nav_tv_username,nav_tv_logout;
//    RecyclerView recyclerView;
//    CoursesAdapter adapter;
      ImageView iv_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_dashboard);
        drawerLayout= findViewById(R.id.director_drawer_layout);
        setNavigationViewListener();
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nav_tv_username = (TextView) headerView.findViewById(R.id.tv_drawerusername);
        String name= sharedPreferences.getString("name","");
        name=name.toUpperCase();
        nav_tv_username.setText(name);
        iv_menu=(ImageView)findViewById(R.id.iv_Dmenu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        fragment_due fragment0 = new fragment_due();
        FragmentTransaction ft0 = getSupportFragmentManager().beginTransaction();
        ft0.replace(R.id.director_fragment_container, fragment0, "");
        ft0.commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.director_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
    }


    //drawaer menu items selected
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_dDue:
                            fragment_due fragment0 = new fragment_due();
                            FragmentTransaction ft0 = getSupportFragmentManager().beginTransaction();
                            ft0.replace(R.id.director_fragment_container, fragment0, "");
                            ft0.commit();
                            return true;
                        case R.id.nav_dReview:
                            fragment_review fragment1 = new fragment_review();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.director_fragment_container, fragment1, "");
                            ft1.commit();
                            return true;
                        case R.id.nav_dApproved:
                            ApprovedFragment fragment2 = new ApprovedFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.director_fragment_container, fragment2, "");
                            ft2.commit();
                            return true;
                        case R.id.nav_dRejected:
                            RejectedFragment fragment3 = new RejectedFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.director_fragment_container, fragment3, "");
                            ft3.commit();
                            return true;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            };

//    private void setDCourses() {
//        Call<List<course>> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getDCourses();
//        call.enqueue(new Callback<List<course>>() {
//            @Override
//            public void onResponse(Call<List<course>> call, Response<List<course>> response) {
//                if(response.isSuccessful()) {
//                    List<course> res = response.body();
//                    Toast.makeText(director_dashboard.this, "res"+res.size(), Toast.LENGTH_SHORT).show();
//                    adapter=new CoursesAdapter(director_dashboard.this,res);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }
//                else
//                    Toast.makeText(director_dashboard.this, "Invalid Crediential", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<course>> call, Throwable t) {
//                Toast.makeText(director_dashboard.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d("Failed",t.getMessage());
//            }
//        });
//    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_logout: {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(director_dashboard.this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}