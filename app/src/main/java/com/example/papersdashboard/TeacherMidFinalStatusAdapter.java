package com.example.papersdashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TeacherMidFinalStatusAdapter extends RecyclerView.Adapter<TeacherMidFinalStatusAdapter.CoursesListViewHolder> {
    private List<TeacherMidFinalStatus> items;
    private Context context;

    public TeacherMidFinalStatusAdapter(Context context, List<TeacherMidFinalStatus> item) {
        this.context = context;
        this.items=item;
    }

    @NonNull
    @Override
    public CoursesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approvalofteachers_layout, parent, false);
        return new CoursesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesListViewHolder holder, int position) {
        // create class with getters and setters to initializes


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CoursesListViewHolder extends RecyclerView.ViewHolder{

        public CoursesListViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
