package com.example.papersdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast.makeText(context, ""+holder.role, Toast.LENGTH_SHORT).show();

        if (holder.role.equals("Admin"))
        {

            holder.iv_admin.setVisibility(View.VISIBLE);

        }else if (holder.role.equals("Professor")){

             holder.iv_teacher.setVisibility(View.VISIBLE);
        }else if (holder.role.equals("Director")){
            holder.iv_teacher.setVisibility(View.VISIBLE);
        }

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
        SharedPreferences sharedPreferences;
        String role;
        ImageView iv_teacher,iv_admin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_id =itemView.findViewById(R.id.course);
            date_created =itemView.findViewById(R.id.date);
            version =itemView.findViewById(R.id.version);
            status =itemView.findViewById(R.id.status);
            semester =itemView.findViewById(R.id.semester);
            iv_teacher =itemView.findViewById(R.id.iv_teacher);
            iv_admin =itemView.findViewById(R.id.iv_admin);

            sharedPreferences=context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
            role=sharedPreferences.getString("role","");
        }
    }
}
