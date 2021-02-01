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

public class TeacherCoursesAdapter extends RecyclerView.Adapter<TeacherCoursesAdapter.CoursesListViewHolder> {
    private List<Courses> items;
    private Context context;

    public TeacherCoursesAdapter(Context context, List<Courses> item) {
        this.context = context;
        this.items=item;
    }

    @NonNull
    @Override
    public CoursesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teachercourselist, parent, false);
        return new CoursesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesListViewHolder holder, int position) {
        // create class with getters and setters to initializes

        holder.coursename.setText(items.get(position).getCoursename());
        holder.coursecode.setText(items.get(position).getCoursecode());
        holder.coursecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Teacher_Dashboard.class);
                String c=items.get(position).getCoursename();
                intent.putExtra("code",c);
                context.startActivity(intent);
            }
        });
        holder.coursename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Teacher_Dashboard.class);
                String c=items.get(position).getCoursename();
                intent.putExtra("code",c);
                context.startActivity(intent);            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CoursesListViewHolder extends RecyclerView.ViewHolder{
        TextView coursename, coursecode;
        public CoursesListViewHolder(@NonNull View itemView) {
            super(itemView);
            coursename =itemView.findViewById(R.id.coursename);
            coursecode =itemView.findViewById(R.id.coursecode);
        }
    }
}
