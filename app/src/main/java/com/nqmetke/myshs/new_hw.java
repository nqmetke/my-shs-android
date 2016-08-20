package com.nqmetke.myshs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class new_hw extends AppCompatActivity {
    public static final String PREFS_NAME = "courses_hw";
    String course_name = null;
    String course_number= null;
    EditText hwTitle;
    EditText hwDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hw);
        Intent intent = getIntent();
         course_name = intent.getExtras().getString("course_name");
         course_number = intent.getExtras().getString("course_number");

        //Setting up the inputs

         hwTitle = (EditText)findViewById(R.id.hwTitle);
         hwDescription = (EditText)findViewById(R.id.hwDescription);




    }
    void saveHw(){
        SharedPreferences hw = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = hw.edit();


        editor.putString("class-number", course_number);
        editor.putString("hw-title", hwTitle.getText().toString());
        editor.putString("hw-description", hwDescription.getText().toString());
        editor.apply();



    }
}
