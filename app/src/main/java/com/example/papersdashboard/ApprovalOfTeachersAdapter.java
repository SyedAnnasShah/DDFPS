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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApprovalOfTeachersAdapter extends RecyclerView.Adapter<ApprovalOfTeachersAdapter.MyViewHolder>   {
    private List<Papers> items;
    private Context context;
    String code="",CourseName;
    String CourseCode;
    public ApprovalOfTeachersAdapter(Context context, List<Papers> item) {
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
                    holder.tname.setText(code);
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
        holder.mids.setText(items.get(position).getType());
        holder.finals.setText(items.get(position).getStatus());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tname ,mids,finals;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            /////////////////////
            /////////////////////////
            tname =itemView.findViewById(R.id.course);
            mids =itemView.findViewById(R.id.type);
            finals =itemView.findViewById(R.id.status);

        }
    }
}
