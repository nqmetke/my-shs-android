package com.nqmetke.myshs;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class schedule_main extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private String[] courses = new String[] {};
    private ArrayList<String>list = new ArrayList<String>();
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);

    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR);
    int minute = c.get(Calendar.MINUTE);
    private int second = c.get(Calendar.SECOND);
    public static final String PREFS_NAME = "user_courses";







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);




        final ListView schedule = (ListView) findViewById(R.id.listView);;
        for (String course : courses) {
            list.add(course);
        }




        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.schedule_layout, list);
            schedule.setAdapter(adapter);
        new JSONTask().execute("http://shstv.herokuapp.com/api/schedule/12/8/8");

    }
    public void editClasses(View v){

        startActivity(new Intent(schedule_main.this, EditClass.class));
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        SharedPreferences courses = getSharedPreferences(PREFS_NAME, 0);
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try

            {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (
                    IOException e
                    )

            {
                e.printStackTrace();
            } finally

            {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            try {
                SharedPreferences.Editor editor = courses.edit();
                editor.putString("json", json);
                editor.commit();

                JSONArray jsonArray = new JSONArray(json);


                String course_1 = courses.getString("course-1", null);
                String course_2 = courses.getString("course-2", null);
                String course_3 = courses.getString("course-3", null);
                String course_4 = courses.getString("course-4", null);
                String course_5 = courses.getString("course-5", null);
                String course_6 = courses.getString("course-6", null);
                String course_7 = courses.getString("course-7", null);
                String course_8 = courses.getString("course-8", null);
                TextView test = (TextView)findViewById(R.id.textView);

                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject e = jsonArray.getJSONObject(i);
                    String course_number = e.getString("name");
                    String course_name;




                    int start_time  = Integer.parseInt(e.getString("start_seconds"));

                    float start_time_float  = Float.parseFloat(e.getString("start_seconds"));
                    float startTime = (start_time/60)/60;
                    float startTimeMin = (start_time_float/60)/60;
                    float minDec = startTimeMin - startTime;
                    float min = 60 * minDec;

                    int end_time  = Integer.parseInt(e.getString("end_seconds"));
                    float end_time_float  = Float.parseFloat(e.getString("end_seconds"));
                    float endTime = (end_time/60)/60;
                    float endTimeMin = (end_time_float/60)/60;
                    float endMinDec = endTimeMin - endTime;
                    float endMin = 60 * endMinDec;

                    if (startTime > 12){
                        startTime = startTime -12;
                    }
                    if (endTime > 12){
                        endTime = endTime - 12;
                    }
                    switch (course_number){
                        case "1":
                            course_name = course_1;

                            break;
                        case "2":
                            course_name = course_2;
                            break;
                        case "3":
                            course_name = course_3;
                            break;
                        case "4":
                            course_name = course_4;
                            break;
                        case "5":
                            course_name = course_5;
                            break;
                        case "6":
                            course_name = course_6;
                            break;
                        case "7":
                            course_name = course_7;
                            break;
                        case "8":
                            course_name = course_8;
                            break;
                        default:
                            course_name = "";
                            break;
                    }




                    list.add(e.getString("name") + " | " + course_name + "  |  " + Math.round(startTime) + ((min < 10) ? ":0" : ":") + Math.round(min) + " - " + Math.round(endTime) + ((endMin < 10) ? ":0" : ":") + Math.round(endMin));


                }
                ;


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    protected void onStop(){
        super.onStop();
        SharedPreferences courses = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = courses.edit();


    }



}







