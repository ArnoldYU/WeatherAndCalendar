package com.example.arnold.weather.weathergetbyinternate.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import com.example.arnold.weather.fragement.test2;
import com.example.arnold.weather.fragement.weatherallview.weatherview;
import com.example.arnold.weather.weathergetbyinternate.Activity.WeatherActivity;
import com.example.arnold.weather.weathergetbyinternate.Database.WeatherDB;
import com.example.arnold.weather.weathergetbyinternate.Model.City;
import com.example.arnold.weather.weathergetbyinternate.Model.County;
import com.example.arnold.weather.weathergetbyinternate.Model.HourlyWeather;
import com.example.arnold.weather.weathergetbyinternate.Model.Province;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    // 保存服务器返回的省级数据
    public static boolean saveProvincesResponse(WeatherDB weatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                Province province;
                List<Province> provinceList = new ArrayList<>();
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    province = new Province();
                    //province.setProvinceId("01");
                    //province.setProvinceName("beijing");
                    province.setProvinceId(array[0]);
                    province.setProvinceName(array[1]);
                    provinceList.add(province);

                }
                weatherDB.saveProvinces(provinceList);
                return true;
            }
        }
        return false;
    }

    // 保存服务器返回的市级数据
    public static boolean saveCitiesResponse(WeatherDB weatherDB, String response, String provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                City city;
                List<City> cityList = new ArrayList<>();
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    city = new City();
                    /*
                    city.setCityId("00");
                    city.setCityName("beijing");
                    city.setProvinceId("01");*/

                    city.setCityId(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    cityList.add(city);

                }
                weatherDB.saveCities(cityList);
                return true;
            }
        }
        return false;
    }

    // 保存服务器返回的县级数据
    public static boolean saveCountiesResponse(WeatherDB weatherDB, String response, String cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                County county;
                List<County> countyList = new ArrayList<>();
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    county = new County();
                    /*county.setCityId("00");
                    county.setCountyId("01");
                    county.setCountyName("beijing");*/
                    county.setCountyId(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    countyList.add(county);
                }
                weatherDB.saveCounties(countyList);
                return true;
            }
        }
        return false;
    }

    // 处理服务器返回的json数据
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonobject = new JSONObject(response);
            JSONObject jjjj = jsonobject.getJSONObject("result");
            JSONArray title = jjjj.getJSONArray("HeWeather5");
            Log.d("wwwww", "handleWeatherResponse: ");
            JSONObject first_object = (JSONObject) title.get(0);

            JSONObject basic = (JSONObject) first_object.get("basic");

            //更新时间
            JSONObject update = (JSONObject) basic.get("update");
            JSONArray daily_forecast = (JSONArray) first_object.get("daily_forecast");

            JSONObject daily_forecast_first = (JSONObject) daily_forecast.get(0);
            JSONObject daily_forecast_second = (JSONObject) daily_forecast.get(1);

//--------------------------------------------
            //今明两天天气
            JSONObject cond = (JSONObject) daily_forecast_first.get("cond");
            JSONObject cond_tomorrow = (JSONObject) daily_forecast_second.get("cond");
            //今明两天的温度
            JSONObject temp = (JSONObject) daily_forecast_first.get("tmp");
            JSONObject temptomorrow = (JSONObject) daily_forecast_second.get("tmp");
            //今明两天天气图标

//--------------------------------------------

            JSONObject astro = (JSONObject) daily_forecast_first.get("astro");

            JSONObject wind = (JSONObject) daily_forecast_first.get("wind");

            JSONArray hourly_forecast = (JSONArray) first_object.get("hourly_forecast");

            WeatherActivity.weatherList.clear();
//城市名
            String cityName = basic.getString("city");
            SharedPreferences.Editor editor = context.getSharedPreferences(cityName + "1", Context.MODE_PRIVATE).edit();
            int level = 0;
            for (int i = 0; i < hourly_forecast.length(); i++) {
                JSONObject json = hourly_forecast.getJSONObject(i);
                JSONObject json_wind = (JSONObject) json.get("wind");
                String date = json.getString("date");
                String[] array = date.split(" ");
                String dir = json_wind.getString("dir");
                String sc = json_wind.getString("sc");
                String hourly_clock = array[1];
                String hourly_temp = "温度：" + json.getString("tmp") + "℃";
                String hourly_pop = "降水概率：" + json.getString("pop");
                String hourly_wind = "风力：" + dir + " " + sc + "级";
                HourlyWeather weather = new HourlyWeather(hourly_clock, hourly_temp, hourly_pop, hourly_wind);

                switch (sc) {
                    case "无风":
                        level = 1;
                        break;
                    case "轻风":
                        level = 2;
                        break;
                    case "微风":
                        level = 3;
                        break;
                    case "和风":
                        level = 4;
                        break;
                    case "轻劲风":
                        level = 5;
                        break;
                    case "强风":
                        level = 6;
                        break;
                    case "疾风":
                        level = 7;
                        break;
                    case "大风":
                        level = 8;
                        break;
                    case "烈风":
                        level = 9;
                        break;
                    case "狂风":
                        level = 10;
                        break;
                    case "暴风":
                        level = 11;
                        break;
                    case "台风":
                        level = 12;
                        break;
                    default:
                        level = 3;
                }
                System.out.println(hourly_clock);
                editor.putString("hourly_clock" + i, hourly_clock);
                System.out.println(json.getString("tmp"));
                editor.putString("hourly_temp" + i, json.getString("tmp"));
                System.out.println(level);
                editor.putString("hourly_wind" + i, String.valueOf(level));
//                WeatherActivity.weatherList.add(weather);
                test2.weatherList.add(weather);
                weatherview.weatherList.add(weather);
            }
            editor.putInt("hourly_forecast_length", hourly_forecast.length());
            editor.commit();


            //夜晚天气
            String nightWeather = cond.getString("txt_n");
            //风力
            String windText = wind.getString("dir") + " " + wind.getString("sc") + "级";
            //降水概率
            String pop = daily_forecast_first.getString("pop");
//--------------------------------------------------------------
            //日出
            String sunriseTime = astro.getString("sr");
            //日落
            String sunsetTime = astro.getString("ss");
            //今天天气
            String dayWeather = cond.getString("txt_n");
            //明天天气
            String dayWeather_tomorrow = cond_tomorrow.getString("txt_n");
            //今天温度
            String tempText = temp.getString("min") + "/" + temp.getString("max") + "℃";
            //明日温度
            String tempText_tomorrow = temptomorrow.getString("min") + "/" + temp.getString("max") + "℃";
            //今天天气图标
            String code = cond.getString("code_n");
            //明天天气图标
            String code_tomorrow = cond_tomorrow.getString("code_n");

//--------------------------------------------------------------
            //更新时间
            String updateTime = update.getString("loc");

            saveWeatherInfo(context, cityName, sunriseTime, sunsetTime, dayWeather, nightWeather, windText, pop, tempText, updateTime,
                    tempText_tomorrow, dayWeather_tomorrow, code, code_tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveWeatherInfo(Context context, String cityName,
                                        String sunriseTime, String sunsetTime, String dayWeather, String nightWeather,
                                        String windText, String pop, String tempText, String updateTime, String tempText_tomorrow,
                                        String dayWeather_tomorrow, String code, String code_tomorrow) {

        SharedPreferences.Editor editor = context.getSharedPreferences(cityName, Context.MODE_PRIVATE).edit();
        editor.putString("cityName", cityName);
        editor.putString("sunriseTime", sunriseTime);//
        editor.putString("sunsetTime", sunsetTime);//
        editor.putString("dayWeather", dayWeather);//
        editor.putString("dayWeather_tomorrow", dayWeather_tomorrow);//
        editor.putString("nightWeather", nightWeather);
        editor.putString("wind", windText);
        editor.putString("pop", pop);
        editor.putString("temp", tempText);//
        editor.putString("temptomorrow", tempText_tomorrow);//
        editor.putString("code",code);//
        editor.putString("code_tomorrow",code_tomorrow);//
        editor.putString("updateTime", updateTime);
        editor.commit();
    }
}
