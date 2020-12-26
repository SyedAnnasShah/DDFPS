package com.example.papersdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder>   {
    private List<course> items;
    private Context context;

    public CoursesAdapter(Context context, List<course> item) {
        this.context = context;
        this.items=item;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.course_id.setText(items.get(position).getCourse_id());
        holder.date_created.setText(items.get(position).getCourse_datecreated());
        holder.version.setText(items.get(position).getCourse_version());
        holder.status.setText(items.get(position).getCourse_status());
        holder.semester.setText(items.get(position).getCourse_semester());




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView course_id ;
        TextView date_created ;
        TextView version ;
        TextView status ;
        TextView semester;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_id =itemView.findViewById(R.id.course);
            date_created =itemView.findViewById(R.id.date);
            version =itemView.findViewById(R.id.version);
            status =itemView.findViewById(R.id.status);
            semester =itemView.findViewById(R.id.semester);
        }
    }
}
