package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class PaperCorrection extends AppCompatActivity {
    String questionDifficulty="";
    Spinner s_difficulty;
    ImageView back;

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
    }
}