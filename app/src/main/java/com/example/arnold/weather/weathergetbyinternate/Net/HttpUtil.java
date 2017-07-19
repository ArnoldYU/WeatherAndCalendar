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

                    System.out.println("111111111111");

                    connection = (HttpURLConnection) url.openConnection();

                    System.out.println("222222222222");

                    connection.setRequestMethod("GET");

                    System.out.println("333333333333");

                    connection.setConnectTimeout(8000);

                    System.out.println("444444444444");

                    connection.setReadTimeout(8000);
//                    connection.setRequestProperty("apikey", "cd71be9c1e9fec114c69764018922c97");
                    System.out.println("555555555555");

                    connection.connect();

                    System.out.println("6666666666666");

                    InputStream inputStream = connection.getInputStream();

                    System.out.println("7777777777777");

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                    System.out.println("888888888888");

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    System.out.println("9999999999999");

                    StringBuilder response = new StringBuilder();

                    System.out.println("0000000000000");

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        System.out.println("lalalalallalalalallalal1");
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        System.out.println("lalalalallalalalallalal2222222222222");
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        System.out.println("lalalalallalalalallalal133333333333");
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
