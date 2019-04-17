package com.sun.weather04.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DataResponse {

    private double mLatitude;
    private double mLongitude;
    private String mTimezone;
    private Currently mCurrently;

    public DataResponse(DataResponseBuilder dataResponseBuilder) {
        mLatitude = dataResponseBuilder.mLatitude;
        mLongitude = dataResponseBuilder.mLongitude;
        mTimezone = dataResponseBuilder.mTimezone;
        mCurrently = dataResponseBuilder.mCurrently;
    }

    public DataResponse() {
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public Currently getCurrently() {
        return mCurrently;
    }

    public void setCurrently(Currently currently) {
        mCurrently = currently;
    }

    public static class DataResponseBuilder {
        private double mLatitude;
        private double mLongitude;
        private String mTimezone;
        private Currently mCurrently;

        public DataResponseBuilder(double latitude, double longitude, String timezone, Currently currently) {
            mLatitude = latitude;
            mLongitude = longitude;
            mTimezone = timezone;
            mCurrently = currently;
        }

        public DataResponseBuilder() {
        }

        public DataResponseBuilder latitude(double latitude) {
            mLatitude = latitude;
            return this;
        }

        public DataResponseBuilder longitude(double longitude) {
            mLongitude = longitude;
            return this;
        }

        public DataResponseBuilder timezone(String timezone) {
            mTimezone = timezone;
            return this;
        }


        public DataResponse build() {
            return new DataResponse(this);
        }

        public DataResponseBuilder currently(JSONObject jsonObject) throws JSONException {

            String tmpTemperature = jsonObject.getString(DataResponseEntry.TEMPERATURE);
            double temperature = Double.parseDouble(tmpTemperature);
            String time = jsonObject.getString(DataResponseEntry.TIME);
            int tmpTime = Integer.parseInt(time);
            String summary = jsonObject.getString(DataResponseEntry.SUMMARY);
            String tmpWindSpeed = jsonObject.getString(DataResponseEntry.WIND_SPEED);
            double windSpeed = Double.parseDouble(tmpWindSpeed);
            String precipProbability = jsonObject.getString(DataResponseEntry.PRECIP_PROBABILITY);
            double tmpPrecipProbability = Double.parseDouble(precipProbability);
            String tmpHumid = jsonObject.getString(DataResponseEntry.HUMID);
            double humid = Double.parseDouble(tmpHumid);
            String icon = jsonObject.getString(DataResponseEntry.ICON);

            mCurrently = new Currently.CurrentlyBuilder()
                    .setSummary(summary)
                    .setTemperature(temperature)
                    .setTime(tmpTime)
                    .setWindSpeed(windSpeed)
                    .setPrecipProbality(tmpPrecipProbability)
                    .setHumidity(humid)
                    .setIcon(icon)
                    .build();
            return this;
        }
    }

    public final class DataResponseEntry {
        private static final String SUMMARY = "summary";
        private static final String ICON = "icon";
        private static final String TIME = "time";
        private static final String HUMID = "humidity";
        private static final String PRECIP_PROBABILITY = "precipProbability";
        private static final String WIND_SPEED = "windSpeed";
        private static final String TEMPERATURE = "temperature";
    }
}
