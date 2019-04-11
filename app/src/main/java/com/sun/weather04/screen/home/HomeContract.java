package com.sun.weather04.screen.home;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.screen.BasePresenter;

public class HomeContract {

    interface View {
        void onGetDataResponseSuccess(DataResponse dataResponse);

        void onGetDataResponseError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {
        void getWeatherData(double longitude, double latitude);
    }
}
