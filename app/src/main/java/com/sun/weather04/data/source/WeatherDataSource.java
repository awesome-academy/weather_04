package com.sun.weather04.data.source;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.source.remote.OnFetchDataListener;

public interface WeatherDataSource {
    interface RemoteDataSource {
        void getWeatherData(OnFetchDataListener<DataResponse> listener, double longitude, double latitude);
    }
}
