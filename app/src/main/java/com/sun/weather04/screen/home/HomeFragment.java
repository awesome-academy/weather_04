package com.sun.weather04.screen.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.weather04.R;
import com.sun.weather04.data.model.Currently;
import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.source.remote.WeatherRemoteDataSource;
import com.sun.weather04.screen.BaseFragment;
import com.sun.weather04.screen.history.HistoryFragment;
import com.sun.weather04.screen.today.TodayFragment;
import com.sun.weather04.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.sun.weather04.data.repository.WeatherRepository.getInstance;


public class HomeFragment extends BaseFragment implements HomeContract.View, LocationListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_PERMISSION_LOCATION = 10;
    private static final int TEMPERATURE_DIFFERENCE = 32;
    private static final double TEMPERATURE_RATIO = 1.8;
    private static final double WIND_SPEED_RATIO = 3.6;
    private HomePresenter mHomePresenter;
    private TextView mTxtLocation;
    private TextView mTxtTime;
    private TextView mTxtTemperature;
    private TextView mTxtSummary;
    private TextView mTxtWindSpeed;
    private TextView mTxtHumid;
    private TextView mTxtPrecipProbability;
    private ImageView mIvIcon;
    private double mLongitude = 0.0;
    private double mLatitude = 0.0;

    private ImageButton mIbOption;
    private Button mBtnSeeDetail;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Switch mSwitchTemperature;
    private Switch mSwitchWindSpeed;
    private TextView mTvHomeOption;
    private TextView mTvSearchOption;
    private TextView mTvHistoryOption;
    private EditText mEdSearch;

    private DataResponse mDataResponse;

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                mDrawerLayout.openDrawer(mNavigationView);
                break;
            case R.id.btn_see_detail:
                FragmentTransaction seeDetailFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                seeDetailFragmentTransaction.replace(R.id.pager_main, new TodayFragment());
                seeDetailFragmentTransaction.addToBackStack(null);
                seeDetailFragmentTransaction.commit();
                break;
            case R.id.tv_home_option:
                mDrawerLayout.closeDrawer(mNavigationView);
                break;
            case R.id.tv_search_option:
                mDrawerLayout.closeDrawer(mNavigationView);
                mEdSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEdSearch, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.tv_history_option:
                mDrawerLayout.closeDrawer(mNavigationView);
                FragmentTransaction historyFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                historyFragmentTransaction.replace(R.id.pager_main, new HistoryFragment());
                historyFragmentTransaction.addToBackStack(null);
                historyFragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mDataResponse == null) {
            Toast.makeText(getActivity(), getString(R.string.on_get_data_response_error),
                    Toast.LENGTH_LONG).show();
        } else {
            switch (buttonView.getId()) {
                case R.id.switch_temperature_option:
                    if (!isChecked) {
                        setTemperature(mDataResponse.getCurrently().getTemperature());
                        break;
                    } else {
                        int temperature = (int) mDataResponse.getCurrently().getTemperature();
                        mTxtTemperature.setText(String.format(Constant.STRING_DISPLAY_FORMAT,
                                temperature, Constant.DEGREE_F));
                        break;
                    }
                case R.id.switch_wind_speed_option:
                    if (!isChecked) {
                        setWindSpeed(mDataResponse.getCurrently().getWindSpeed());
                        break;
                    } else {
                        mTxtWindSpeed.setText(String.format(Constant.STRING_DISPLAY_FORMAT,
                                convertWindSpeed(mDataResponse.getCurrently().getWindSpeed()), Constant.M_S));
                        break;
                    }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        checkPermission();
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    @Override
    public void initView(View view) {
        mTxtLocation = view.findViewById(R.id.tv_location);
        mTxtHumid = view.findViewById(R.id.tv_humid);
        mTxtSummary = view.findViewById(R.id.tv_summary);
        mTxtTime = view.findViewById(R.id.tv_time);
        mTxtTemperature = view.findViewById(R.id.tv_temperature);
        mTxtPrecipProbability = view.findViewById(R.id.tv_rain);
        mTxtWindSpeed = view.findViewById(R.id.tv_wind_speed);
        mIvIcon = view.findViewById(R.id.iv_summary_ic);
        mIbOption = view.findViewById(R.id.btn_menu);
        mDrawerLayout = view.findViewById(R.id.layoutDrawer);
        mNavigationView = view.findViewById(R.id.nav_view);
        mBtnSeeDetail = view.findViewById(R.id.btn_see_detail);
        mSwipeRefreshLayout = view.findViewById(R.id.srl_swipe_refresh_layout_home);
        mSwitchTemperature = view.findViewById(R.id.switch_temperature_option);
        mSwitchWindSpeed = view.findViewById(R.id.switch_wind_speed_option);
        mTvHomeOption = view.findViewById(R.id.tv_home_option);
        mTvSearchOption = view.findViewById(R.id.tv_search_option);
        mTvHistoryOption = view.findViewById(R.id.tv_history_option);
        mEdSearch = view.findViewById(R.id.et_search);
    }

    @Override
    public void initData() {
        checkUpdateLocation();
        mHomePresenter = new HomePresenter(getInstance(new WeatherRemoteDataSource()));
        mHomePresenter.setView(this);
        mHomePresenter.getWeatherData(Constant.LONGITUDE, Constant.LATITUDE);
        mSwipeRefreshLayout.setRefreshing(true);
        mTvHomeOption.setOnClickListener(this);
        mTvSearchOption.setOnClickListener(this);
        mTvHistoryOption.setOnClickListener(this);
    }

    @Override
    public void onGetDataResponseSuccess(DataResponse dataResponse) {
        if (dataResponse == null) return;
        mDataResponse = dataResponse;
        setLocation(dataResponse.getTimezone());
        setSummary(dataResponse.getCurrently().getSummary());
        setWindSpeed(dataResponse.getCurrently().getWindSpeed());
        setPrecipProbability(dataResponse.getCurrently().getPrecipProbability());
        setHumid(dataResponse.getCurrently().getHumidity());
        setTemperature(dataResponse.getCurrently().getTemperature());
        setTime(dataResponse.getCurrently().getTime());
        setIcon(dataResponse.getCurrently().getIcon());

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5 * 1000 * 60,
                                1, this);
                    }
                } else {
                    getActivity().finish();
                }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLongitude = location.getLongitude();
        mLatitude = location.getLatitude();
        mHomePresenter.getWeatherData(mLongitude, mLatitude);
    }

    @Override
    public void onGetDataResponseError(Exception e) {
        Toast.makeText(getActivity(), getString(R.string.on_get_data_response_error), Toast.LENGTH_LONG).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initListener() {
        mIbOption.setOnClickListener(this);
        mBtnSeeDetail.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwitchTemperature.setOnCheckedChangeListener(this);
        mSwitchWindSpeed.setOnCheckedChangeListener(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
                return;
            }
        }
    }

    private void checkUpdateLocation() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constant.TIME_TO_UPDATE,
                    Constant.DISTANCE_TO_UPDATE, this);
        }
    }

    private String dateFormat(int time) {
        Long longTime = (long) time;
        Date date = new Date(longTime * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DAY_FORMAT);
        String day = simpleDateFormat.format(date);
        return day;
    }


    private void setLocation(String timezone) {
        mTxtLocation.setText(timezone == null ? "" : timezone);
    }

    private void setTemperature(double F) {
        double c = ((F - TEMPERATURE_DIFFERENCE) / TEMPERATURE_RATIO);
        int C = (int) c;
        mTxtTemperature.setText(String.format(Constant.STRING_DISPLAY_FORMAT, C, Constant.DEGREE_C));
    }

    private void setIcon(String icon) {
        switch (icon) {
            case Currently.CurrentlyEntry.CLEAR_DAY:
                mIvIcon.setBackgroundResource(R.drawable.ic_clear_day);
                break;
            case Currently.CurrentlyEntry.CLEAR_NIGHT:
                mIvIcon.setBackgroundResource(R.drawable.ic_clear_night);
                break;
            case Currently.CurrentlyEntry.RAIN:
                mIvIcon.setBackgroundResource(R.drawable.ic_rain);
                break;
            case Currently.CurrentlyEntry.SNOW:
                mIvIcon.setBackgroundResource(R.drawable.ic_snow);
                break;
            case Currently.CurrentlyEntry.SLEET:
                mIvIcon.setBackgroundResource(R.drawable.ic_sleet);
                break;
            case Currently.CurrentlyEntry.WIND:
                mIvIcon.setBackgroundResource(R.drawable.ic_wind);
                break;
            case Currently.CurrentlyEntry.FOG:
                mIvIcon.setBackgroundResource(R.drawable.ic_fog);
                break;
            case Currently.CurrentlyEntry.CLOUDY:
                mIvIcon.setBackgroundResource(R.drawable.ic_cloudy);
                break;
            case Currently.CurrentlyEntry.PARTLY_CLOUDY_DAY:
                mIvIcon.setBackgroundResource(R.drawable.ic_partly_cloud_day);
                break;
            case Currently.CurrentlyEntry.PARTLY_CLOUDY_NIGHT:
                mIvIcon.setBackgroundResource(R.drawable.ic_partly_cloud_night);
                break;
            default:
                break;
        }
    }

    private void setSummary(String summary) {
        mTxtSummary.setText(summary);
    }

    private void setWindSpeed(double windSpeed) {
        int tmpWindSpeed = (int) windSpeed;
        mTxtWindSpeed.setText(String.format(Constant.STRING_DISPLAY_FORMAT, tmpWindSpeed, Constant.KM_H));
    }

    private void setPrecipProbability(double precipProbability) {
        mTxtPrecipProbability.setText(String.format(Constant.STRING_DISPLAY_FORMAT, precipProbability, Constant.PERCENT));
    }

    private void setHumid(double humid) {
        mTxtHumid.setText(String.format(Constant.STRING_DISPLAY_FORMAT, humid, Constant.PERCENT));
    }

    private void setTime(int time) {
        mTxtTime.setText(dateFormat(time));
    }

    private int convertWindSpeed(double k_m) {
        double m_s = k_m / WIND_SPEED_RATIO;
        return (int) m_s;
    }
}
