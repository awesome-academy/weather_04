package com.sun.weather04.utils;

import com.sun.weather04.BuildConfig;

public class Constant {
    private static final String URL_BASE = "https://api.darksky.net/";
    private static final String FORECAST = "forecast/";
    private static final String KEY = BuildConfig.API_KEY;
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String URL_CURRENT_WEATHER = URL_BASE + FORECAST + KEY;
    public static final String KM_H = "km/h";
    public static final String M_S = "m/s";
    public static final String PERCENT = "%";
    public static final String DEGREE_C = "ºC";
    public static final String DEGREE_F = "ºF";
    public static final String ON_GET_RESPONE_ERROR = "onGetDataResponseError";
    public static final String SIMPLE_DAY_FORMAT= "HH:mm - EEEE, dd/MM/yyyy";
    public static final String SIMPLE_DATE_FORMAT= "EEEE";
    public static final String STRING_DISPLAY_FORMAT= "%s%s";
    public static final int TIME_TO_UPDATE= 5*1000*60;
    public static final int DISTANCE_TO_UPDATE= 1;
    public static double LONGITUDE = 0 ;
    public static double LATITUDE = 0 ;
}
