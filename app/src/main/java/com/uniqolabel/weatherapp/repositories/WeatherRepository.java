package com.uniqolabel.weatherapp.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.uniqolabel.weatherapp.interfaces.WeatherMainEndpoint;
import com.uniqolabel.weatherapp.model.CurrentWeatherResponse;
import com.uniqolabel.weatherapp.model.WeatherForecastResponse;
import com.uniqolabel.weatherapp.viewmodel.WeatherViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {

    private static final String TAG = "WeatherRepository";
    private MutableLiveData<WeatherForecastResponse> responseMutableLiveData;
    private MutableLiveData<CurrentWeatherResponse> currentWeatherResponseMutableLiveData;
    private WeatherMainEndpoint weatherMainEndpoint;
    private static WeatherRepository weatherRepository;

    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherMainEndpoint.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherMainEndpoint = retrofit.create(WeatherMainEndpoint.class);
    }

    public synchronized static WeatherRepository getInstance() {
        if (weatherRepository == null) {
            weatherRepository = new WeatherRepository();
        }
        return weatherRepository;
    }

    public MutableLiveData<WeatherForecastResponse> getWeatherReportFromNetwork(String latitude, String longitude, String units, String appid) {
        responseMutableLiveData = new MutableLiveData<>();
        WeatherViewModel.loading.setValue(true);
        weatherMainEndpoint.getWeatherForecastInfo(latitude, longitude, units, appid).enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                WeatherViewModel.loading.setValue(false);
                if (response.isSuccessful()) {
                    responseMutableLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: failure "+response.raw().request().url());
                }
                else Log.d(TAG, "onResponse: "+response.raw().request().url());
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                WeatherViewModel.loading.setValue(false);
                Log.d(TAG, "onFailure: "+t.getMessage());
                Log.d(TAG, "onResponse: failure "+call.request().url());
                responseMutableLiveData.setValue(null);
            }
        });
        return responseMutableLiveData;
    }

    public MutableLiveData<CurrentWeatherResponse> getCurrentWeatherReportFromNetwork(String latitude, String longitude, String units, String appid) {
        currentWeatherResponseMutableLiveData = new MutableLiveData<>();
        WeatherViewModel.loading.setValue(true);
        weatherMainEndpoint.getCurrentWeatherInfo(latitude, longitude, units, appid).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                WeatherViewModel.loading.setValue(false);
                if (response.isSuccessful()) {
                    currentWeatherResponseMutableLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: "+response.raw().request().url());
                }
                else Log.d(TAG, "onResponse parse failure: "+response.raw().request().url());
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                WeatherViewModel.loading.setValue(false);
                Log.d(TAG, "onFailure: "+t.getMessage());
                Log.d(TAG, "onResponse failure: "+call.request().url());
                currentWeatherResponseMutableLiveData.setValue(null);
            }
        });
        return currentWeatherResponseMutableLiveData;
    }
}
