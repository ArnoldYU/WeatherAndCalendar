package com.example.arnold.weather.weathergetbyinternate.Net;


import com.example.arnold.weather.weathergetbyinternate.Util.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);


                    connection = (HttpURLConnection) url.openConnection();


                    connection.setRequestMethod("GET");


                    connection.setConnectTimeout(8000);


                    connection.setReadTimeout(8000);
//                    connection.setRequestProperty("apikey", "cd71be9c1e9fec114c69764018922c97");

                    connection.connect();


                    InputStream inputStream = connection.getInputStream();


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");


                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                    StringBuilder response = new StringBuilder();


                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
