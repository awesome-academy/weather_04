package com.sun.weather04.screen.sevendaysnext;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.screen.BasePresenter;

public class SevenDaysNextContract {

    interface View {
        void onGetDataResponseSuccess(DataResponse dataResponse);

        void onGetDataResponseError(Exception e);
    }

    interface Presenter extends BasePresenter<SevenDaysNextContract.View> {
        void getWeatherData(double longitude, double latitude);
    }
}
