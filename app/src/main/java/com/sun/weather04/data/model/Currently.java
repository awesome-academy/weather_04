package com.sun.weather04.data.model;

public class Currently {

    private int mTime;
    private String mSummary;
    private String mIcon;
    private double mPrecipProbability;
    private double mTemperature;
    private double mApparentTemperature;
    private double mHumidity;
    private double mWindSpeed;
    private double mCloudCover;
    private double mOzone;

    public Currently(CurrentlyBuilder currentlyBuilder) {
        mTime = currentlyBuilder.mTime;
        mSummary = currentlyBuilder.mSummary;
        mIcon = currentlyBuilder.mIcon;
        mPrecipProbability = currentlyBuilder.mPrecipProbability;
        mTemperature = currentlyBuilder.mTemperature;
        mApparentTemperature = currentlyBuilder.mApparentTemperature;
        mHumidity = currentlyBuilder.mHumidity;
        mWindSpeed = currentlyBuilder.mWindSpeed;
        mCloudCover = currentlyBuilder.mCloudCover;
        mOzone = currentlyBuilder.mOzone;
    }

    public Currently() {
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public double getPrecipProbability() {
        return mPrecipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        mPrecipProbability = precipProbability;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getApparentTemperature() {
        return mApparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        mApparentTemperature = apparentTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public double getCloudCover() {
        return mCloudCover;
    }

    public void setCloudCover(double cloudCover) {
        mCloudCover = cloudCover;
    }

    public double getOzone() {
        return mOzone;
    }

    public void setOzone(double ozone) {
        mOzone = ozone;
    }

    public static class CurrentlyBuilder {
        private int mTime;
        private String mSummary;
        private String mIcon;
        private double mTemperature;
        private double mPrecipProbability;
        private double mApparentTemperature;
        private double mHumidity;
        private double mWindSpeed;
        private double mCloudCover;
        private double mOzone;

        public CurrentlyBuilder() {
        }

        public CurrentlyBuilder setTime(int time) {
            mTime = time;
            return this;
        }

        public CurrentlyBuilder setIcon(String icon) {
            mIcon = icon;
            return this;
        }

        public CurrentlyBuilder setTemperature(double temperature) {
            mTemperature = temperature;
            return this;
        }

        public CurrentlyBuilder setApparentTemperature(double apparentTemperature) {
            mApparentTemperature = apparentTemperature;
            return this;
        }

        public CurrentlyBuilder setSummary(String summary) {
            mSummary = summary;
            return this;
        }

        public CurrentlyBuilder setPrecipProbality(double precipProbality) {
            mPrecipProbability = precipProbality;
            return this;
        }

        public CurrentlyBuilder setHumidity(double humidity) {
            mHumidity = humidity;
            return this;
        }

        public CurrentlyBuilder setWindSpeed(double windSpeed) {
            mWindSpeed = windSpeed;
            return this;
        }

        public CurrentlyBuilder setCloudCover(int cloudCover) {
            mCloudCover = cloudCover;
            return this;
        }

        public CurrentlyBuilder setOzone(int ozone) {
            mOzone = ozone;
            return this;
        }

        public Currently build() {
            return new Currently(this);
        }
    }


    public final class CurrentlyEntry {
        private static final String CLEAR_DAY = "clear-day";
        private static final String CLEAR_NIGHT = "clear-night";
        private static final String RAIN = "rain";
        private static final String SNOW = "snow";
        private static final String SLEET = "sleet";
        private static final String WIND = "wind";
        private static final String FOG = "fog";
        private static final String CLOUDY = "cloudy";
        private static final String PARTLY_CLOUDY_DAY = "partly-cloudy-day";
        private static final String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";
    }
}
