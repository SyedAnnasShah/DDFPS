package com.example.papersdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class admin_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
//    RecyclerView recyclerView;
//    CoursesAdapter adapter;
    ImageView iv_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        drawerLayout= findViewById(R.id.admin_drawer_layout);
        iv_menu=(ImageView)findViewById(R.id.iv_Amenu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        PendingFragment fragment1=new PendingFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.admin_fragment_container,fragment1,"");
        ft1.commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.admin_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

//        recyclerView =findViewById(R.id.admin_recycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        setACourses();
    }
    //////////////////////////// frag
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_aPending:
                            PendingFragment fragment1 = new PendingFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.admin_fragment_container, fragment1, "");
                            ft1.commit();
                            return true;
/////////////////////////// this is supposed to be printed fragment but for testing i am using rejected
                        case R.id.nav_aPrinted:
                            RejectedFragment fragment2 = new RejectedFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.admin_fragment_container, fragment2, "");
                            ft2.commit();
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

//    private void setACourses() {
//        Call<List<course>> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getACourses();
//        call.enqueue(new Callback<List<course>>() {
//            @Override
//            public void onResponse(Call<List<course>> call, Response<List<course>> response) {
//                if(response.isSuccessful()) {
//                    List<course> res = response.body();
//                    Toast.makeText(admin_dashboard.this, "res"+res.size(), Toast.LENGTH_SHORT).show();
//                    adapter=new CoursesAdapter(admin_dashboard.this,res);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }
//                else {
//                    try {
//                        Toast.makeText(admin_dashboard.this, "No Reponse" + response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<course>> call, Throwable t) {
//                Toast.makeText(admin_dashboard.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d("Failed",t.getMessage());
//            }
//        });
//    }


    public void ClickMenu(View view) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}