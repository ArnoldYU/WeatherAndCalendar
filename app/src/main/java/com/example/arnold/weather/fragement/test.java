package com.example.arnold.weather.fragement;

/**
 * Created by Arnold on 2017/7/16.
 */

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arnold.weather.MainActivity;
import com.example.arnold.weather.R;
import com.example.arnold.weather.weathergetbyinternate.Activity.ChooseAreaActivity;
import com.example.arnold.weather.weathergetbyinternate.Activity.WeatherActivity;
import com.example.arnold.weather.weathergetbyinternate.Database.WeatherDB;
import com.example.arnold.weather.weathergetbyinternate.Model.City;
import com.example.arnold.weather.weathergetbyinternate.Model.County;
import com.example.arnold.weather.weathergetbyinternate.Model.Province;
import com.example.arnold.weather.weathergetbyinternate.Net.HttpUtil;
import com.example.arnold.weather.weathergetbyinternate.Util.HttpCallbackListener;
import com.example.arnold.weather.weathergetbyinternate.Util.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class test extends Fragment {
    // 标记当前列表为省份
    public static final int LEVEL_PROVINCE = 0;
    // 标记当前列表为城市
    public static final int LEVEL_CITY = 1;
    // 标记当前列表为县
    public static final int LEVEL_COUNTY = 2;
    // 进度对话框
    private ProgressDialog progressDialog;
    // 标题栏
    private TextView titleText;
    // 数据列表
    private ListView listView;
    // 列表数据
    private ArrayAdapter<String> adapter;
    // 数据库
    private WeatherDB weatherDB;

    private List<String> dataList;

    private List<Province> provinceList;

    private List<City> cityList;

    private List<County> countyList;
    //选择的省份
    private Province selectedProvince;
    //选择的城市
    private City selectedCity;
    //当前选择的列表类型
    private int currentLevel;
    //标记是否从WeatherActivity跳转而来的
    private boolean isFromWeatherActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.activity_choose_area, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        titleText = (TextView) view.findViewById(R.id.title);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        weatherDB = WeatherDB.getInstance(getActivity());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(index);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(index);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    //当点击到县列表时，就利用Intent跳转到天气信息界面
                    String countyName = countyList.get(index).getCountyName();
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("CountyName", countyName);
                    startActivity(intent);
                }
            }
        });
        queryProvinces();
        return view;
    }

    private void queryProvinces() {
        showProgressDialog();
        provinceList = weatherDB.getAllProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
            closeProgressDialog();
        } else {
            queryFromServer(null, "province");
        }
    }

    private void queryCities() {
        showProgressDialog();
        cityList = weatherDB.getAllCity(selectedProvince.getProvinceId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
            closeProgressDialog();
        } else {
            queryFromServer(selectedProvince.getProvinceId(), "city");
        }
    }

    private void queryCounties() {
        showProgressDialog();
        countyList = weatherDB.getAllCountry(selectedCity.getCityId());
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
            closeProgressDialog();
        } else {
            queryFromServer(selectedCity.getCityId(), "county");
        }
    }

    private void queryFromServer(final String code, final String type) {
        String address;
        // code不为空
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        System.out.println(type);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.saveProvincesResponse(weatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.saveCitiesResponse(weatherDB, response, selectedProvince.getProvinceId());
                } else if ("county".equals(type)) {
                    result = Utility.saveCountiesResponse(weatherDB, response, selectedCity.getCityId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("加载");
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        closeProgressDialog();
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
