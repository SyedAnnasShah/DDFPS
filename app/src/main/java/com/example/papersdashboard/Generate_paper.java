package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Generate_paper extends AppCompatActivity {
    TextView addedques;
    EditText addingques;
    String questionDifficulty="";
    Spinner s_difficulty;
    Button addQuestion, generatePaper;
    int e=2,m=2,h=2;
    String n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_paper);
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

        addQuestion=findViewById(R.id.btn_addques);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionDifficulty=="easy"){
                    e--;
                }else if(questionDifficulty=="medium"){
                    m--;
                }else if(questionDifficulty=="hard"){
                    h--;
                }



                String o = addedques.getText().toString();
                n = addingques.getText().toString();
                addedques.setText(o+"\n"+n);
                addingques.setText("");
                AddQuestion(n);
            }
        });
        generatePaper=findViewById(R.id.btn_generatePaper);
        generatePaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Generate_paper.this, "Generate paper", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddQuestion(String b){

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .AddQuestion(1,"1",b,questionDifficulty,1,"NULL");
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




    }


}