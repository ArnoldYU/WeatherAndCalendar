package com.example.arnold.weather.fragement.weatherallview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnold.weather.R;
import com.example.arnold.weather.weathergetbyinternate.Model.HourlyWeather;
import com.example.arnold.weather.weathergetbyinternate.Net.HttpUtil;
import com.example.arnold.weather.weathergetbyinternate.Util.HttpCallbackListener;
import com.example.arnold.weather.weathergetbyinternate.Util.Utility;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
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

    private boolean mode;

    public static List<HourlyWeather> weatherList = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private View view;

    private TextView suggestion_comf;
    private TextView suggestion_drsg;
    private TextView suggestion_flu;
    private TextView suggestion_sport;
    private TextView suggestion_trav;

    private TextView forecast1day;
    private TextView forecast1date;
    private TextView forecast1weather1;
    private ImageView forecast1image1;
    private TextView forecast1temp1;
    private TextView forecast1temp2;
    private ImageView forecast1image2;
    private TextView forecast1weather2;
    private TextView forecast1wind1;
    private TextView forecast1wind2;

    private TextView forecast2day;
    private TextView forecast2date;
    private TextView forecast2weather1;
    private ImageView forecast2image1;
    private TextView forecast2temp1;
    private TextView forecast2temp2;
    private ImageView forecast2image2;
    private TextView forecast2weather2;
    private TextView forecast2wind1;
    private TextView forecast2wind2;

    private TextView forecast3day;
    private TextView forecast3date;
    private TextView forecast3weather1;
    private ImageView forecast3image1;
    private TextView forecast3temp1;
    private TextView forecast3temp2;
    private ImageView forecast3image2;
    private TextView forecast3weather2;
    private TextView forecast3wind1;
    private TextView forecast3wind2;

    private TextView forecast4day;
    private TextView forecast4date;
    private TextView forecast4weather1;
    private ImageView forecast4image1;
    private TextView forecast4temp1;
    private TextView forecast4temp2;
    private ImageView forecast4image2;
    private TextView forecast4weather2;
    private TextView forecast4wind1;
    private TextView forecast4wind2;

    private TextView forecast5day;
    private TextView forecast5date;
    private TextView forecast5weather1;
    private ImageView forecast5image1;
    private TextView forecast5temp1;
    private TextView forecast5temp2;
    private ImageView forecast5image2;
    private TextView forecast5weather2;
    private TextView forecast5wind1;
    private TextView forecast5wind2;

    private TextView forecast6day;
    private TextView forecast6date;
    private TextView forecast6weather1;
    private ImageView forecast6image1;
    private TextView forecast6temp1;
    private TextView forecast6temp2;
    private ImageView forecast6image2;
    private TextView forecast6weather2;
    private TextView forecast6wind1;
    private TextView forecast6wind2;

    private TextView forecast7day;
    private TextView forecast7date;
    private TextView forecast7weather1;
    private ImageView forecast7image1;
    private TextView forecast7temp1;
    private TextView forecast7temp2;
    private TextView forecast7weather2;
    private TextView forecast7wind1;
    private TextView forecast7wind2;

    private ImageView weatherimgtoday;
    private ImageView weatherimgtomorrow;
    private TextView temperaturetoday;
    private TextView temperaturetomorrow;
    private TextView weathertoday;
    private TextView weathertomorrow;
    private TextView bigweather;
    private TextView bigtempterature;
    private TextView selectcityname;
    private TextView hum;
    private TextView pres;
    private com.example.arnold.weather.fragement.weatherallview.Today24HourView myToday24HourView;
    private IndexHorizontalScrollView indexHorizontalScrollView;
    private Today24HourView today24HourView;
    private String mycity = null;
    private SharedPreferences sharedPreferences1;
    private SharedPreferences sharedPreferences2;
    private Button sharebutton;
    private Button suggestionbutton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weatherview, container, false);
//        loadChart();

        mode = true;

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

        sharebutton = (Button) view.findViewById(R.id.sharedsend);

        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Information = null;


                Information = "今日温度:" + sharedPreferences.getString("temp", "未知") + "\n" +
                        "今日天气:" + sharedPreferences.getString("dayWeather", "未知") + "\n" +
                        "出行建议:" + "\n" +
                        sharedPreferences.getString("suggestion_comf_txt", "未知") + "\n" +
                        sharedPreferences.getString("suggestion_drsg_txt", "未知") + "\n" +
                        sharedPreferences.getString("suggestion_flu_txt", "未知") + "\n" +
                        sharedPreferences.getString("suggestion_sport_txt", "未知") + "\n" +
                        sharedPreferences.getString("suggestion_trav_txt", "未知") + "\n";

                Uri smsToUri = Uri.parse("smsto:");
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                intent.putExtra("sms_body", Information);
                startActivity(intent);
            }
        });

        suggestion_comf = (TextView) view.findViewById(R.id.suggestion_comf_txt);
        suggestion_drsg = (TextView) view.findViewById(R.id.suggestion_drsg_txt);
        suggestion_flu = (TextView) view.findViewById(R.id.suggestion_flu_txt);
        suggestion_sport = (TextView) view.findViewById(R.id.suggestion_sport_txt);
        suggestion_trav = (TextView) view.findViewById(R.id.suggestion_trav_txt);

        suggestionbutton = (Button) view.findViewById(R.id.suggestbutton);

        suggestionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode) {
                    suggestion_comf.setVisibility(view.VISIBLE);
                    suggestion_drsg.setVisibility(view.VISIBLE);
                    suggestion_flu.setVisibility(view.VISIBLE);
                    suggestion_sport.setVisibility(view.VISIBLE);
                    suggestion_trav.setVisibility(view.VISIBLE);
                    suggestionbutton.setText("隐藏");
                    mode = false;
                } else {
                    suggestion_comf.setVisibility(view.INVISIBLE);
                    suggestion_drsg.setVisibility(view.INVISIBLE);
                    suggestion_flu.setVisibility(view.INVISIBLE);
                    suggestion_sport.setVisibility(view.INVISIBLE);
                    suggestion_trav.setVisibility(view.INVISIBLE);
                    suggestionbutton.setText("建议");
                    mode = true;
                }

            }
        });


        bigweather = (TextView) view.findViewById(R.id.bigweather);
        bigtempterature = (TextView) view.findViewById(R.id.bigtemperature);
        hum = (TextView) view.findViewById(R.id.textView7);
        pres = (TextView) view.findViewById(R.id.textView8);
//        wind = (TextView)view.findViewById(R.id.textView9);
        temperaturetoday = (TextView) view.findViewById(R.id.temperaturetoday);
        temperaturetomorrow = (TextView) view.findViewById(R.id.temperaturetomorrow);
        weathertoday = (TextView) view.findViewById(R.id.weathertoday);
        weathertomorrow = (TextView) view.findViewById(R.id.weathertomorrow);
        sunriseTime = (TextView) view.findViewById(R.id.sunuptime);
        sunsetTime = (TextView) view.findViewById(R.id.sundowntime);
        selectcityname = (TextView) view.findViewById(R.id.selectcityname);
        weatherimgtoday = (ImageView) view.findViewById(R.id.weatherimgtoday);
        weatherimgtomorrow = (ImageView) view.findViewById(R.id.weatherimgtomorrow);

        String countyName = mycity;
        sharedPreferences = getActivity().getSharedPreferences(countyName, Context.MODE_PRIVATE);
        sharedPreferences1 = getActivity().getSharedPreferences(countyName + "1", Context.MODE_APPEND);
        System.out.println("dsasdasd" + countyName + "1");
        sharedPreferences2 = getActivity().getSharedPreferences(countyName + "2", Context.MODE_APPEND);
        // 当countyName不为空
        if (!TextUtils.isEmpty(countyName)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("CountyName", countyName);
            editor.commit();
        } else {
            countyName = sharedPreferences.getString("CountyName", "");
        }
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

        suggestion_comf.setText(sharedPreferences.getString("suggestion_comf_txt", "未知"));
        suggestion_drsg.setText(sharedPreferences.getString("suggestion_drsg_txt", "未知"));
        suggestion_flu.setText(sharedPreferences.getString("suggestion_flu_txt", "未知"));
        suggestion_sport.setText(sharedPreferences.getString("suggestion_sport_txt", "未知"));
        suggestion_trav.setText(sharedPreferences.getString("suggestion_trav_txt", "未知"));

        weatherimgtoday.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences.getString("code", "999"))));
        weatherimgtomorrow.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences.getString("code_tomorrow", "999"))));
        temperaturetoday.setText(sharedPreferences.getString("temp", "未知"));
        temperaturetomorrow.setText(sharedPreferences.getString("temptomorrow", "未知"));
        weathertoday.setText(sharedPreferences.getString("dayWeather", "未知"));
        weathertomorrow.setText(sharedPreferences.getString("dayWeather_tomorrow", "未知"));
        sunriseTime.setText("日出：" + sharedPreferences.getString("sunriseTime", "未知"));
        sunsetTime.setText("日落：" + sharedPreferences.getString("sunsetTime", "未知"));
        hum.setText(sharedPreferences.getString("hum", "未知") + "%");
        pres.setText(sharedPreferences.getString("pres", "未知") + "hPa");
        bigtempterature.setText(sharedPreferences1.getString("hourly_temp0", "未知") + "°");
        bigweather.setText(sharedPreferences.getString("dayWeather", "未知"));
        selectcityname.setText(mycity);

        for (int i = 0; i < 7; i++) {
            setinformation(view);
//            forecastday.setText(sharedPreferences2.getString("forecastday","未知"));
        }

        Toast.makeText(getActivity(), mycity + "已经是最新数据了", Toast.LENGTH_SHORT).show();
    }

    public static int returnbackground(int code) {
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


    public void setinformation(View view) {
        Calendar c = Calendar.getInstance();
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week < 0) {
            week = 0;
        }

        forecast1day = (TextView) view.findViewById(R.id.forecast1day1);
        forecast1date = (TextView) view.findViewById(R.id.forecast1date1);
        forecast1weather1 = (TextView) view.findViewById(R.id.forecast1weather1);
        forecast1image1 = (ImageView) view.findViewById(R.id.forecast1image1);
        forecast1temp1 = (TextView) view.findViewById(R.id.forecast1temp1);
        forecast1temp2 = (TextView) view.findViewById(R.id.forecast1temp2);
        forecast1image2 = (ImageView) view.findViewById(R.id.forecast1image2);
        forecast1weather2 = (TextView) view.findViewById(R.id.forecast1weather2);
        forecast1wind1 = (TextView) view.findViewById(R.id.forecast1wind1);
        forecast1wind2 = (TextView) view.findViewById(R.id.forecast1wind2);

        forecast1day.setText("今天");
        forecast1date.setText(sharedPreferences2.getString("forecast1date", "未知"));
        forecast1weather1.setText(sharedPreferences2.getString("forecast1weather1", "未知"));
        forecast1image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast1image1", "999"))));
        forecast1temp1.setText(sharedPreferences2.getString("forecast1image1", "未知"));
        forecast1temp2.setText(sharedPreferences2.getString("forecast1temp2", "未知"));
        forecast1image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast1image2", "999"))));
        forecast1weather2.setText(sharedPreferences2.getString("forecast1weather2", "未知"));
        forecast1wind1.setText(sharedPreferences2.getString("forecast1wind1", "未知"));
        forecast1wind2.setText(sharedPreferences2.getString("forecast1wind2", "未知"));

        forecast2day = (TextView) view.findViewById(R.id.forecast2day1);
        forecast2date = (TextView) view.findViewById(R.id.forecast2date1);
        forecast2weather1 = (TextView) view.findViewById(R.id.forecast2weather1);
        forecast2image1 = (ImageView) view.findViewById(R.id.forecast2image1);
        forecast2temp1 = (TextView) view.findViewById(R.id.forecast2temp1);
        forecast2temp2 = (TextView) view.findViewById(R.id.forecast2temp2);
        forecast2image2 = (ImageView) view.findViewById(R.id.forecast2image2);
        forecast2weather2 = (TextView) view.findViewById(R.id.forecast2weather2);
        forecast2wind1 = (TextView) view.findViewById(R.id.forecast2wind1);
        forecast2wind2 = (TextView) view.findViewById(R.id.forecast2wind2);

        forecast2day.setText("明天");
        forecast2date.setText(sharedPreferences2.getString("forecast2date", "未知"));
        forecast2weather1.setText(sharedPreferences2.getString("forecast2weather1", "未知"));
        forecast2image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast2image1", "999"))));
        forecast2temp1.setText(sharedPreferences2.getString("forecast2image1", "未知"));
        forecast2temp2.setText(sharedPreferences2.getString("forecast2temp2", "未知"));
        forecast2image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast2image2", "999"))));
        forecast2weather2.setText(sharedPreferences2.getString("forecast1weather2", "未知"));
        forecast2wind1.setText(sharedPreferences2.getString("forecast2wind1", "未知"));
        forecast2wind2.setText(sharedPreferences2.getString("forecast2wind2", "未知"));

        forecast3day = (TextView) view.findViewById(R.id.forecast3day1);
        forecast3date = (TextView) view.findViewById(R.id.forecast3date1);
        forecast3weather1 = (TextView) view.findViewById(R.id.forecast3weather1);
        forecast3image1 = (ImageView) view.findViewById(R.id.forecast3image1);
        forecast3temp1 = (TextView) view.findViewById(R.id.forecast3temp1);
        forecast3temp2 = (TextView) view.findViewById(R.id.forecast3temp2);
        forecast3image2 = (ImageView) view.findViewById(R.id.forecast3image2);
        forecast3weather2 = (TextView) view.findViewById(R.id.forecast3weather2);
        forecast3wind1 = (TextView) view.findViewById(R.id.forecast3wind1);
        forecast3wind2 = (TextView) view.findViewById(R.id.forecast3wind2);

        forecast3day.setText(weekDays[(week + 1) % 7]);
        forecast3date.setText(sharedPreferences2.getString("forecast3date", "未知"));
        forecast3weather1.setText(sharedPreferences2.getString("forecast3weather1", "未知"));
        forecast3image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast3image1", "999"))));
        forecast3temp1.setText(sharedPreferences2.getString("forecast3mage1", "未知"));
        forecast3temp2.setText(sharedPreferences2.getString("forecast3temp2", "未知"));
        forecast3image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast3image2", "999"))));
        forecast3weather2.setText(sharedPreferences2.getString("forecast3weather2", "未知"));
        forecast3wind1.setText(sharedPreferences2.getString("forecast3wind1", "未知"));
        forecast3wind2.setText(sharedPreferences2.getString("forecast3wind2", "未知"));

        forecast4day = (TextView) view.findViewById(R.id.forecast4day1);
        forecast4date = (TextView) view.findViewById(R.id.forecast4date1);
        forecast4weather1 = (TextView) view.findViewById(R.id.forecast4weather1);
        forecast4image1 = (ImageView) view.findViewById(R.id.forecast4image1);
        forecast4temp1 = (TextView) view.findViewById(R.id.forecast4temp1);
        forecast4temp2 = (TextView) view.findViewById(R.id.forecast4temp2);
        forecast4image2 = (ImageView) view.findViewById(R.id.forecast4image2);
        forecast4weather2 = (TextView) view.findViewById(R.id.forecast4weather2);
        forecast4wind1 = (TextView) view.findViewById(R.id.forecast4wind1);
        forecast4wind2 = (TextView) view.findViewById(R.id.forecast4wind2);

        forecast4day.setText(weekDays[(week + 2) % 7]);
        forecast4date.setText(sharedPreferences2.getString("forecast4date", "未知"));
        forecast4weather1.setText(sharedPreferences2.getString("forecast4weather1", "未知"));
        forecast4image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast4image1", "999"))));
        forecast4temp1.setText(sharedPreferences2.getString("forecast4image1", "未知"));
        forecast4temp2.setText(sharedPreferences2.getString("forecast4temp2", "未知"));
        forecast4image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast4image2", "999"))));
        forecast4weather2.setText(sharedPreferences2.getString("forecast4weather2", "未知"));
        forecast4wind1.setText(sharedPreferences2.getString("forecast4wind1", "未知"));
        forecast4wind2.setText(sharedPreferences2.getString("forecast4wind2", "未知"));

        forecast5day = (TextView) view.findViewById(R.id.forecast5day1);
        forecast5date = (TextView) view.findViewById(R.id.forecast5date1);
        forecast5weather1 = (TextView) view.findViewById(R.id.forecast5weather1);
        forecast5image1 = (ImageView) view.findViewById(R.id.forecast5image1);
        forecast5temp1 = (TextView) view.findViewById(R.id.forecast5temp1);
        forecast5temp2 = (TextView) view.findViewById(R.id.forecast5temp2);
        forecast5image2 = (ImageView) view.findViewById(R.id.forecast5image2);
        forecast5weather2 = (TextView) view.findViewById(R.id.forecast5weather2);
        forecast5wind1 = (TextView) view.findViewById(R.id.forecast5wind1);
        forecast5wind2 = (TextView) view.findViewById(R.id.forecast5wind2);

        forecast5day.setText(weekDays[(week + 3) % 7]);
        forecast5date.setText(sharedPreferences2.getString("forecast5date", "未知"));
        forecast5weather1.setText(sharedPreferences2.getString("forecast5weather1", "未知"));
        forecast5image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast5image1", "999"))));
        forecast5temp1.setText(sharedPreferences2.getString("forecast5image1", "未知"));
        forecast5temp2.setText(sharedPreferences2.getString("forecast5temp2", "未知"));
        forecast5image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast5image2", "999"))));
        forecast5weather2.setText(sharedPreferences2.getString("forecast5weather2", "未知"));
        forecast5wind1.setText(sharedPreferences2.getString("forecast5wind1", "未知"));
        forecast5wind2.setText(sharedPreferences2.getString("forecast5wind2", "未知"));

        forecast6day = (TextView) view.findViewById(R.id.forecast6day1);
        forecast6date = (TextView) view.findViewById(R.id.forecast6date1);
        forecast6weather1 = (TextView) view.findViewById(R.id.forecast6weather1);
        forecast6image1 = (ImageView) view.findViewById(R.id.forecast6image1);
        forecast6temp1 = (TextView) view.findViewById(R.id.forecast6temp1);
        forecast6temp2 = (TextView) view.findViewById(R.id.forecast6temp2);
        forecast6image2 = (ImageView) view.findViewById(R.id.forecast6image2);
        forecast6weather2 = (TextView) view.findViewById(R.id.forecast6weather2);
        forecast6wind1 = (TextView) view.findViewById(R.id.forecast6wind1);
        forecast6wind2 = (TextView) view.findViewById(R.id.forecast6wind2);

        forecast6day.setText(weekDays[(week + 4) % 7]);
        forecast6date.setText(sharedPreferences2.getString("forecast6date", "未知"));
        forecast6weather1.setText(sharedPreferences2.getString("forecast6weather1", "未知"));
        forecast6image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast6image1", "999"))));
        forecast6temp1.setText(sharedPreferences2.getString("forecast6image1", "未知"));
        forecast6temp2.setText(sharedPreferences2.getString("forecast6temp2", "未知"));
        forecast6image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast6image2", "999"))));
        forecast6weather2.setText(sharedPreferences2.getString("forecast6weather2", "未知"));
        forecast6wind1.setText(sharedPreferences2.getString("forecast6wind1", "未知"));
        forecast6wind2.setText(sharedPreferences2.getString("forecast6wind2", "未知"));

        forecast7day = (TextView) view.findViewById(R.id.forecast7day1);
        forecast7date = (TextView) view.findViewById(R.id.forecast7date1);
        forecast7weather1 = (TextView) view.findViewById(R.id.forecast7weather1);
        forecast7image1 = (ImageView) view.findViewById(R.id.forecast7image1);
        forecast7temp1 = (TextView) view.findViewById(R.id.forecast7temp1);
        forecast7temp2 = (TextView) view.findViewById(R.id.forecast7temp2);
        ImageView forecast7image2 = (ImageView) view.findViewById(R.id.forecast7image2);
        forecast7weather2 = (TextView) view.findViewById(R.id.forecast7weather2);
        forecast7wind1 = (TextView) view.findViewById(R.id.forecast7wind1);
        forecast7wind2 = (TextView) view.findViewById(R.id.forecast7wind2);

        forecast7day.setText(weekDays[(week + 5) % 7]);
        forecast7date.setText(sharedPreferences2.getString("forecast7date", "未知"));
        forecast7weather1.setText(sharedPreferences2.getString("forecast7weather1", "未知"));
        forecast7image1.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast7image1", "999"))));
        forecast7temp1.setText(sharedPreferences2.getString("forecast7image1", "未知"));
        forecast7temp2.setText(sharedPreferences2.getString("forecast7temp2", "未知"));
        forecast7image2.setBackgroundResource(returnbackground(Integer.valueOf(sharedPreferences2.getString("forecast7image2", "999"))));
        forecast7weather2.setText(sharedPreferences2.getString("forecast7weather2", "未知"));
        forecast7wind1.setText(sharedPreferences2.getString("forecast7wind1", "未知"));
        forecast7wind2.setText(sharedPreferences2.getString("forecast7wind2", "未知"));

    }

}
