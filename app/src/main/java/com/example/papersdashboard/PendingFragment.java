package com.example.papersdashboard;

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


public class PendingFragment extends Fragment {

    RecyclerView recyclerView;
    CoursesAdapter adapter;

    public PendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pending, container, false);
        recyclerView = view.findViewById(R.id.recycler_p);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setCourses();

        return view;
    }

    private void setCourses() {
        Call<List<course>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCourses("Pending");
        call.enqueue(new Callback<List<course>>() {
            @Override
            public void onResponse(Call<List<course>> call, Response<List<course>> response) {
                if(response.isSuccessful()) {
                    List<course> res = response.body();
                    Toast.makeText(getContext(), "res"+res.size(), Toast.LENGTH_SHORT).show();
                    adapter=new CoursesAdapter(getActivity(),res);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getContext(), "Invalid Crediential", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<course>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }
}