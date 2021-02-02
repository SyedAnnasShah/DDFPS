package com.example.papersdashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PapersAdapter extends RecyclerView.Adapter<PapersAdapter.MyViewHolder>   {
    private List<Papers> items;
    private Context context;
    String code="",CourseName;
    String CourseCode;
    public PapersAdapter(Context context, List<Papers> item) {
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
        int id=items.get(position).getCourseid();
        Call<Courses> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCodeAndName(id);
        call.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if(response.isSuccessful()) {
                    Courses res=response.body();
                    code=res.getCoursename();
                    holder.coursecode.setText(code);
                }
                else {
                    Toast.makeText(context, "No Response Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Toast.makeText(context, "Failed"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        int teacherId=items.get(position).getTeacherid();
        Call<List<MembersClass>> call_1 = RetrofitClient
                .getInstance()
                .getApi()
                .getUserName(teacherId);
        call_1.enqueue(new Callback<List<MembersClass>>() {
            @Override
            public void onResponse(Call<List<MembersClass>> call, Response<List<MembersClass>> response) {
                if(response.isSuccessful()) {
                    List<MembersClass> res = response.body();
                    String v0 =res.get(0).getFirstname();
                    String v1 =res.get(0).getLastname();
                    String name= v0 + " " + v1;
                    name= name.toUpperCase();
                    holder.teacherName.setText(name);
                }
                else {
                    Toast.makeText(context, "Failed to Retrieve Name" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MembersClass>> call, Throwable t) {
                Toast.makeText(context, "failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        holder.type.setText(items.get(position).getType());
        holder.status.setText(items.get(position).getStatus());
        holder.semester.setText(items.get(position).getSemester());

        if (holder.role.equals("Admin")) {
            holder.iv_admin.setVisibility(View.VISIBLE);
        }else if (holder.role.equals("Professor")){
             holder.iv_teacher.setVisibility(View.VISIBLE);
        }else if (holder.role.equals("Director")){
            holder.iv_teacher.setVisibility(View.VISIBLE);
            holder.teacher.setVisibility(View.VISIBLE);
            holder.teacherName.setVisibility(View.VISIBLE);
        }
        holder.iv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.role.equals("Admin")){
//                    Intent intent = new Intent(context, Generate_paper.class);
//                    context.startActivity(intent);
                }
            }
        });
        holder.iv_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.role.equals("Professor")){
                    Intent intent = new Intent(context, Generate_paper.class);
                    intent.putExtra("type",items.get(position).getType());
                    intent.putExtra("semester",items.get(position).getSemester());
                    intent.putExtra("courseid",items.get(position).getCourseid());
                    intent.putExtra("paperid",items.get(position).getPaperid());
                    context.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView coursecode ;
        TextView type ;
        TextView status ;
        TextView semester;
        TextView teacherName,teacher;
        SharedPreferences sharedPreferences;
        String role;
        ImageView iv_teacher,iv_admin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coursecode =itemView.findViewById(R.id.course);
            type =itemView.findViewById(R.id.type);
            status =itemView.findViewById(R.id.status);
            semester =itemView.findViewById(R.id.semester);
            teacher= itemView.findViewById(R.id.tv_assignedTeacher);
            teacherName= itemView.findViewById(R.id.tv_assignedTeacherName);
            iv_teacher =itemView.findViewById(R.id.iv_teacher);
            iv_admin =itemView.findViewById(R.id.iv_admin);
            sharedPreferences=context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
            role=sharedPreferences.getString("role","");
        }
    }
}
