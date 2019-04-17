package com.sun.weather04.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.weather04.R;
import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.repository.WeatherRepository;
import com.sun.weather04.data.source.remote.WeatherRemoteDataSource;
import com.sun.weather04.screen.BaseFragment;
import com.sun.weather04.utils.Constant;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomePresenter mHomePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initData() {
        mHomePresenter = new HomePresenter(WeatherRepository.getInstance(new WeatherRemoteDataSource()));
        mHomePresenter.setView(this);
        mHomePresenter.getWeatherData(Constant.LONGITUDE, Constant.LATITUDE);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onGetDataResponseSuccess(DataResponse dataResponse) {
    }

    @Override
    public void onGetDataResponseError(Exception e) {
    }
}
