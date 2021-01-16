package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class teachercard extends AppCompatActivity {
    RecyclerView recyclerView;
    SessionsAdapter adapter;
    SharedPreferences sharedPreferences;
    int idofteacher;
    Button gotoGeneratePaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachercard);
        recyclerView = findViewById(R.id.recycler_session);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        idofteacher=sharedPreferences.getInt("id",0);
        getingSessions();
        gotoGeneratePaper=findViewById(R.id.btn_gotoGeneratePaper);
        gotoGeneratePaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(teachercard.this,Generate_paper.class);
                startActivity(i);
            }
        });
    }
    private void getingSessions() {
        Call<List<Papers>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getSession(idofteacher);
        call.enqueue(new Callback<List<Papers>>() {
            @Override
            public void onResponse(Call<List<Papers>> call, Response<List<Papers>> response) {
                if(response.isSuccessful()) {
                    List<Papers> res = response.body();
                    adapter=new SessionsAdapter(teachercard.this,res);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(teachercard.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Papers>> call, Throwable t) {
                Toast.makeText(teachercard.this, "On Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {

    }
}