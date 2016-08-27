package com.nqmetke.myshs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class course_hw extends AppCompatActivity {
    public static final String PREFS_NAME = "courses_hw";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_hw);


        SharedPreferences hw = getSharedPreferences(PREFS_NAME, 0);

        Intent intent = getIntent();
        final String course_name = intent.getExtras().getString("course_name");
        final String course_number = intent.getExtras().getString("course_number");


        TextView textView = (TextView)findViewById(R.id.textView3);
        textView.setText(course_name);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.editClassesButton);
/*        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), new_hw.class);
                startActivity(new Intent(course_hw.this, new_hw.class));
                intent.putExtra("course_name", course_name);
                intent.putExtra("course_number", course_number);
                startActivity(intent);
            }*//*
        });*/
    }

}
