package com.uniqolabel.weatherapp.repositories;

import com.uniqolabel.weatherapp.interfaces.WeatherMainURL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {

    private WeatherMainURL weatherMainURL;
    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherMainURL.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
        weatherMainURL = retrofit.create(WeatherMainURL.class);
    }
}
