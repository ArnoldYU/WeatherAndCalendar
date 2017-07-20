package com.example.arnold.weather.fragement.weatherallview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnold.weather.R;
import com.example.arnold.weather.weathergetbyinternate.Adapter.WeatherAdapter;
import com.example.arnold.weather.weathergetbyinternate.Model.HourlyWeather;
import com.example.arnold.weather.weathergetbyinternate.Net.HttpUtil;
import com.example.arnold.weather.weathergetbyinternate.Util.HttpCallbackListener;
import com.example.arnold.weather.weathergetbyinternate.Util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arnold on 2017/7/20.
 */

public class weatherview extends Fragment {
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
    private View view;
//    private IndexHorizontalScrollView indexHorizontalScrollView;
//    private Today24HourView today24HourView;

    private ImageView weatherimgtoday;
    private ImageView weatherimgtomorrow;
    private TextView temperaturetoday;
    private TextView temperaturetomorrow;
    private TextView weathertoday;
    private TextView weathertomorrow;
    private com.example.arnold.weather.fragement.weatherallview.Today24HourView myToday24HourView;
    private IndexHorizontalScrollView indexHorizontalScrollView;
    private Today24HourView today24HourView;
    private String mycity = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weatherview, container, false);
//        loadChart();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mycity = bundle.getString("City");
        }
        System.out.println(weatherList.size());
        init(view, mycity);
        System.out.println("1");
        initViews(view);
        return view;
    }


    private void initViews(View view) {
        System.out.println("2");
        indexHorizontalScrollView = (IndexHorizontalScrollView) view.findViewById(R.id.indexHorizontalScrollView);
        today24HourView = (Today24HourView) view.findViewById(R.id.today24HourView);
        indexHorizontalScrollView.setToday24HourView(today24HourView);
    }

    public void init(View view, String mycity) {
//        citySwitch = (Button) view.findViewById(R.id.citySwitch);
//        weatherRefresh = (Button) view.findViewById(R.id.weatherRefresh);
//        weatherRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                weatherRefresh.setText("同步中……");
//                String countyName = sharedPreferences.getString("CountyName", "");
//                if (!TextUtils.isEmpty(countyName)) {
//                    queryFromServer(countyName);
//                }
//            }
//        });
//        citySwitch.setOnClickListener(this);
//        weatherRefresh.setOnClickListener(this);
//        cityName = (TextView) view.findViewById(R.id.cityName);
//        DayNightWeather = (TextView) view.findViewById(R.id.DayNightWeather);

        temperaturetoday = (TextView) view.findViewById(R.id.temperaturetoday);
        temperaturetomorrow = (TextView) view.findViewById(R.id.temperaturetomorrow);
        weathertoday = (TextView) view.findViewById(R.id.weathertoday);
        weathertomorrow = (TextView) view.findViewById(R.id.weathertomorrow);
        sunriseTime = (TextView) view.findViewById(R.id.sunuptime);
        sunsetTime = (TextView) view.findViewById(R.id.sundowntime);

        weatherimgtoday = (ImageView) view.findViewById(R.id.weatherimgtoday);
        weatherimgtomorrow = (ImageView) view.findViewById(R.id.weatherimgtomorrow);


//        wind = (TextView) view.findViewById(R.id.wind);
//        pop = (TextView) view.findViewById(R.id.pop);
//        updateTime = (TextView) view.findViewById(R.id.updateTime);
//        listview = (ListView) view.findViewById(R.id.hourlyForecast);
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
//        weatherRefresh.setText("同步中……");
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
//                            weatherRefresh.setText("更新数据");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showWeather() {
//        cityName.setText(sharedPreferences.getString("cityName", "未知"));
//        DayNightWeather.setText("日：" + sharedPreferences.getString("dayWeather", "未知") + " 夜：" + sharedPreferences.getString("nightWeather", "未知"));
//        temp.setText("温度：" + sharedPreferences.getString("temp", "未知"));
//        wind.setText("风力：" + sharedPreferences.getString("wind", "未知"));
//        pop.setText("降水概率：" + sharedPreferences.getString("pop", "未知"));
//        updateTime.setText("发布时间:" + sharedPreferences.getString("updateTime", "未知"));
//        WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.hourly_weather, weatherList);
//        System.out.println(weatherList.size());
//        listview.setAdapter(adapter);
//        Today24HourView.changesize(weatherList.size());
//        myToday24HourView = new com.example.arnold.weather.fragement.weatherallview.Today24HourView(getActivity());
//

//        indexHorizontalScrollView = (IndexHorizontalScrollView)view.findViewById(R.id.indexHorizontalScrollView);
//////        Today24HourView today24HourView = (Today24HourView)view.findViewById(R.id.today24HourView);
//////        indexHorizontalScrollView.setToday24HourView(myToday24HourView);
//        indexHorizontalScrollView.addView(myToday24HourView);

        weatherimgtoday.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences.getString("code", "999"))));
        weatherimgtomorrow.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences.getString("code_tomorrow", "999"))));
        temperaturetoday.setText(sharedPreferences.getString("temp", "未知"));
        temperaturetomorrow.setText(sharedPreferences.getString("temptomorrow", "未知"));
        weathertoday.setText(sharedPreferences.getString("dayWeather", "未知"));
        weathertomorrow.setText(sharedPreferences.getString("dayWeather_tomorrow", "未知"));
        sunriseTime.setText("日出：" + sharedPreferences.getString("sunriseTime", "未知"));
        sunsetTime.setText("日落：" + sharedPreferences.getString("sunsetTime", "未知"));
        Toast.makeText(getActivity(), mycity+"已经是最新数据了", Toast.LENGTH_SHORT).show();
//        weatherRefresh.setText("更新数据");
    }

    public int returnbackground(int code) {
        switch (code) {
            case 100:
                return R.mipmap.w0;
            case 101:
                return R.drawable.a101;
            case 102:
                return R.mipmap.w1;
            case 103:
                return R.mipmap.w2;
            case 104:
                return R.drawable.a104;
            case 200:
                return R.drawable.a200;
            case 201:
                return R.drawable.a201;
            case 202:
                return R.drawable.a202;
            case 203:
                return R.drawable.a203;
            case 204:
                return R.drawable.a204;
            case 205:
                return R.drawable.a205;
            case 206:
                return R.drawable.a206;
            case 207:
                return R.drawable.a207;
            case 208:
                return R.drawable.a208;
            case 209:
                return R.drawable.a209;
            case 210:
                return R.drawable.a210;
            case 212:
                return R.drawable.a212;
            case 213:
                return R.drawable.a213;
            case 300:
                return R.mipmap.w3;
            case 301:
                return R.mipmap.w3;
            case 302:
                return R.mipmap.w4;
            case 303:
                return R.mipmap.w5;
            case 304:
                return R.drawable.a304;
            case 305:
                return R.mipmap.w7;
            case 306:
                return R.mipmap.w8;
            case 307:
                return R.mipmap.w9;
            case 308:
                return R.mipmap.w10;
            case 309:
                return R.drawable.a309;
            case 310:
                return R.drawable.a310;
            case 311:
                return R.drawable.a311;
            case 312:
                return R.drawable.a312;
            case 313:
                return R.drawable.a313;
            case 400:
                return R.mipmap.w7;
            case 401:
                return R.mipmap.w8;
            case 402:
                return R.mipmap.w14;
            case 403:
                return R.mipmap.w15;
            case 404:
                return R.mipmap.w16;
            case 405:
                return R.drawable.a405;
            case 406:
                return R.drawable.a406;
            case 407:
                return R.drawable.a407;
            case 500:
                return R.drawable.a500;
            case 501:
                return R.drawable.a501;
            case 502:
                return R.drawable.a502;
            case 503:
                return R.drawable.a503;
            case 504:
                return R.drawable.a504;
            case 508:
                return R.drawable.a508;
            case 999:
                return R.drawable.a999;
            case 507:
                return R.drawable.a507;
            case 900:
                return R.drawable.a900;
            case 901:
                return R.drawable.a901;

        }
        return R.drawable.a999;
    }
}
