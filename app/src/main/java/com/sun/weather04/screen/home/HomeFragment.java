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
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.weather04.R;
import com.sun.weather04.data.model.Currently;
import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.source.remote.WeatherRemoteDataSource;
import com.sun.weather04.screen.BaseFragment;
import com.sun.weather04.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.sun.weather04.data.repository.WeatherRepository.getInstance;


public class HomeFragment extends BaseFragment implements HomeContract.View, LocationListener {

    private static final int REQUEST_PERMISSION_LOCATION = 10;
    private static final int TEMPERATURE_DIFFERENCE = 32;
    private static final double TEMPERATURE_RATIO = 1.8;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        checkPermission();
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
        mTxtLocation = view.findViewById(R.id.tv_location);
        mTxtHumid = view.findViewById(R.id.tv_humid);
        mTxtSummary = view.findViewById(R.id.tv_summary);
        mTxtTime = view.findViewById(R.id.tv_time);
        mTxtTemperature = view.findViewById(R.id.tv_temperature);
        mTxtPrecipProbability = view.findViewById(R.id.tv_rain);
        mTxtWindSpeed = view.findViewById(R.id.tv_wind_speed);
        mIvIcon = view.findViewById(R.id.iv_summary_ic);
    }

    @Override
    public void initData() {
        checkUpdateLocation();
        mHomePresenter = new HomePresenter(getInstance(new WeatherRemoteDataSource()));
        mHomePresenter.setView(this);
        mHomePresenter.getWeatherData(mLongitude, mLatitude);
    }

    @Override
    public void onGetDataResponseSuccess(DataResponse dataResponse) {
        if (dataResponse == null) return;
        setLocation(dataResponse.getTimezone());
        setSummary(dataResponse.getCurrently().getSummary());
        setWindSpeed(dataResponse.getCurrently().getWindSpeed());
        setPrecipProbability(dataResponse.getCurrently().getPrecipProbability());
        setHumid(dataResponse.getCurrently().getHumidity());
        setTemperature(dataResponse.getCurrently().getTemperature());
        setTime(dataResponse.getCurrently().getTime());
        setIcon(dataResponse.getCurrently().getIcon());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
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
    }


    @Override
    public void onLocationChanged(Location location) {
        mLongitude = location.getLongitude();
        mLatitude = location.getLatitude();
        mHomePresenter.getWeatherData(mLongitude, mLatitude);
    }

    @Override
    public void onGetDataResponseError(Exception e) {
        Log.e(Constant.ON_GET_RESPONE_ERROR, e.toString());
    }

    @Override
    public void initListener() {
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
        mTxtWindSpeed.setText(String.format(Constant.STRING_DISPLAY_FORMAT, windSpeed, Constant.KM_H));
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
}
