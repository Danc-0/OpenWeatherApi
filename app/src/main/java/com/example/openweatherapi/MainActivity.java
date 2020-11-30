package com.example.openweatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView t1_temp, t2_city, t3_description, t4_date;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1_temp = findViewById(R.id.t1_temp);
        t2_city = findViewById(R.id.t2_city);
        t3_description = findViewById(R.id.t3_description);
        t4_date = findViewById(R.id.t4_Date);
        findWeather();
    }

    private void findWeather() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Nairobi,Kenya&APPID=33e71c18d875877c65f261ed121c43d3&units=Imperial";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("main");//Is the "main" object in the API
                    JSONArray array = response.getJSONArray("weather");//Is the "weather" array object in the API
                    JSONObject object = array.getJSONObject(0); //Indexing of our object and the array list

                    String temp = String.valueOf(jsonObject.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-yyyy-MM-dd");
                    String formated_date = sdf.format(calendar.getTime());

                    t4_date.setText(formated_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Creating Queue object and Initializing it
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}