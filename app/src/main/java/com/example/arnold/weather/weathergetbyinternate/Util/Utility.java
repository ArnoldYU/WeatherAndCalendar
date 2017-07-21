package com.example.arnold.weather.weathergetbyinternate.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


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
            String cityName = basic.getString("city");

            //获得出行建议
            JSONObject suggestion = (JSONObject) first_object.get("suggestion");
            JSONObject suggestion_comf = (JSONObject) suggestion.get("comf");
            JSONObject suggestion_drsg = (JSONObject)suggestion.get("drsg");
            JSONObject suggestion_flu = (JSONObject)suggestion.get("flu");
            JSONObject suggestion_sport = (JSONObject)suggestion.get("sport");
            JSONObject suggestion_trav = (JSONObject) suggestion.get("trav");

            String suggestion_comf_txt = suggestion_comf.getString("txt");
            String suggestion_drsg_txt = suggestion_drsg.getString("txt");
            String suggestion_flu_txt = suggestion_flu.getString("txt");
            String suggestion_sport_txt = suggestion_sport.getString("txt");
            String suggestion_trav_txt = suggestion_trav.getString("txt");

            JSONObject daily_forecast_first = (JSONObject) daily_forecast.get(0);
            JSONObject daily_forecast_second = (JSONObject) daily_forecast.get(1);
            JSONObject daily_forecast_third = (JSONObject) daily_forecast.get(2);
            JSONObject daily_forecast_fourth = (JSONObject) daily_forecast.get(3);
            JSONObject daily_forecast_fifth = (JSONObject) daily_forecast.get(4);
            JSONObject daily_forecast_sixth = (JSONObject) daily_forecast.get(5);
            JSONObject daily_forecast_seventh = (JSONObject) daily_forecast.get(6);
            JSONObject nowday = null;
            //获得近七天的信息
            SharedPreferences.Editor editor1 = context.getSharedPreferences(cityName + "2", Context.MODE_PRIVATE).edit();
            String forecastdate;
            String forecastweather1;
            String forecastimage1;
            String forecasttemp1;
            String forecasttemp2;
            String forecastimage2;
            String forecastweather2;
            String forecastwind1;
            String forecastwind2;
            int lala;
            for (int i = 0; i < 7; i++) {
                lala = i + 1;
                switch (i) {
                    case 0:
                        nowday = daily_forecast_first;
                        break;
                    case 1:
                        nowday = daily_forecast_second;
                        break;
                    case 2:
                        nowday = daily_forecast_third;
                        break;
                    case 3:
                        nowday = daily_forecast_fourth;
                        break;
                    case 4:
                        nowday = daily_forecast_fifth;
                        break;
                    case 5:
                        nowday = daily_forecast_sixth;
                        break;
                    case 6:
                        nowday = daily_forecast_seventh;
                        break;
                    default:
                }
//                System.out.println("noday:"+nowday.getString("date"));
                forecastdate = nowday.getString("date");
                forecastdate = forecastdate.substring(5);
                JSONObject forecastcond = (JSONObject) nowday.get("cond");
                JSONObject forecasttmp = (JSONObject) nowday.get("tmp");
                JSONObject forecastwind = (JSONObject) nowday.get("wind");
                forecasttemp1 = forecasttmp.getString("max");
                forecasttemp2 = forecasttmp.getString("min");
                forecastweather1 = forecastcond.getString("txt_d");
                forecastimage1 = forecastcond.getString("code_d");
                forecastweather2 = forecastcond.getString("txt_n");
                forecastimage2 = forecastcond.getString("code_n");
                forecastwind1 = forecastwind.getString("dir");
                forecastwind2 = forecastwind.getString("sc") + "级";

                editor1.putString("forecast" + lala + "date", forecastdate);
                editor1.putString("forecast" + lala + "temp1", forecasttemp1);
                editor1.putString("forecast" + lala + "temp2", forecasttemp2);
                editor1.putString("forecast" + lala + "weather1", forecastweather1);
                editor1.putString("forecast" + lala + "image1", forecastimage1);
                editor1.putString("forecast" + lala + "weather2", forecastweather2);
                editor1.putString("forecast" + lala + "image2", forecastimage2);
                editor1.putString("forecast" + lala + "wind1", forecastwind1);
                editor1.putString("forecast" + lala + "wind2", forecastwind2);

            }
            editor1.commit();

//--------------------------------------------

            //实况信息
            JSONObject now = (JSONObject) first_object.get("now");
            //今明两天天气
            JSONObject cond = (JSONObject) daily_forecast_first.get("cond");
            JSONObject cond_tomorrow = (JSONObject) daily_forecast_second.get("cond");
            //今明两天的温度
            JSONObject temp = (JSONObject) daily_forecast_first.get("tmp");
            JSONObject temptomorrow = (JSONObject) daily_forecast_second.get("tmp");

//--------------------------------------------

            JSONObject astro = (JSONObject) daily_forecast_first.get("astro");

            JSONObject wind = (JSONObject) daily_forecast_first.get("wind");

            JSONArray hourly_forecast = (JSONArray) first_object.get("hourly_forecast");

            WeatherActivity.weatherList.clear();
//城市名
            SharedPreferences.Editor editor = context.getSharedPreferences(cityName + "1", Context.MODE_PRIVATE).edit();
            int level = 0;
            for (int i = 0; i < hourly_forecast.length(); i++) {
                JSONObject json = hourly_forecast.getJSONObject(i);
                JSONObject json_wind = (JSONObject) json.get("wind");
                JSONObject json_cond = (JSONObject) json.get("cond");
                String date = json.getString("date");
                String[] array = date.split(" ");
                String dir = json_wind.getString("dir");
                String sc = json_wind.getString("sc");
                String hourly_clock = array[1];
                String hourly_temp = "温度：" + json.getString("tmp") + "℃";
                String hourly_pop = "降水概率：" + json.getString("pop");
                String hourly_wind = "风力：" + dir + " " + sc + "级";
                String hourly_code = json_cond.getString("code");
                HourlyWeather weather = new HourlyWeather(hourly_clock, hourly_temp, hourly_pop, hourly_wind);

                editor.putString("hourly_code" + i, hourly_code);
                level = returnlevel(sc);
//                System.out.println(hourly_clock);
                editor.putString("hourly_clock" + i, hourly_clock);
//                System.out.println(json.getString("tmp"));
                editor.putString("hourly_temp" + i, json.getString("tmp"));
//                System.out.println(level);
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
            String sc = wind.getString("sc");
            String mywind = returnlevel(sc) + "级";

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
            //湿度
            String hum = now.getString("hum");
            //气压
            String pres = now.getString("pres");

//--------------------------------------------------------------
            //更新时间
            String updateTime = update.getString("loc");

            saveWeatherInfo(context, cityName, sunriseTime, sunsetTime, dayWeather, nightWeather, windText, pop, tempText, updateTime,
                    tempText_tomorrow, dayWeather_tomorrow, code, code_tomorrow, hum, pres, mywind,suggestion_comf_txt,suggestion_drsg_txt,
                    suggestion_flu_txt,suggestion_sport_txt,suggestion_trav_txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void saveWeatherInfo(Context context, String cityName,
                                        String sunriseTime, String sunsetTime, String dayWeather, String nightWeather,
                                        String windText, String pop, String tempText, String updateTime, String tempText_tomorrow,
                                        String dayWeather_tomorrow, String code, String code_tomorrow, String hum, String pres,
                                        String mywind,String suggestion_comf_txt,String suggestion_drsg_txt,
                                        String suggestion_flu_txt, String suggestion_sport_txt,String suggestion_trav_txt) {

        SharedPreferences.Editor editor = context.getSharedPreferences(cityName, Context.MODE_PRIVATE).edit();
        editor.putString("cityName", cityName);
        editor.putString("sunriseTime", sunriseTime);//
        editor.putString("sunsetTime", sunsetTime);//
        editor.putString("dayWeather", dayWeather);//
        editor.putString("dayWeather_tomorrow", dayWeather_tomorrow);//
        editor.putString("nightWeather", nightWeather);
        editor.putString("wind", windText);
        editor.putString("mywind", mywind);//
        editor.putString("pop", pop);
        editor.putString("temp", tempText);//
        editor.putString("temptomorrow", tempText_tomorrow);//
        editor.putString("code", code);//
        editor.putString("code_tomorrow", code_tomorrow);//
        editor.putString("hum", hum);
        editor.putString("pres", pres);
        editor.putString("updateTime", updateTime);
        editor.putString("suggestion_comf_txt",suggestion_comf_txt);
        editor.putString("suggestion_drsg_txt",suggestion_drsg_txt);
        editor.putString("suggestion_flu_txt",suggestion_flu_txt);
        editor.putString("suggestion_sport_txt",suggestion_sport_txt);
        editor.putString("suggestion_trav_txt",suggestion_trav_txt);

        editor.commit();
    }

    private static int returnlevel(String sc) {
        int level = 0;
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
        return level;
    }

}
