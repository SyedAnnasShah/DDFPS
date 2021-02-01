package com.example.papersdashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Generate_paper extends AppCompatActivity {
    TextView addedques, hard_added,medium_added,easy_added,tv_coursename,tv_papertype,tv_credithours,tv_section;
    EditText addingques;
    String questionDifficulty="" ,storingImage="" , section;
    Spinner s_difficulty;
    Button btn_addQuestion, btn_generatePaper,btn_addImage;
    private static final int image=1;
    ImageView back,iv_image;
    Bitmap bitmap;
    int e=0,m=0,h=0;
    String n;
    String typ, sem, cname;
    int cidd, pid;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_paper);
//        actionBar=getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        // getting info from prevoius activity(papers adapter) to autofill the felds of generate paper
        Intent intent= getIntent();
        typ=intent.getStringExtra("type");
        sem=intent.getStringExtra("semester");
        cname=intent.getStringExtra("coursename");
        cidd=intent.getIntExtra("courseid",0);
        pid=intent.getIntExtra("paperid",0);

        hard_added=findViewById(R.id.tv_hard_added);
        medium_added=findViewById(R.id.tv_medium_added);
        easy_added=findViewById(R.id.tv_easy_added);

        tv_coursename=findViewById(R.id.tv_coursename);
        tv_coursename.setText(cname);
        tv_papertype=findViewById(R.id.tv_papertype);
        tv_papertype.setText(typ);
        tv_credithours=findViewById(R.id.tv_credithours);
        tv_credithours.setText("4 Credit Hours");
        tv_section=findViewById(R.id.tv_section);
        setCourseCode(cidd);
        iv_image=findViewById(R.id.iv_image);
        back=findViewById(R.id.backBtn);
        addedques=findViewById(R.id.addedques);
        addedques.setMovementMethod(new ScrollingMovementMethod());
        addingques=findViewById(R.id.addingques);
        addingques.setMovementMethod(new ScrollingMovementMethod());
        s_difficulty = findViewById(R.id.spinnerdifficulty);
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
        btn_addImage=findViewById(R.id.btn_addimage);
        btn_addImage.setOnClickListener(new View.OnClickListener() {
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
                    easy_added.setText(String.valueOf(e));
                }else if(questionDifficulty=="medium"){
                    m++;
                    medium_added.setText(String.valueOf(m));
                }else if(questionDifficulty=="hard"){
                    h++;
                    hard_added.setText(String.valueOf(h));
                }
                String o = addedques.getText().toString();
                n = addingques.getText().toString();
                addedques.setText(o+"\n"+n);
                addingques.setText("");
                AddQuestion(n,storingImage);
            }
        });
        btn_generatePaper =findViewById(R.id.btn_generatePaper);
        btn_generatePaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Generate_paper.this, "Generate paper", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void AddQuestion(String b,String i){

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .AddQuestion(1,"1",b,questionDifficulty,i,pid,"NULL");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {

                    try {
                        String res=response.body().string();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
                else {
                    try {
                        Toast.makeText(Generate_paper.this, "Failed "+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Generate_paper.this, "Failed  "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed",t.getMessage());
            }
        });
    }// on create body

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
                    tv_section.setText(res.getCoursecode());
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

}