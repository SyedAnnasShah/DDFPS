package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPaperDetails extends AppCompatActivity {


    TeacherMidFinalStatusAdapter teacherMidFinalStatusAdapter;
    RecyclerView recycler_teachersapproval;
    TextView code,name,status,date;
    int paperid;
    Dialog myDialog;
    TextView tv_coursecode ,tv_coursenamehere,tv_papertype,tv_credithours,tv_section,tv_markssummary;
    //addedques, hard_added,medium_added,easy_added,tv_coursename,
    String typ, sem, cname,ccode, StringCRS, section;
    int cidd, pid;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_paper_details);

        myDialog = new Dialog(this);
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        recycler_teachersapproval=findViewById(R.id.recycler_teachersapproval);


        recycler_teachersapproval.setHasFixedSize(true);
        recycler_teachersapproval.setLayoutManager(new LinearLayoutManager(this));

        // to set dialog(paper details) at top
        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        Intent intent= getIntent();
        typ=intent.getStringExtra("type");
        sem=intent.getStringExtra("semester");
        cidd=intent.getIntExtra("courseid",0);
        pid=intent.getIntExtra("paperid",0);

        back=(ImageView) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        code=(TextView)findViewById(R.id.tv_coursecode_sh);
        name=(TextView)findViewById(R.id.tv_coursename_sh);
        status=(TextView)findViewById(R.id.tv_status_sh);
        //date=(TextView)findViewById(R.id.tv_datecreated_sh);
        //Intent intent= getIntent();
        paperid=intent.getIntExtra("pid",0);

        Toast.makeText(this, "pid"+paperid, Toast.LENGTH_SHORT).show();
        setCoursenamecodestauts(paperid);

        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        int tid=sharedPreferences.getInt("id",0);

        getTeacherMidFinal(tid,ccode);

    }

    private void getTeacherMidFinal(int tid, String ccode) {


        Call<List<TeacherMidFinalStatus>> call1 = RetrofitClient
                .getInstance()
                .getApi()
                .getTeacherMidFinal(tid,ccode);
        call1.enqueue(new Callback<List<TeacherMidFinalStatus>>() {
            @Override
            public void onResponse(Call<List<TeacherMidFinalStatus>> call, Response<List<TeacherMidFinalStatus>> response) {
                if(response.isSuccessful()) {
                    List<TeacherMidFinalStatus> res=response.body();

                   teacherMidFinalStatusAdapter=new TeacherMidFinalStatusAdapter(ShowPaperDetails.this,res);
                   recycler_teachersapproval.setAdapter(teacherMidFinalStatusAdapter);
                   teacherMidFinalStatusAdapter.notifyDataSetChanged();

                }
                else {
                    try {
                        Toast.makeText(ShowPaperDetails.this, "No Response Found"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TeacherMidFinalStatus>> call, Throwable t) {
                Toast.makeText(ShowPaperDetails.this, "Failed"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setCoursenamecodestauts(int paperid) {
        Call<Papers> call = RetrofitClient
                .getInstance()
                .getApi()
                .getPaperViaPaperID(paperid);
        call.enqueue(new Callback<Papers>() {
            @Override
            public void onResponse(Call<Papers> call, Response<Papers> response) {
                if(response.isSuccessful()) {
                    Papers res = response.body();
                    status.setText(res.getStatus());
                    Toast.makeText(ShowPaperDetails.this, "Status "+ res.getStatus(), Toast.LENGTH_SHORT).show();

                    Call<Courses> call1 = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getCodeAndName(res.getCourseid());
                    call1.enqueue(new Callback<Courses>() {
                        @Override
                        public void onResponse(Call<Courses> call, Response<Courses> response) {
                            if(response.isSuccessful()) {
                                Courses res=response.body();
                                name.setText(res.getCoursename());
                                cname=res.getCoursename();
                                Toast.makeText(ShowPaperDetails.this, "Name "+ res.getCoursename(), Toast.LENGTH_SHORT).show();
                                code.setText(res.getCoursecode());
                                ccode=res.getCoursecode();
                                Toast.makeText(ShowPaperDetails.this, "code "+ res.getCoursecode(), Toast.LENGTH_SHORT).show();
                                StringCRS=String.valueOf(res.getCrs());
                            }
                            else {
                                try {
                                    Toast.makeText(ShowPaperDetails.this, "No Response Found"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Courses> call, Throwable t) {
                            Toast.makeText(ShowPaperDetails.this, "Failed"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    try {
                        Toast.makeText(ShowPaperDetails.this, "Nothing found"+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Papers> call, Throwable t) {
                Toast.makeText(ShowPaperDetails.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });

    }

    public void ShowPopup(View v) {
        Button btnHide;
        myDialog.setContentView(R.layout.paperdetails_layout);
        btnHide =(Button) myDialog.findViewById(R.id.btn_hide);
        //btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });



        tv_coursenamehere=(TextView) myDialog.findViewById(R.id.tv_coursenamehere);
        tv_credithours=(TextView) myDialog.findViewById(R.id.tv_credithours);
        tv_markssummary=(TextView) myDialog.findViewById(R.id.tv_totalmarkssummary);
        tv_section=(TextView) myDialog.findViewById(R.id.tv_section);
        tv_papertype=(TextView) myDialog.findViewById(R.id.tv_papertype);
        tv_coursecode=(TextView) myDialog.findViewById(R.id.tv_coursecode);

        tv_coursecode.setText(ccode);
        tv_papertype.setText(typ);
        tv_section.setText(sem);
        Toast.makeText(this, ""+section, Toast.LENGTH_SHORT).show();
        //tv_credithours.setText("4 Credit Hours");
        tv_coursenamehere.setText(cname);
        //StringCRS=StringCRS+"Crs";
        tv_credithours.setText("");
        tv_credithours.setText(StringCRS+"Crs");
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}