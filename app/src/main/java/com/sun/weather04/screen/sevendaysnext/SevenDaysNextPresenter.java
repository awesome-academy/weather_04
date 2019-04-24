package com.sun.weather04.screen.sevendaysnext;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.repository.WeatherRepository;
import com.sun.weather04.data.source.remote.OnFetchDataListener;

public class SevenDaysNextPresenter implements SevenDaysNextContract.Presenter {

    private SevenDaysNextContract.View mView;
    private WeatherRepository mRepository;

    SevenDaysNextPresenter(WeatherRepository weatherRepository) {
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
        }, longitude, latitude);
    }

    @Override
    public void setView(SevenDaysNextContract.View view) {
        mView = view;
    }
}
