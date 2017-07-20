package com.example.arnold.weather.fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnold.weather.R;
import com.example.arnold.weather.weathergetbyinternate.Activity.ChooseAreaActivity;
import com.example.arnold.weather.weathergetbyinternate.Activity.WeatherActivity;
import com.example.arnold.weather.weathergetbyinternate.Adapter.WeatherAdapter;
import com.example.arnold.weather.weathergetbyinternate.Model.*;
import com.example.arnold.weather.weathergetbyinternate.Model.HourlyWeather;
import com.example.arnold.weather.weathergetbyinternate.Net.HttpUtil;
import com.example.arnold.weather.weathergetbyinternate.Util.HttpCallbackListener;
import com.example.arnold.weather.weathergetbyinternate.Util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arnold on 2017/7/19.
 */

public class test2 extends Fragment {

    // 城市切换按钮
    private Button citySwitch;
    // 刷新数据按钮
    private Button weatherRefresh;
    // 城市名
    private TextView cityName;
    // 白天夜晚天气描叙
    private TextView DayNightWeather;
    // 温度
    private TextView temp;
    // 日出时间
    private TextView sunriseTime;
    // 日落时间
    private TextView sunsetTime;
    // 风力
    private TextView wind;
    // 降水概率
    private TextView pop;
    // 发布时间
    private TextView updateTime;
    // 今日天气预测列表
    private ListView listview;

    public static List<HourlyWeather> weatherList = new ArrayList<>();

    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather, container, false);
        String mycity = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mycity = bundle.getString("City");
        }
        System.out.println(weatherList.size());
        init(view, mycity);
        return view;
    }

    private void init(View view, String mycity) {
        citySwitch = (Button) view.findViewById(R.id.citySwitch);
        weatherRefresh = (Button) view.findViewById(R.id.weatherRefresh);
        weatherRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherRefresh.setText("同步中……");
                String countyName = sharedPreferences.getString("CountyName", "");
                if (!TextUtils.isEmpty(countyName)) {
                    queryFromServer(countyName);
                }
            }
        });
//        citySwitch.setOnClickListener(this);
//        weatherRefresh.setOnClickListener(this);
        cityName = (TextView) view.findViewById(R.id.cityName);
        DayNightWeather = (TextView) view.findViewById(R.id.DayNightWeather);
        temp = (TextView) view.findViewById(R.id.temp);
        sunriseTime = (TextView) view.findViewById(R.id.sunriseTime);
        sunsetTime = (TextView) view.findViewById(R.id.sunsetTime);
        wind = (TextView) view.findViewById(R.id.wind);
        pop = (TextView) view.findViewById(R.id.pop);
        updateTime = (TextView) view.findViewById(R.id.updateTime);
        listview = (ListView) view.findViewById(R.id.hourlyForecast);
        String countyName = mycity;
        sharedPreferences = getActivity().getSharedPreferences(countyName, Context.MODE_PRIVATE);


        // 当countyName不为空
        if (!TextUtils.isEmpty(countyName)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("CountyName", countyName);
            editor.commit();
        } else {
            countyName = sharedPreferences.getString("CountyName", "");
        }
        weatherRefresh.setText("同步中……");
        queryFromServer(countyName);
    }

    private void queryFromServer(final String countyName) {
        try {
            String url = "https://way.jd.com/he/freeweather?city=";
            String name = new String(countyName.getBytes("UTF-8"));//
            String bla = "&appkey=cd71be9c1e9fec114c69764018922c97";

            Log.d("name123", url + name);
            HttpUtil.sendHttpRequest(url + name + bla, new HttpCallbackListener() {
                @Override

                public void onFinish(String response) {
                    Utility.handleWeatherResponse(getActivity(), response);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });

                }

                @Override
                public void onError(Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "同步失败", Toast.LENGTH_LONG).show();
                            weatherRefresh.setText("更新数据");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showWeather() {
        cityName.setText(sharedPreferences.getString("cityName", "未知"));
        sunriseTime.setText("日出：" + sharedPreferences.getString("sunriseTime", "未知"));
        sunsetTime.setText("日落：" + sharedPreferences.getString("sunsetTime", "未知"));
        DayNightWeather.setText("日：" + sharedPreferences.getString("dayWeather", "未知") + " 夜：" + sharedPreferences.getString("nightWeather", "未知"));
        temp.setText("温度：" + sharedPreferences.getString("temp", "未知"));
        wind.setText("风力：" + sharedPreferences.getString("wind", "未知"));
        pop.setText("降水概率：" + sharedPreferences.getString("pop", "未知"));
        updateTime.setText("发布时间:" + sharedPreferences.getString("updateTime", "未知"));
        WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.hourly_weather, weatherList);
        System.out.println(weatherList.size());
        listview.setAdapter(adapter);
        Toast.makeText(getActivity(), "已经是最新数据了", Toast.LENGTH_SHORT).show();
        weatherRefresh.setText("更新数据");
    }
}
