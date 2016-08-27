package com.nqmetke.myshs;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
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
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import android.support.design.widget.FloatingActionButton;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class schedule_main extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private ArrayList<String>list = new ArrayList<String>();

    public static final String PREFS_NAME = "user_courses";
    List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
    Calendar c = null;
    Calendar cal = Calendar.getInstance();
    int day_name_schedule = cal.get(Calendar.DAY_OF_WEEK);
    int year =  0;
    int month = 0;
    int day = 0;
    int hour = 0;
    int minute = 0;
    int day_name = 0;
    int second = 0;
    int real_second = 0;
    int endTimeFinal = 0;
    List<HashMap<String, String>> prevFill;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);
        System.out.println("Connecting...");
        setTitle("mySHS");

        new JSONTask().execute("http://shstv.herokuapp.com/api/schedule/today");
        final ListView schedule = (ListView) findViewById(R.id.listView);
        final String[] col_value = new String[] {"col_number", "col_course", "col_time"};
        final int[] col_id = new int[] {R.id.item1, R.id.item2, R.id.item3};



        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingSchedule);
                                if (fillMaps != prevFill) {
                                    final SimpleAdapter adapter = new SimpleAdapter(schedule_main.this, fillMaps, R.layout.schedule_layout, col_value, col_id);
                                    schedule.setAdapter(adapter);
                                    hideView(progressBar);
                                }
                                prevFill = fillMaps;

                                    c = Calendar.getInstance();
                                    year = c.get(Calendar.YEAR);
                                    month = c.get(Calendar.MONTH);
                                    day = c.get(Calendar.DAY_OF_MONTH);
                                    hour = c.get(Calendar.HOUR);
                                    minute = c.get(Calendar.MINUTE);
                                    day_name = c.get(Calendar.DAY_OF_WEEK);
                                    second = c.get(Calendar.SECOND);
                                    String val = null;
                                    int am_pm = c.get(Calendar.AM_PM);
                                    if (am_pm == (Calendar.PM)) {
                                        hour += 12;
                                    }


                                    real_second = hour * 60 * 60 + minute * 60 + second;
                                    int endPeriod = 0;
                                    for (int i = 0; i < 6; i++) {
                                        endPeriod = Integer.parseInt(fillMaps.get(i).get("raw_num"));
                                        if (real_second < endPeriod) {
                                            break;
                                        }

                                    }

                                    TextView time = (TextView) findViewById(R.id.textView2);
                                    int timeLeft = endPeriod - real_second;
                                    int minLeft = timeLeft / 60;
                                    if (minLeft > 0) {

                                        time.setText(Integer.toString(minLeft) + ":" + (60 - second));
                                    } else {
                                        String text = "School is over for the day!";
                                        time.setText(text);
                                    }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();








        schedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), course_hw.class);
                String course_name = (fillMaps.get(position)).get("col_course");
                String course_num = (fillMaps.get(position)).get("col_number");

                intent.putExtra("course_name", course_name);
                intent.putExtra("course_number", course_num);
                //intent.putExtra("hashMap", fillMaps.get(position).get("col_course"));
                startActivity(intent);
                intent.putExtra(EXTRA_MESSAGE, position);


            }
        });



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
            System.out.println("Connecting...");


            try

            {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                //InputStream stream = getAssets().open("mainSchedule.json");

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                System.out.println("Parsing...");
                System.out.println("Returned: " + buffer.toString());
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
        protected void onPostExecute(String json_raw) {
            System.out.println("Starting Post Execute...");
            super.onPostExecute(json_raw);
            try {
                SharedPreferences schedules = getSharedPreferences("schedule", 0);
                SharedPreferences.Editor editor = schedules.edit();
                editor.putString("json", json_raw);
                editor.apply();
                String json = schedules.getString("json", null);

                System.out.println("Fo Shizzle! This is json: " + json);
               // System.out.println("Wowie, this is json_raw: " + json_raw);



                JSONArray jsonArray = new JSONArray(json);



                String course_1 = courses.getString("course-1", null);
                String course_2 = courses.getString("course-2", null);
                String course_3 = courses.getString("course-3", null);
                String course_4 = courses.getString("course-4", null);
                String course_5 = courses.getString("course-5", null);
                String course_6 = courses.getString("course-6", null);
                String course_7 = courses.getString("course-7", null);
                String course_8 = courses.getString("course-8", null);
                int day_number = 0;

                switch(day_name_schedule) {
                    case 2:
                        day_number = 0;
                        break;
                    case 3:
                        day_number = 1;
                        break;
                    case 4:
                        day_number = 2;
                        break;
                    case 5:
                        day_number = 3;
                        break;
                    case 6:
                        day_number = 4;
                        break;
                }
                //JSONArray jsonArray = h.getJSONArray(day_number);
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
                    System.out.println("Done! Adding to Hashmap!");
                    System.out.println("Course Name: " + course_name);
                    System.out.println("Course Number: " + e.getString("name"));
                    System.out.println("Course Time: " + Math.round(startTime) + ((min < 10) ? ":0" : ":") + Math.round(min) + " - " + Math.round(endTime) + ((endMin < 10) ? ":0" : ":") + Math.round(endMin));
                    System.out.println("Course Name: " + Integer.toString(end_time));



                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("col_time", Math.round(startTime) + ((min < 10) ? ":0" : ":") + Math.round(min) + " - " + Math.round(endTime) + ((endMin < 10) ? ":0" : ":") + Math.round(endMin));
                    // map.put("col_time", "col_time");
                    map.put("col_course", course_name);
                    //map.put("col_course", "course_name");
                    map.put("col_number", e.getString("name"));
                    // map.put("col_number", "col_number");
                    map.put("raw_num", Integer.toString(end_time));
                    fillMaps.add(map);



                    //list.add(e.getString("name") + " | " + course_name + "  |  " + Math.round(startTime) + ((min < 10) ? ":0" : ":") + Math.round(min) + " - " + Math.round(endTime) + ((endMin < 10) ? ":0" : ":") + Math.round(endMin));


                }
                ;


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }

    protected void onStart(){
        super.onStart();
        //new JSONTask().execute("http://shstv.herokuapp.com/api/schedule/today");
    }


    protected void onStop(){
        super.onStop();
        SharedPreferences courses = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = courses.edit();


    }
    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}







