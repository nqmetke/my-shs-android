package com.nqmetke.myshs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Handler;

public class EditClass extends AppCompatActivity {

    public static final String PREFS_NAME = "user_courses";

    private EditText editText1 = null;
    private EditText editText2 = null;
    private EditText editText3 = null;
    private EditText editText4 = null;
    private EditText editText5 = null;
    private EditText editText6 = null;
    private EditText editText7 = null;
    private EditText editText8 = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        SharedPreferences courses = getSharedPreferences(PREFS_NAME, 0);
          editText1 = (EditText)findViewById(R.id.editText);
          editText2 = (EditText)findViewById(R.id.editText2);
          editText3 = (EditText)findViewById(R.id.editText3);
          editText4 = (EditText)findViewById(R.id.editText4);
          editText5 = (EditText)findViewById(R.id.editText5);
          editText6 = (EditText)findViewById(R.id.editText6);
          editText7 = (EditText)findViewById(R.id.editText7);
          editText8 = (EditText)findViewById(R.id.editText8);

            editText1.setText(courses.getString("course-1", null));
        editText2.setText(courses.getString("course-2", null));
        editText3.setText(courses.getString("course-3", null));
        editText4.setText(courses.getString("course-4", null));
        editText5.setText(courses.getString("course-5", null));
        editText6.setText(courses.getString("course-6", null));
        editText7.setText(courses.getString("course-7", null));
        editText8.setText(courses.getString("course-8", null));

    }

    public void goBack(View v){




        String course_1 = editText1.getText().toString();
        String course_2 = editText2.getText().toString();
        String course_3 = editText3.getText().toString();
        String course_4 = editText4.getText().toString();
        String course_5 = editText5.getText().toString();
        String course_6 = editText6.getText().toString();
        String course_7 = editText7.getText().toString();
        String course_8 = editText8.getText().toString();

        SharedPreferences courses = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = courses.edit();
        editor.putString("course-1", course_1);
        editor.putString("course-2", course_2);
        editor.putString("course-3", course_3);
        editor.putString("course-4", course_4);
        editor.putString("course-5", course_5);
        editor.putString("course-6", course_6);
        editor.putString("course-7", course_7);
        editor.putString("course-8", course_8);

        editor.commit();

        Intent intent = new Intent(this, schedule_main.class);
        startActivity(intent);

    }
}
