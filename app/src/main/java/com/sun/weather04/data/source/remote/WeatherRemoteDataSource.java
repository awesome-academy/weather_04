package com.sun.weather04.data.source.remote;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.source.WeatherDataSource;
import com.sun.weather04.utils.Constant;

public class WeatherRemoteDataSource implements WeatherDataSource.RemoteDataSource {

    private static WeatherRemoteDataSource sInstance;

    public static WeatherRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getWeatherData(OnFetchDataListener<DataResponse> listener, double longitude, double latitude) {
        new GetCurrentlyWeatherAsyncTask(listener).execute(Constant.URL_CURRENT_WEATHER + "/"+longitude+ ","+ latitude);
    }
}
