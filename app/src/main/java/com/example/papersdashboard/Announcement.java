package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Announcement extends AppCompatActivity {
    EditText duedate,term,easy,hard,medium;
    Button createAnnounccement;
    ImageView backImageAnnouncement;
    String txtdate,txtterm;
    int txteasy,txthard,txtmedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        duedate=(EditText)findViewById(R.id.et_duedate);
        term=(EditText)findViewById(R.id.et_term);
        easy=(EditText)findViewById(R.id.et_easy);
        medium=(EditText)findViewById(R.id.et_medium);
        hard=(EditText)findViewById(R.id.et_hard);
        createAnnounccement=(Button)findViewById(R.id.btn_submitAnnouncement);
        backImageAnnouncement=(ImageView)findViewById(R.id.backBtnAnnouncement) ;
        backImageAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        createAnnounccement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoConfiguration();
            }
        });


    }

    private void insertIntoConfiguration() {
        txtdate=duedate.getText().toString();
        txtterm=term.getText().toString();
        txteasy=Integer.parseInt(easy.getText().toString());
        txtmedium=Integer.parseInt(medium.getText().toString());
        txthard=Integer.parseInt(hard.getText().toString());
        // use a call here to updaet data in configuration table



    }
}