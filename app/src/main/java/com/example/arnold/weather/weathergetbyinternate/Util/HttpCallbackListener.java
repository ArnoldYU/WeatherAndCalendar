package com.example.arnold.weather.weathergetbyinternate.Util;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}
