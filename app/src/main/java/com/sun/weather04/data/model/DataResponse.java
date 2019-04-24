package com.sun.weather04.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataResponse {

    private double mLatitude;
    private double mLongitude;
    private String mTimezone;
    private Currently mCurrently;
    private List<SevenDaysNext> mSevenDaysNext;

    public DataResponse(DataResponseBuilder dataResponseBuilder) {
        mLatitude = dataResponseBuilder.mLatitude;
        mLongitude = dataResponseBuilder.mLongitude;
        mTimezone = dataResponseBuilder.mTimezone;
        mCurrently = dataResponseBuilder.mCurrently;
        mSevenDaysNext = dataResponseBuilder.mSevenDaysNext;
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

    public List<SevenDaysNext> getSevenDaysNext() {
        return mSevenDaysNext;
    }

    public void setSevenDaysNext(List<SevenDaysNext> sevenDaysNext) {
        mSevenDaysNext = sevenDaysNext;
    }

    public static class DataResponseBuilder {
        private double mLatitude;
        private double mLongitude;
        private String mTimezone;
        private Currently mCurrently;
        private List<SevenDaysNext> mSevenDaysNext;

        public DataResponseBuilder(double latitude, double longitude, String timezone,
                                   Currently currently, List<SevenDaysNext> sevenDaysNext) {
            mLatitude = latitude;
            mLongitude = longitude;
            mTimezone = timezone;
            mCurrently = currently;
            mSevenDaysNext = sevenDaysNext;
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

        public List<SevenDaysNext> sevenDaysNext(JSONObject jsonObject) throws JSONException {
            JSONArray jsonArray = jsonObject.getJSONArray(DataResponseEntry.DAILY);
            List<SevenDaysNext> sevenDaysNextList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                SevenDaysNext sevenDaysNextObject;
                jsonObject = (JSONObject) jsonArray.get(i);
                String summary = jsonObject.getString(DataResponseEntry.SUMMARY);
                String icon = jsonObject.getString(DataResponseEntry.ICON);

                String tmpMinTemperature = jsonObject.getString(DataResponseEntry.MIN_TEMPERATURE);
                double minTemperature = Double.parseDouble(tmpMinTemperature);
                String tmpMaxTemperature = jsonObject.getString(DataResponseEntry.MAX_TEMPERATURE);
                double maxTemperature = Double.parseDouble(tmpMaxTemperature);

                String tmpTime = jsonObject.getString(DataResponseEntry.TIME);
                int time = Integer.parseInt(tmpTime);

                String tmpWindSpeed = jsonObject.getString(DataResponseEntry.WIND_SPEED);
                double windSpeed = Double.parseDouble(tmpWindSpeed);

                String tmpPrecipProbability = jsonObject.getString(DataResponseEntry.PRECIP_PROBABILITY);
                double precipProbability = Double.parseDouble(tmpPrecipProbability);

                String tmpHumid = jsonObject.getString(DataResponseEntry.HUMID);
                double humid = Double.parseDouble(tmpHumid);

                String tmpOzone = jsonObject.getString(DataResponseEntry.OZONE);
                double ozone = Double.parseDouble(tmpOzone);

                String tmpCloudCover = jsonObject.getString(DataResponseEntry.CLOUD_COVER);
                double cloudCover = Double.parseDouble(tmpCloudCover);

                sevenDaysNextObject = new SevenDaysNext.SevenDaysNextBuilder()
                        .setSummary(summary)
                        .setIcon(icon)
                        .setMinTemperature(minTemperature)
                        .setMaxTemperature(maxTemperature)
                        .setTime(time)
                        .setWindSpeed(windSpeed)
                        .setPrecipProbality(precipProbability)
                        .setHumidity(humid)
                        .setOzone(ozone)
                        .setCloudCover(cloudCover)
                        .build();

                sevenDaysNextList.add(sevenDaysNextObject);
            }
            return sevenDaysNextList;
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
        private static final String DAILY = "data";
        private static final String MIN_TEMPERATURE = "temperatureMin";
        private static final String MAX_TEMPERATURE = "temperatureMax";
        private static final String OZONE = "ozone";
        private static final String CLOUD_COVER = "cloudCover";
    }
}
