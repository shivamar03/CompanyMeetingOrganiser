package com.example.companymeetingorganiser;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class NetworkUtils {
    // Base URL for Books API.
    private static final String BASE_URL =  "http://fathomless-shelf-5846.herokuapp.com/api/schedule?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "date";

    static String getmeetingInfo(String queryDate)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dateJSON = null;
        String line;

        try{
            Uri.Builder builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryDate);

            URL requestUrl = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            while((line = reader.readLine()) != null)
            {
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length() == 0){
                return null;
            }

            dateJSON = builder.toString();

        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
        finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return dateJSON;
    }

}
