package com.nqmetke.myshs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

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
    String FILENAME = "homework";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hw);
        Intent intent = getIntent();
         course_name = intent.getExtras().getString("course_name");
         course_number = intent.getExtras().getString("course_number");
        FileOutputStream fos = null;
        FileInputStream ofi = null;
        hwTitle = (EditText)findViewById(R.id.hwTitle);
        hwDescription = (EditText)findViewById(R.id.hwDescription);
        //Setting up the inputs

        try {
            ofi = openFileInput(FILENAME);
            hwTitle.setText(ofi.read());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
    void saveHomework(){
        ArrayList<String> homeworkContent = new ArrayList<String>();


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            homeworkContent.add(1, course_number);
            homeworkContent.add(1, hwTitle.getText().toString());
            homeworkContent.add(1, hwDescription.getText().toString());
        for(String s: homeworkContent) {
            fos.write(s.getBytes());
        }
        fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


   /*     editor.putString("class-number", course_number);
        editor.putString("hw-title", hwTitle.getText().toString());
        editor.putString("hw-description", hwDescription.getText().toString());
        editor.apply();*/



    }



}
