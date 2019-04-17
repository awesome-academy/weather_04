package com.sun.weather04.screen.home;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.repository.WeatherRepository;
import com.sun.weather04.data.source.remote.OnFetchDataListener;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private WeatherRepository mRepository;

    HomePresenter(WeatherRepository weatherRepository) {
        mRepository = weatherRepository;
    }

    @Override
    public void getWeatherData(double longitude, double latitude) {
        mRepository.getWeatherData(new OnFetchDataListener<DataResponse>() {
            @Override
            public void onSuccess(DataResponse data) {
                mView.onGetDataResponseSuccess(data);
            }

            @Override
            public void onError(Exception e) {
                mView.onGetDataResponseError(e);
            }
        },longitude,latitude);
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }
}
