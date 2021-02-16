package com.example.papersdashboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComments extends AppCompatActivity {
    ImageView back, questionImage;
    Button btn_approvePaper,btn_returnfrompaper,btn_acceptQuestion,btn_rejectQuestion;
    Button btn_q1,btn_q2,btn_q3,btn_q4,btn_q5,btn_q6;
    EditText et_addedComments, et_questiondatadb;
    TextView tv_questionaddedmarksdb,tv_questionaddeddificultydb,tv_questionStatus;
    TextView tv_coursenamehere, tv_credithours, tv_markssummary, tv_section, tv_papertype, tv_coursecode;
    int index=0, pid=1;
    String image, role;
    List<Questions> ques;
    int PaperRejectStatus=0;
    String comment,data;
    Dialog myDialog;
    String typ, sem, cname,ccode, StringCRS;
    int cidd, tid=0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comments);
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        role= sharedPreferences.getString("role","");
        if(role.equals("Professor")){
            tid=sharedPreferences.getInt("id",0);
        }
        myDialog = new Dialog(this);
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        // to set dialog(paper details) at top
        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        // getting info from prevoius activity(papers adapter) to autofill the felds of generate paper
        Intent intent= getIntent();
        typ=intent.getStringExtra("type");
        sem=intent.getStringExtra("semester");
        cidd=intent.getIntExtra("courseid",0);
        pid=intent.getIntExtra("paperid",0);
        //cname=intent.getStringExtra("coursename");
        Call<Courses> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCodeAndName(cidd);
        call.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if(response.isSuccessful()) {
                    Courses res=response.body();
                    cname = res.getCoursename();
                    ccode = res.getCoursecode();
                    int crs = res.getCrs();
                    StringCRS=String.valueOf(crs);
                }
                else {
                    Toast.makeText(AddComments.this, "No Response Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Toast.makeText(AddComments.this, "Failed"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_approvePaper=(Button) findViewById(R.id.btn_approvePaper);
        btn_returnfrompaper=(Button) findViewById(R.id.btn_returnFromPaper);
        btn_acceptQuestion=(Button) findViewById(R.id.btn_AcceptQuestion);
        btn_rejectQuestion=(Button) findViewById(R.id.btn_RejectQuestion);
        btn_q1=(Button) findViewById(R.id.btn_q1);
        btn_q2=(Button) findViewById(R.id.btn_q2);
        btn_q3=(Button) findViewById(R.id.btn_q3);
        btn_q4=(Button) findViewById(R.id.btn_q4);
        btn_q5=(Button) findViewById(R.id.btn_q5);
        btn_q6=(Button) findViewById(R.id.btn_q6);
        et_addedComments=(EditText)findViewById(R.id.et_AddedCommentsArea);
        et_questiondatadb=(EditText) findViewById(R.id.tv_questiondatadb);
        tv_questionaddedmarksdb=(TextView)findViewById(R.id.tv_questionaddedmarksdb);
        tv_questionaddeddificultydb=(TextView)findViewById(R.id.tv_questionaddeddificultydb);
        tv_questionStatus=(TextView)findViewById(R.id.tv_questionStatus);
        questionImage=(ImageView)findViewById(R.id.iv_questionaddedimagedb);
        back=(ImageView)findViewById(R.id.backBtn);

        Intent intent1= getIntent();
        pid=intent1.getIntExtra("paperid",0);
        getQuestionsFromDB();

        btn_acceptQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_questionStatus.setText("Accepted");
                PaperRejectStatus=0;
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                if(data.equals(""))
                    temporary.questiondata= ques.get(index).questiondata;
                else
                    temporary.questiondata= data;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.status= "Accepted";
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                temporary.comments= ques.get(index).comments;
                ques.set(index,temporary);
            }
        });
        btn_rejectQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaperRejectStatus=1;
                tv_questionStatus.setText("(Rejected)");
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                if(data.equals(""))
                    temporary.questiondata= ques.get(index).questiondata;
                else
                    temporary.questiondata= data;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.status= "Rejected";
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                if(comment.equals(""))
                    temporary.comments= ques.get(index).comments;
                else
                    temporary.comments= comment;
                ques.set(index,temporary);
            }
        });
        btn_approvePaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals("Professor")){
                    if(PaperRejectStatus==1){
                        Toast.makeText(AddComments.this, "Can not Approve paper one or more Quetions are Rejected", Toast.LENGTH_SHORT).show();
                        // change status to rejected and open director dashboard
                    }
                    else {
                        Toast.makeText(AddComments.this, "Paper Approved", Toast.LENGTH_SHORT).show();
                        //change status to accepteed and open director dashboard
                        for( int i =0;i<ques.size();i++){
                            if(ques.get(i).status.equals(1)){
                                PaperRejectStatus=1;
                            }
                        }
                        UpdateQuestionsP();
                    }

                }else if (role.equals("Director")){
                    if(PaperRejectStatus==1){
                        Toast.makeText(AddComments.this, "Can not Approve paper one or more Quetions are Rejected", Toast.LENGTH_SHORT).show();
                        // change status to rejected and open director dashboard
                    }
                    else {
                        Toast.makeText(AddComments.this, "Paper Approved", Toast.LENGTH_SHORT).show();
                        //change status to accepteed and open director dashboard
                        UpdateQuestionsD();
                    }
                }
            }
        });
        btn_returnfrompaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals("Professor")){
                    if(PaperRejectStatus==1){
                        Toast.makeText(AddComments.this, "Paper Rejected", Toast.LENGTH_SHORT).show();
                        // change status to rejected and open director dashboard
                        UpdateQuestionsP();
                    }

                }else if (role.equals("Director")){
                    if(PaperRejectStatus==1){
                        Toast.makeText(AddComments.this, "Paper Rejected", Toast.LENGTH_SHORT).show();
                        // change status to rejected and open director dashboard
                        UpdateQuestionsD();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_questiondatadb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    data=et_questiondatadb.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });
        et_addedComments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                comment =et_addedComments.getText().toString();
            }
        });
        btn_q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAndAddInList();
                index=0;
                setDataIntoLayout(index);
            }
        });
        btn_q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAndAddInList();
                index=1;
                setDataIntoLayout(index);
            }
        });
        btn_q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAndAddInList();
                index=2;
                setDataIntoLayout(index);
            }
        });
        btn_q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAndAddInList();
                index=3;
                setDataIntoLayout(index);
            }
        });
        btn_q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAndAddInList();
                index=4;
                setDataIntoLayout(index);
            }
        });
        btn_q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAndAddInList();
                index=5;
                setDataIntoLayout(index);
            }
        });
    }

    private void setDataAndAddInList() {
        et_addedComments.setText("");
        tv_questionStatus.setText(ques.get(index).status);
        Questions temporary = new Questions();
        temporary.questionid= ques.get(index).questionid;
        temporary.paperid= ques.get(index).paperid;
        temporary.marks= ques.get(index).marks;
//        if(data.equals(""))
//            temporary.questiondata= ques.get(index).questiondata;
//        else
//            temporary.questiondata= data;
        temporary.questiondata= data;
        temporary.difficulty= ques.get(index).difficulty;
        temporary.status=ques.get(index).status;
        temporary.image= ques.get(index).image;
        temporary.questionno= ques.get(index).questionno;
//        if(comment.equals(""))
//            temporary.comments= ques.get(index).comments;
//        else
//            temporary.comments= comment;
        temporary.comments= comment;
        ques.set(index,temporary);
    }

    private void setDataIntoLayout(int index) {
        et_questiondatadb.setText(ques.get(index).questiondata);
        tv_questionaddedmarksdb.setText("Marks: "+String.valueOf(ques.get(index).marks));
        tv_questionaddeddificultydb.setText("Difficulty: ("+ques.get(index).difficulty+")");
        image=ques.get(index).image;
//        if(!ques.get(index).status.equals(null))
//            tv_questionStatus.setText(ques.get(index).status);
//        else
//            tv_questionStatus.setText("");
        tv_questionStatus.setText(ques.get(index).status);
        if (image==null){
            questionImage.setVisibility(View.GONE);
        }else {
            questionImage.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            questionImage.setImageBitmap(decodedByte);
        }

    }

    private void getQuestionsFromDB() {
        Call<List<Questions>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getQuestionsForComments(pid);
        call.enqueue(new Callback<List<Questions>>() {
            @Override
            public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                if(response.isSuccessful()) {
                    //List<Questions> res = response.body();
                    ques = response.body();

                    et_questiondatadb.setText(ques.get(index).questiondata);
                    tv_questionaddedmarksdb.setText("Marks: "+String.valueOf(ques.get(index).marks));
                    tv_questionaddeddificultydb.setText("Difficulty: ( "+ques.get(index).difficulty+" )");
                    //ques=response.body();
                    tv_questionStatus.setText(ques.get(index).status);
                    image=ques.get(index).image;
                    if (image==null){
                        questionImage.setVisibility(View.GONE);
                    }else {
                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        questionImage.setImageBitmap(decodedByte);
                    }
                }
                else
                    Toast.makeText(AddComments.this, "No Questions Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Questions>> call, Throwable t) {
                Toast.makeText(AddComments.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }

    //make it update questions
    private void UpdateQuestionsD(){
        int i=0;
        String st="";
        if(PaperRejectStatus==1)
            st="Rejected";
        else if (PaperRejectStatus==0)
            st="Approved";
        for(;i<ques.size();i++) {

            Toast.makeText(this, ""+ ques.get(i).questionid +ques.get(i).questiondata+ques.get(i).paperid+ques.get(i).status+ques.get(i).comments+st, Toast.LENGTH_SHORT).show();
            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getUpdateQuestionsAfterReview(ques.get(i).questionid,ques.get(i).questiondata,ques.get(i).comments,ques.get(i).paperid,ques.get(i).status,st);
            //questions.get(i).paperid, questions.get(i).questionno, questions.get(i).questiondata, questions.get(i).difficulty, questions.get(i).image, questions.get(i).paperid, "NULL",questions.get(i).marks
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String res = response.body().string();
                            Toast.makeText(AddComments.this, ""+res, Toast.LENGTH_SHORT).show();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    } else {
                        Toast.makeText(AddComments.this, "Failed else " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(AddComments.this, "Failed  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Failed", t.getMessage());
                }
            });
        }
    }
    private void UpdateQuestionsP(){
        int i=0;
        String st="";
        if(PaperRejectStatus==1)
            st="Rejected";
        else if (PaperRejectStatus==0)
            st="Approved";
        for(;i<ques.size();i++) {
            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getUpdateQuestionP(ques.get(i).questionno,ques.get(i).questiondata,ques.get(i).difficulty,ques.get(i).image,ques.get(i).paperid,ques.get(i).status,ques.get(i).marks,ques.get(i).comments,st,tid);
            //questions.get(i).paperid, questions.get(i).questionno, questions.get(i).questiondata, questions.get(i).difficulty, questions.get(i).image, questions.get(i).paperid, "NULL",questions.get(i).marks
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String res = response.body().string();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    } else {
                        try {
                            Toast.makeText(AddComments.this, "Failed " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(AddComments.this, "Failed  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Failed", t.getMessage());
                }
            });
            Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
        }
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
        //Toast.makeText(this, ""+section, Toast.LENGTH_SHORT).show();
        //tv_credithours.setText("4 Credit Hours");
        tv_coursenamehere.setText(cname);
        //StringCRS=StringCRS+"Crs";
        tv_credithours.setText("");
        tv_credithours.setText(StringCRS+"Crs");
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}