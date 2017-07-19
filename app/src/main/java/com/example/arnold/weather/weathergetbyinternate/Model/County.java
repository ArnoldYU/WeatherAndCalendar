package com.example.arnold.weather.weathergetbyinternate.Model;

/**
 * 地区
 */
public class County {

    private String countyName;

    private String countyId;

    private String cityId;

    public String getCityId() {
        return cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
