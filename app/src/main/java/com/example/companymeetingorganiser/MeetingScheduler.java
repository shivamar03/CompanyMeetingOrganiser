package com.example.companymeetingorganiser;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MeetingScheduler extends Activity {
EditText date, startTime, endTime;
Button submitButton;
Integer counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_meeting);
        date = findViewById(R.id.date);
        startTime = findViewById(R.id.starttime);
        endTime = findViewById(R.id.endtime);
        submitButton = findViewById(R.id.submitButton);
        String setDate = getIntent().getStringExtra("dateToSet");
        date.setText(setDate);
        
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchDataAgain().execute();
               /* if (counter == 1)
                {
                    Toast.makeText(MeetingScheduler.this, "Slot Not Available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MeetingScheduler.this, "Slot Available", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
        
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                Integer year = c.get(Calendar.YEAR);
                Integer month = c.get(Calendar.MONTH);
                Integer day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MeetingScheduler.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MeetingScheduler.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                startTime.setText(String.format("%d:%02d", hourOfDay , minute));
                            }

                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MeetingScheduler.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                endTime.setText(String.format("%d:%02d", hourOfDay , minute));

                            }

                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    public class FetchDataAgain extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            String temp = date.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(temp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String tempDate = sdf.format(c.getTime());
            return NetworkUtils.getmeetingInfo(tempDate);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast toastcheck = null;
            //ArrayList<MeetingModel> meetingModelArrayList = new ArrayList<>();
            try {
                JSONArray itemsArray = new JSONArray(s);
                for (int i = 0; i < itemsArray.length(); i++) {
                    String temp = startTime.getText().toString();
                    JSONObject dataobj = itemsArray.getJSONObject(i);
                    if(temp.equals(dataobj.getString("start_time")))
                    {
                        if(toastcheck != null)
                        {toastcheck.cancel();}
                        Toast.makeText(MeetingScheduler.this, "Slot Not Available", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        toastcheck = Toast.makeText(MeetingScheduler.this, "Slot Available", Toast.LENGTH_SHORT);
                        toastcheck.show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
    }
}
