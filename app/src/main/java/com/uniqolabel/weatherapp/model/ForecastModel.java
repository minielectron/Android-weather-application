package com.uniqolabel.weatherapp.model;

public class ForecastModel {

    private String dayName;
    private String tempRange;
    private String date;
    private String iconName;

    public ForecastModel() {
    }

    public ForecastModel(String dayName, String tempRange, String date, String iconName) {
        this.dayName = dayName;
        this.tempRange = tempRange;
        this.date = date;
        this.iconName = iconName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
