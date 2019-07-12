package com.example.companymeetingorganiser;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String setDate;
    Button nextButton, prevButton, meetingButton;
    TextView tv;
    private MeetingAdapter meetingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        meetingButton = findViewById(R.id.scheduleButton);
        tv = findViewById(R.id.textView);
        new FetchData().execute();

        meetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(MainActivity.this, MeetingScheduler.class);
                i.putExtra("dateToSet", setDate);
                startActivity(i);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempDate = null;
            if(tv.getText().equals("Today"))
            {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, 1);
                tempDate = sdf.format(cal.getTime());
            }
            else
            {
                String temp = tv.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(temp));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DAY_OF_MONTH, 1);
                tempDate = sdf.format(c.getTime());
            }
            tv.setText(tempDate);
            new FetchData().execute();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempDate = null;
                if(tv.getText().equals("Today"))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    tempDate = sdf.format(cal.getTime());
                }
                else
                {
                    String temp = tv.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(temp));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    c.add(Calendar.DAY_OF_MONTH, -1);
                    tempDate = sdf.format(c.getTime());
                }
                tv.setText(tempDate);
                new FetchData().execute();
            }
        });
    }

    public class FetchData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
                if(tv.getText().equals("Today"))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar cal = Calendar.getInstance();
                    setDate = sdf.format(cal.getTime());
                }
                else {
                    setDate = (tv.getText().toString());
                }
                return NetworkUtils.getmeetingInfo(setDate);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<MeetingModel> meetingModelArrayList = new ArrayList<>();
            try {
                JSONArray itemsArray = new JSONArray(s);
                for (int i = 0; i < itemsArray.length(); i++) {
                    MeetingModel meetingModel = new MeetingModel();
                    JSONObject dataobj = itemsArray.getJSONObject(i);
                    meetingModel.setStartTime(dataobj.getString("start_time"));
                    meetingModel.setEndTime(dataobj.getString("end_time"));
                    meetingModel.setDescription(dataobj.getString("description"));
                    meetingModel.setParticipants(dataobj.getString("participants"));
                    meetingModelArrayList.add(meetingModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            meetingAdapter = new MeetingAdapter(MainActivity.this , meetingModelArrayList);
            listView.setAdapter(meetingAdapter);
}
    }
}
