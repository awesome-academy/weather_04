package com.sun.weather04.utils;

import com.sun.weather04.BuildConfig;

public class Constant {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private static final String URL_BASE = "https://api.darksky.net/";
    private static final String FORECAST = "forecast/";
    private static final String KEY = BuildConfig.API_KEY;
    public static final String URL_CURRENT_WEATHER = URL_BASE + FORECAST + KEY;
    public static double LONGITUDE = 0 ;
    public static double LATITUDE = 0 ;
}
