package com.uniqolabel.weatherapp.interfaces;

import com.uniqolabel.weatherapp.model.CurrentWeatherResponse;
import com.uniqolabel.weatherapp.model.WeatherForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherMainEndpoint {
    String WEATHER_URL= "http://api.openweathermap.org/";

    @GET("data/2.5/forecast")
    Call<WeatherForecastResponse> getWeatherForecastInfo(@Query("lat") String latitude,
                                                         @Query("lon") String longitude,
                                                         @Query("units") String units,
                                                         @Query("appid") String appid);
    @GET("data/2.5/weather")
    Call<CurrentWeatherResponse> getCurrentWeatherInfo (@Query("lat") String latitude,
                                                 @Query("lon") String longitude,
                                                 @Query("units") String units,
                                                 @Query("appid") String appid);

}
