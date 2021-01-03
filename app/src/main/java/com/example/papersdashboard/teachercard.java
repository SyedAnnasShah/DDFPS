package com.example.papersdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class teachercard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachercard);

//        Spinner spinner_courses = findViewById(R.id.spinner_courses);
//        String[] items_courses = new String[]{"PF", "DDS", "OOP"};
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_courses);
//        spinner_courses.setAdapter(adapter1);
        Spinner spinner_semester = findViewById(R.id.spinner_semester);
        String[] items_semester = new String[]{"1", "2", "4"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_semester);
        spinner_semester.setAdapter(adapter2);
        Spinner spinner_section = findViewById(R.id.spinner_section);
        String[] items_section = new String[]{"A", "B", "C"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_section);
        spinner_section.setAdapter(adapter3);
        Spinner spinner_discipline = findViewById(R.id.spinner_discipline);
        String[] items_discipline = new String[]{"BSCS", "BSIT", "MCS"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_discipline);
        spinner_discipline.setAdapter(adapter4);
    }
}