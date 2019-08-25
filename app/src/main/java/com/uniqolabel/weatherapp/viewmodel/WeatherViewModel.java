package com.uniqolabel.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.uniqolabel.weatherapp.model.CurrentWeatherResponse;
import com.uniqolabel.weatherapp.model.WeatherForecastResponse;
import com.uniqolabel.weatherapp.repositories.WeatherRepository;

public class WeatherViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<WeatherForecastResponse> weatherSuccessResponseMutableLiveData;
    private MutableLiveData<CurrentWeatherResponse> currentWeatherResponseMutableLiveData;
    private WeatherRepository weatherRepository;
    public static MutableLiveData<Boolean> loading;
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        weatherSuccessResponseMutableLiveData = new MutableLiveData<>();
        weatherRepository = WeatherRepository.getInstance();
        loading = new MutableLiveData<>();
    }

    public LiveData<WeatherForecastResponse> getWeatherInfoNetworkCall(String latitude, String longitude, String units, String appid){
        weatherSuccessResponseMutableLiveData = weatherRepository.getWeatherReportFromNetwork(latitude,longitude,units,appid);
        return weatherSuccessResponseMutableLiveData;
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherInfoNetworkCall(String latitude, String longitude, String units, String appid){
        currentWeatherResponseMutableLiveData = weatherRepository.getCurrentWeatherReportFromNetwork(latitude,longitude,units,appid);
        return currentWeatherResponseMutableLiveData;
    }

}
