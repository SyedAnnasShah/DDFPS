package com.example.papersdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_requested extends Fragment {
    RecyclerView recyclerView;
    PapersAdapter adapter;
    SharedPreferences sharedPreferences;
    int id=0;
    String role,Professor="Professor";
    public fragment_requested() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requested, container, false);

        recyclerView = view.findViewById(R.id.recycler_req);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPreferences= getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        role=sharedPreferences.getString("role","");
        if(role.equals(Professor)){
            id=sharedPreferences.getInt("id",0);
            Toast.makeText(getContext(), "if  Role "+role + "ID  "+id, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getContext(), "else Role "+role + "ID  "+id, Toast.LENGTH_LONG).show();
        setCourses();


        // Inflate the layout for this fragment
        return view;
    }
    private void setCourses() {
        Call<List<Papers>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getPapers("Requested",id);
        call.enqueue(new Callback<List<Papers>>() {
            @Override
            public void onResponse(Call<List<Papers>> call, Response<List<Papers>> response) {
                if(response.isSuccessful()) {
                    List<Papers> res = response.body();
                    Toast.makeText(getContext(), "res"+res.size(), Toast.LENGTH_SHORT).show();
                    adapter=new PapersAdapter(getActivity(),res);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getContext(), "No Course Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Papers>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }
}