package com.example.papersdashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Generate_paper extends AppCompatActivity {
    Dialog myDialog;
    ArrayList<Questions> questions = new ArrayList<Questions>();
    Questions que;
    String Q="Q : ",Qno;
    int questionNumberBeingAdded=1 , indexoflist=0, markss;
    TextView addedques, hard_added,medium_added,easy_added,tv_coursename,tv_coursecode ,tv_coursenamehere,tv_papertype,tv_credithours,tv_section,tv_markssummary;
    EditText addingques , et_marks;
    String questionDifficulty="" ,storingImage="" , section;
    Spinner s_difficulty;
    Button btn_addQuestion, btn_generatePaper;
    private static final int image=1;
    ImageView back,iv_image, iv_addbullet, iv_addImage;
    Bitmap bitmap;
    int e=0,m=0,h=0;
    String n;
    String typ, sem, cname,ccode, StringCRS, fullsection;
    int cidd, pid , enteredmarks;
    SharedPreferences sharedPreferences;
    AddQuestionAdapter adapter;
    RecyclerView recycler_questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_paper);

        recycler_questions=findViewById(R.id.recycler_questions);

        recycler_questions.setHasFixedSize(true);
        recycler_questions.setLayoutManager(new LinearLayoutManager(this));

        myDialog = new Dialog(this);

        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        // to set dialog(paper details) at top
        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


//        actionBar=getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        // getting info from prevoius activity(papers adapter) to autofill the felds of generate paper
        Intent intent= getIntent();
        typ=intent.getStringExtra("type");
        sem=intent.getStringExtra("semester");
        cidd=intent.getIntExtra("courseid",0);
        pid=intent.getIntExtra("paperid",0);
        //cname=intent.getStringExtra("coursename");

        tv_coursename=findViewById(R.id.tv_coursename);
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
                    tv_coursename.setText(cname);
                    StringCRS=String.valueOf(crs);
                }
                else {
                    Toast.makeText(Generate_paper.this, "No Response Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Toast.makeText(Generate_paper.this, "Failed"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
//
//        hard_added=findViewById(R.id.tv_hard_added);
//        medium_added=findViewById(R.id.tv_medium_added);
//        easy_added=findViewById(R.id.tv_easy_added);

        //tv_coursename=findViewById(R.id.tv_coursename);
        //tv_coursename.setText(cname);
        // it was showing null here cause this code is executed at last so i shifted the set course name above in call

//        tv_papertype=findViewById(R.id.tv_papertype);
//        tv_papertype.setText(typ);
//        //tv_credithours=findViewById(R.id.tv_credithours);
//        //tv_credithours.setText("4 Credit Hours");
//        tv_section=findViewById(R.id.tv_section);
//        setCourseCode(cidd);

        et_marks=findViewById(R.id.et_EnterMarks);
        iv_image=findViewById(R.id.iv_image);
        back=findViewById(R.id.backBtn);
//        addedques=findViewById(R.id.addedques);
        addingques=findViewById(R.id.addingques);
        addingques.setMovementMethod(new ScrollingMovementMethod());
        s_difficulty = findViewById(R.id.spinnerdifficulty);
        String[] items_difficulty = new String[]{"Easy", "Medium", "Hard"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_difficulty);
        iv_addbullet=findViewById(R.id.iv_addBullet);
        iv_addbullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getquestion = addingques.getText().toString();
                addingques.setText(getquestion+"\n"+"\t"+"◎ ");
            }
        });

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
        iv_addImage=findViewById(R.id.iv_addImage);
        iv_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                // this image varible has 1 stored in it which tells how many pics are to be picked
                startActivityForResult(photoPickerIntent,image);
            }
        });


        btn_addQuestion =findViewById(R.id.btn_addques);
        btn_addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bitmap!=null){
                    storingImage=convertToString();
                }else if(storingImage==null){
                    storingImage="";
                }
                if(questionDifficulty=="easy"){
                    e++;
//                    easy_added.setText(String.valueOf(e));
                }else if(questionDifficulty=="medium"){
                    m++;
//                    medium_added.setText(String.valueOf(m));
                }else if(questionDifficulty=="hard"){
                    h++;
//                    hard_added.setText(String.valueOf(h));
                }
//                String o = addedques.getText().toString();
                Qno = Q.substring(0,1)+ questionNumberBeingAdded +Q.substring(1,3);
                n = addingques.getText().toString();
//                addedques.setText(o+"\n"+Qno+n);
                addingques.setText("");
                //AddQuestion(n,storingImage);

                if (et_marks.getText().toString()==""){
                    Toast.makeText(Generate_paper.this, "Enter Marks", Toast.LENGTH_LONG).show();
                    // call alert dialouge here
                    return;
                }
                else{
                    markss= Integer.parseInt(et_marks.getText().toString());
//                    enteredmarks=Integer.parseInt(tv_markssummary.getText().toString());
//                    enteredmarks=enteredmarks+markss;
//                    tv_markssummary.setText(String.valueOf(enteredmarks));
                }
                que=new Questions();
                que.marks=markss;
                que.questionno=Qno;
                que.image=storingImage;
                que.paperid=pid;
                que.difficulty=questionDifficulty;
                que.questiondata=n;
                questions.add(que);
                indexoflist=indexoflist+1;
                questionNumberBeingAdded=questionNumberBeingAdded+1;

                adapter=new AddQuestionAdapter(Generate_paper.this,questions);
                recycler_questions.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                //now clear every feild
                iv_image.invalidate();
                iv_image.setImageBitmap(null);
                et_marks.setText("");
            }
        });
        btn_generatePaper =findViewById(R.id.btn_generatePaper);
        btn_generatePaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddQuestions();
                changeStatusOfpaperToReview(pid);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void AddQuestions(){
        int i=0;
        for(;i<questions.size();i++) {
            Toast.makeText(this, ""+questions.get(i).questionno+questions.get(i).questiondata+questions.get(i).difficulty+questions.get(i).image+questions.get(i).paperid+questions.get(i).marks, Toast.LENGTH_LONG).show();
            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .AddQuestion(questions.get(i).questionno,questions.get(i).questiondata,questions.get(i).difficulty,questions.get(i).image,questions.get(i).paperid,questions.get(i).marks);
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
                            Toast.makeText(Generate_paper.this, "Failed " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Generate_paper.this, "Failed  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Failed", t.getMessage());
                }
            });
            Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
        }
    }// on create body

    private void  changeStatusOfpaperToReview(int pid){

    }

    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                iv_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Generate_paper.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void setCourseCode(int cid) {
        Call<Courses> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCodeAndName(cid);
        call.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if(response.isSuccessful()) {
                    Courses res=response.body();
//                    tv_section.setText(res.getCoursecode());
                }
                else {
                    Toast.makeText(Generate_paper.this, "No Response Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {

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