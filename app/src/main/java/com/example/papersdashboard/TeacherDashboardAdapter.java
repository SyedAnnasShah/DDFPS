package com.example.papersdashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TeacherDashboardAdapter extends RecyclerView.Adapter<TeacherDashboardAdapter.MyViewHolder>   {
    private List<Papers> items;
    private Context context;
    String name="";
    String midDetail,finaldetail;
    int tid;
    public TeacherDashboardAdapter(Context context, List<Papers> item) {
        this.context = context;
        this.items=item;
    }
    @NonNull
    @Override
    public TeacherDashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teachercourses_layout, parent, false);
        return new TeacherDashboardAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TeacherDashboardAdapter.MyViewHolder holder, int position) {
        int id=items.get(position).getCourseid();
        finaldetail="FINAl"+"\n ("+items.get(position).getStatus()+" )";
        midDetail="MID"+"\n (Approved)";
        holder.btn_finaldetails.setText(finaldetail);
        holder.btn_middetails.setText(midDetail);
        Call<Courses> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCodeAndName(id);
        call.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if(response.isSuccessful()) {
                    Courses res=response.body();
                   // name=res.getCoursename();
                    holder.coursename.setText(res.getCoursename());
                    holder.coursecode.setText(res.getCoursecode());
                    Call<MembersClass> call1 = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getMemberType(tid);
                    call1.enqueue(new Callback<MembersClass>() {
                        @Override
                        public void onResponse(Call<MembersClass> call, Response<MembersClass> response) {
                            if(response.isSuccessful()) {
                                MembersClass res = response.body();
                                String fname= res.getFirstname();
                                String lname = res.getLastname();
                                holder.teachernameFirst.setText(fname.toUpperCase());
                                holder.teachernameLast.setText(lname.toUpperCase());
                            }
                            else {
                                try {
                                    Toast.makeText(context, "Failed "+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MembersClass> call, Throwable t) {
                            Toast.makeText(context, "Failed to get name "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Failed",t.getMessage());
                        }
                    });

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



//
//        int teacherId=items.get(position).getTeacherid();
//        Call<List<MembersClass>> call_1 = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getUserName(teacherId);
//        call_1.enqueue(new Callback<List<MembersClass>>() {
//            @Override
//            public void onResponse(Call<List<MembersClass>> call, Response<List<MembersClass>> response) {
//                if(response.isSuccessful()) {
//                    List<MembersClass> res = response.body();
//                    String v0 =res.get(0).getFirstname();
//                    String v1 =res.get(0).getLastname();
//                    String name= v0 + " " + v1;
//                    name= name.toUpperCase();
//                    holder.teacherName.setText(name);
//                }
//                else {
//                    Toast.makeText(context, "Failed to Retrieve Name" , Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MembersClass>> call, Throwable t) {
//                Toast.makeText(context, "failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.btn_finaldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.get(position).getStatus().equals("Due")) {
                    Intent intent = new Intent(context, Generate_paper.class);
                    intent.putExtra("type", items.get(position).getType());
                    intent.putExtra("semester", items.get(position).getSemester());
                    intent.putExtra("courseid", items.get(position).getCourseid());
                    intent.putExtra("paperid", items.get(position).getPaperid());
                    context.startActivity(intent);
                }else if (items.get(position).getStatus().equals("Rejected")){
                    Intent intent = new Intent(context, PaperCorrection.class);
                    intent.putExtra("type", items.get(position).getType());
                    intent.putExtra("semester", items.get(position).getSemester());
                    intent.putExtra("courseid", items.get(position).getCourseid());
                    intent.putExtra("paperid", items.get(position).getPaperid());
                    context.startActivity(intent);
                }
            }
        });
        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPaperDetails.class);
                intent.putExtra("type", items.get(position).getType());
                intent.putExtra("semester", items.get(position).getSemester());
                intent.putExtra("courseid", items.get(position).getCourseid());
                intent.putExtra("paperid", items.get(position).getPaperid());
                intent.putExtra("pid", items.get(position).getPaperid());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView coursecode ;
        TextView coursename ;
        TextView teachernameFirst,teachernameLast ;
        SharedPreferences sharedPreferences;
        String username;
        Button btn_details,btn_middetails,btn_finaldetails;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coursecode =itemView.findViewById(R.id.tv_coursecode_tcl);
            coursename =itemView.findViewById(R.id.tv_coursename_tcl);
            teachernameFirst= itemView.findViewById(R.id.tv_responsibleteacherFirst);
            teachernameLast= itemView.findViewById(R.id.tv_responsibleteacherLast);
            btn_details =itemView.findViewById(R.id.btn_morepaperdetails);
            btn_middetails =itemView.findViewById(R.id.btn_middetails);
            btn_finaldetails =itemView.findViewById(R.id.btn_finaldetails);
            sharedPreferences=context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
            //username=sharedPreferences.getString("name","No Name set");
            tid=sharedPreferences.getInt("id",0);
        }
    }
}

