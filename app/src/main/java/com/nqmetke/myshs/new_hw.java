package com.nqmetke.myshs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class new_hw extends AppCompatActivity {
    public static final String PREFS_NAME = "courses_hw";
    String course_name = null;
    String course_number= null;
    EditText hwTitle;
    EditText hwDescription;
    Button submitBtn;
    String FILENAME = "homework";
    //DatabaseHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hw);
       // db = new DatabaseHelper(this);
        Intent intent = getIntent();
         course_name = intent.getExtras().getString("course_name");
         course_number = intent.getExtras().getString("course_number");
        hwTitle = (EditText)findViewById(R.id.hwTitle);
        hwDescription = (EditText)findViewById(R.id.hwDescription);
        submitBtn = (Button)findViewById(R.id.submit);
        saveHomework();






    }
    public void saveHomework() {




}
}
