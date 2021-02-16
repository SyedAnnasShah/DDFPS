package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaperCorrection extends AppCompatActivity {
    String questionDifficulty="";
    Spinner s_difficulty;
    ImageView back,questionImageRE;
    Button btn_q1,btn_q2,btn_q3,btn_q4,btn_q5,btn_q6,btn_ReSubmit,btn_updateQ;
    EditText et_qdataRE;
    TextView tv_questionaddedmarksRE,tv_questionaddeddificultyRE,tv_questionStatusRE;
    int index=0, pid=1;
    String image;
    Bitmap bitmap;
    List<Questions> ques;
    Questions temporary;
    int PaperRejectStatus=0;
    String comment,data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_correction);
        back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_q1=(Button) findViewById(R.id.b1);
        btn_q2=(Button) findViewById(R.id.b2);
        btn_q3=(Button) findViewById(R.id.b3);
        btn_q4=(Button) findViewById(R.id.b4);
        btn_q5=(Button) findViewById(R.id.b5);
        btn_q6=(Button) findViewById(R.id.b6);
        btn_ReSubmit=(Button) findViewById(R.id.btn_ReSubmit);
        btn_updateQ=(Button) findViewById(R.id.btn_UpdateQuestion);
        tv_questionaddedmarksRE=(TextView) findViewById(R.id.tv_questionaddedmarksRE);
        tv_questionaddeddificultyRE=(TextView) findViewById(R.id.tv_questionaddeddificultyRE);
        tv_questionStatusRE=(TextView) findViewById(R.id.tv_questionStatusRE);
        et_qdataRE=(EditText)findViewById(R.id.et_questiondataRE);
        questionImageRE=(ImageView)findViewById(R.id.iv_questionaddedimageRE);
        s_difficulty = findViewById(R.id.spinnerdifficultyRE);
        String[] items_difficulty = new String[]{"Easy", "Medium", "Hard"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_difficulty);
        s_difficulty.setAdapter(adapter1);
        s_difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Easy"))
                {
                    s_difficulty.setBackgroundColor(Color.GREEN);
                    // do your stuff
                    questionDifficulty="easy";
                }
                else if(selectedItem.equals("Medium"))
                {
                    s_difficulty.setBackgroundColor(Color.YELLOW);
                    // do your stuff
                    questionDifficulty="medium";
                }
                else if(selectedItem.equals("Hard"))
                {
                    s_difficulty.setBackgroundColor(Color.RED);
                    // do your stuff
                    questionDifficulty="hard";
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        Intent intent= getIntent();
        pid=intent.getIntExtra("paperid",0);
        getQuestionsFromDB();
        btn_q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                temporary.questiondata= ques.get(index).questiondata;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                temporary.comments= comment;
                ques.set(index,temporary);
                index=0;
                setDataIntoLayout(index);
            }
        });
        btn_q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                temporary.questiondata= ques.get(index).questiondata;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                temporary.comments= comment;
                ques.set(index,temporary);
                index=1;
                setDataIntoLayout(index);
            }
        });
        btn_q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                temporary.questiondata= ques.get(index).questiondata;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                temporary.comments= comment;
                ques.set(index,temporary);
                index=2;
                setDataIntoLayout(index);
            }
        });
        btn_q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                temporary.questiondata= ques.get(index).questiondata;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                temporary.comments= comment;
                ques.set(index,temporary);
                index=3;
                setDataIntoLayout(index);
            }
        });
        btn_q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                temporary.questiondata= ques.get(index).questiondata;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
                temporary.comments= comment;
                ques.set(index,temporary);
                index=4;
                setDataIntoLayout(index);
            }
        });
        btn_q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions temporary = new Questions();
                temporary.questionid= ques.get(index).questionid;
                temporary.paperid= ques.get(index).paperid;
                temporary.marks= ques.get(index).marks;
                temporary.questiondata= ques.get(index).questiondata;
                temporary.difficulty= ques.get(index).difficulty;
                temporary.image= ques.get(index).image;
                temporary.questionno= ques.get(index).questionno;
//                if(!comment.equals(""))
//                    temporary.comments= data;
//                else
//
                temporary.comments= ques.get(index).comments;
                ques.set(index,temporary);
                index=5;
                setDataIntoLayout(index);
            }
        });
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

                    Toast.makeText(PaperCorrection.this, "size "+ques.size()+" paper id "+pid, Toast.LENGTH_SHORT).show();
                    et_qdataRE.setText(ques.get(index).questiondata);
                    tv_questionaddedmarksRE.setText("Marks: "+String.valueOf(ques.get(index).marks));
                    tv_questionaddeddificultyRE.setText("Difficulty: ( "+ques.get(index).difficulty+" )");
                    //ques=response.body();
                    image=ques.get(index).image;
                    if (image==null){
                        questionImageRE.setVisibility(View.GONE);
                    }else {
                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        questionImageRE.setImageBitmap(decodedByte);
                    }
                }
                else
                    Toast.makeText(PaperCorrection.this, "No Questions Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Questions>> call, Throwable t) {
                Toast.makeText(PaperCorrection.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }
    private void setDataIntoLayout(int index) {
        et_qdataRE.setText(ques.get(index).questiondata);
        tv_questionaddedmarksRE.setText("Marks: "+String.valueOf(ques.get(index).marks));
        tv_questionaddeddificultyRE.setText("Difficulty: ("+ques.get(index).difficulty+")");
        image=ques.get(index).image;
        if (image==null){
            questionImageRE.setVisibility(View.GONE);
        }else {
            questionImageRE.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            questionImageRE.setImageBitmap(decodedByte);
        }

    }
    private void UpdateQuestions(){
        int i=0;
        String st="";
        if(PaperRejectStatus==1)
            st="Rejected";
        else if (PaperRejectStatus==0)
            st="Approved";
        for(;i<ques.size();i++) {
//            Call<ResponseBody> call = RetrofitClient
//                    .getInstance()
//                    .getApi()
//                    .getUpdateQuestionP(ques.get(i).questionno,ques.get(i).questiondata,ques.get(i).difficulty,ques.get(i).image,ques.get(i).paperid,ques.get(i).status,ques.get(i).marks,ques.get(i).comments,st);
//            //questions.get(i).paperid, questions.get(i).questionno, questions.get(i).questiondata, questions.get(i).difficulty, questions.get(i).image, questions.get(i).paperid, "NULL",questions.get(i).marks
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        try {
//                            String res = response.body().string();
//                        } catch (IOException ioException) {
//                            ioException.printStackTrace();
//                        }
//
//                    } else {
//                        try {
//                            Toast.makeText(PaperCorrection.this, "Failed " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(PaperCorrection.this, "Failed  " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.d("Failed", t.getMessage());
//                }
//            });
            Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
        }
    }
}