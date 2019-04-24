package com.sun.weather04.screen.sevendaysnext;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.weather04.R;
import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.model.SevenDaysNext;
import com.sun.weather04.data.source.remote.WeatherRemoteDataSource;
import com.sun.weather04.screen.BaseFragment;
import com.sun.weather04.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sun.weather04.data.repository.WeatherRepository.getInstance;

public class SevenDaysNextFragment extends BaseFragment implements SevenDaysNextContract.View{

    private SevenDaysNextAdapter mAdapter;
    private RecyclerView mRecyclerViewDisplay;
    private TextView mLocation;
    private SevenDaysNextPresenter mSevenDaysNextPresenter;
    private List<SevenDaysNext> mListSevenDayNext=new ArrayList<>();
    private DataResponse mDataResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_seven_days_next, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new SevenDaysNextAdapter(mListSevenDayNext);
        mRecyclerViewDisplay.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewDisplay.setAdapter(mAdapter);
    }

    @Override
    public void initView(View view) {
        mLocation = view.findViewById(R.id.tv_seven_days_next_location);
        mRecyclerViewDisplay = view.findViewById(R.id.recycler_view_seven_days_next);
    }

    @Override
    public void initData() {
        mSevenDaysNextPresenter = new SevenDaysNextPresenter(getInstance(new WeatherRemoteDataSource()));
        mSevenDaysNextPresenter.setView(this);
        mSevenDaysNextPresenter.getWeatherData(Constant.LONGITUDE, Constant.LATITUDE);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onGetDataResponseSuccess(DataResponse dataResponse) {
        if (dataResponse == null) return;
        mDataResponse = dataResponse;
        mLocation.setText(dataResponse.getTimezone());
        mListSevenDayNext.clear();
        mListSevenDayNext.addAll(mDataResponse.getSevenDaysNext());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDataResponseError(Exception e) {

    }
}
