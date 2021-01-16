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

    private MutableLiveData<WeatherForecastResponse> weatherSuccessResponseMutableLiveData;
    private final WeatherRepository weatherRepository;
    public static MutableLiveData<Boolean> loading;

    public WeatherViewModel(@NonNull final Application application) {
        super(application);
        weatherSuccessResponseMutableLiveData = new MutableLiveData<>();
        weatherRepository = WeatherRepository.getInstance();
        loading = new MutableLiveData<>();
    }

    public LiveData<WeatherForecastResponse> getWeatherInfoNetworkCall(final String latitude, final String longitude, String units, String appid){
        weatherSuccessResponseMutableLiveData = weatherRepository.getWeatherReportFromNetwork(latitude,longitude,units,appid);
        return weatherSuccessResponseMutableLiveData;
    }

    public LiveData<CurrentWeatherResponse> getCurrentWeatherInfoNetworkCall(final String latitude, final String longitude, String units, String appid){
        MutableLiveData<CurrentWeatherResponse> currentWeatherResponseMutableLiveData = weatherRepository.getCurrentWeatherReportFromNetwork(latitude, longitude, units, appid);
        return currentWeatherResponseMutableLiveData;
    }

}
