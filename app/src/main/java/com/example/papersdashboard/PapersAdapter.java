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
    String code="";
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
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCodeAndName(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    ResponseBody res = response.body();
                    try {
                        code =res.string();
                        String coden [] = code.split("\"");
                        code=coden[1];
                        holder.coursecode.setText(code);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(context, "Failed to Retrieve any Course" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Failed"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
        }
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
        SharedPreferences sharedPreferences;
        String role;
        ImageView iv_teacher,iv_admin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coursecode =itemView.findViewById(R.id.course);
            type =itemView.findViewById(R.id.type);
            status =itemView.findViewById(R.id.status);
            semester =itemView.findViewById(R.id.semester);
            iv_teacher =itemView.findViewById(R.id.iv_teacher);
            iv_admin =itemView.findViewById(R.id.iv_admin);
            sharedPreferences=context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
            role=sharedPreferences.getString("role","");
        }
    }
}
