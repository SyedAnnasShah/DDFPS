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
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_due extends Fragment {
    RecyclerView recyclerView;
    PapersAdapter adapter;
    SharedPreferences sharedPreferences;
    int id=0;
    String code,role,Professor="Professor";

    public fragment_due() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_due, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPreferences= getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        role=sharedPreferences.getString("role","");
//        if(role.equals(Professor)){
//            id=sharedPreferences.getInt("id",0);
//        }

        setCourses();
        return view;
    }
    private void setCourses() {
        Call<List<Papers>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getPapers("Due",id);
        call.enqueue(new Callback<List<Papers>>() {
            @Override
            public void onResponse(Call<List<Papers>> call, Response<List<Papers>> response) {
                if(response.isSuccessful()) {
                    List<Papers> res = response.body();
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