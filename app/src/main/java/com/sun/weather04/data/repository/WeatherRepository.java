package com.sun.weather04.data.repository;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.source.WeatherDataSource;
import com.sun.weather04.data.source.remote.OnFetchDataListener;

public class WeatherRepository {
    private static WeatherRepository sInstance;
    private WeatherDataSource.RemoteDataSource mRemoteDataSource;

    private WeatherRepository(WeatherDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static WeatherRepository getInstance(
            WeatherDataSource.RemoteDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new WeatherRepository(remoteDataSource);
        }
        return sInstance;
    }

    public void getWeatherData(OnFetchDataListener<DataResponse> listener, double longitude, double latitude) {
        mRemoteDataSource.getWeatherData(listener, longitude, latitude);
    }
}
