package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Generate_paper extends AppCompatActivity {
    TextView addedques;
    Button btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_paper);
        addedques=findViewById(R.id.addedques);
        addedques.setMovementMethod(new ScrollingMovementMethod());
        Spinner s_difficulty = findViewById(R.id.spinnerdifficulty);
        String[] items_difficulty = new String[]{"Easy", "Medium", "Hard"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_difficulty);
        s_difficulty.setAdapter(adapter1);

        btnadd=findViewById(R.id.btn_addques);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Generate_paper.this, "btn btn", Toast.LENGTH_LONG).show();
            }
        });
    }
}