package com.sun.weather04.screen.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sun.weather04.R;
import com.sun.weather04.screen.BaseActivity;
import com.sun.weather04.screen.home.HomeFragment;
import com.sun.weather04.screen.sevendaysnext.SevenDaysNextFragment;
import com.sun.weather04.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements LocationListener {

    private static final int REQUEST_PERMISSION_LOCATION = 10;
    private ViewPager mImageViewPager;
    private TabLayout mTabLayout;
    private MainPagerAdapter mainPagerAdapter;
    private List<Fragment> mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();
        initData();
        initListener();

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mListFragment);
        mImageViewPager.setAdapter(mainPagerAdapter);
        mTabLayout.setupWithViewPager(mImageViewPager, true);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
                return;
            }
        }
    }

    @Override
    public void initView() {
        mImageViewPager = findViewById(R.id.pager_main);
        mTabLayout = findViewById(R.id.tab_dots_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constant.TIME_TO_UPDATE, Constant.DISTANCE_TO_UPDATE, this);
                    }
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void initData() {
        mListFragment = new ArrayList<>();
        mListFragment.add(new HomeFragment());
        mListFragment.add(new SevenDaysNextFragment());
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constant.TIME_TO_UPDATE, Constant.DISTANCE_TO_UPDATE, this);
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onLocationChanged(Location location) {
        Constant.LONGITUDE = location.getLongitude();
        Constant.LATITUDE = location.getLatitude();
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
}
