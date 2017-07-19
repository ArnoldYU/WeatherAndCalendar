package com.example.arnold.weather.weathergetbyinternate.Model;

/**
 * 城市
 */
public class City {

    private String cityName;

    private String cityId;

    private String provinceId;

    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
