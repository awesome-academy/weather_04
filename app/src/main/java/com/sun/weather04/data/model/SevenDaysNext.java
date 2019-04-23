package com.sun.weather04.data.model;

public class SevenDaysNext {

    private int mTime;
    private String mSummary;
    private String mIcon;
    private double mPrecipProbability;
    private double mMinTemperature;
    private double mMaxTemperature;
    private double mHumidity;
    private double mWindSpeed;
    private double mCloudCover;
    private double mOzone;

    public SevenDaysNext(SevenDaysNext.SevenDaysNextBuilder dailyBuilder) {
        mTime = dailyBuilder.mTime;
        mSummary = dailyBuilder.mSummary;
        mIcon = dailyBuilder.mIcon;
        mPrecipProbability = dailyBuilder.mPrecipProbability;
        mMinTemperature = dailyBuilder.mMinTemperature;
        mMaxTemperature = dailyBuilder.mMaxTemperature;
        mHumidity = dailyBuilder.mHumidity;
        mWindSpeed = dailyBuilder.mWindSpeed;
        mCloudCover = dailyBuilder.mCloudCover;
        mOzone = dailyBuilder.mOzone;
    }

    public SevenDaysNext() {
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

    public double getMinTemperature() {
        return mMinTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        mMinTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return mMaxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        mMaxTemperature = maxTemperature;
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

    public static class SevenDaysNextBuilder {
        private int mTime;
        private String mSummary;
        private String mIcon;
        private double mMinTemperature;
        private double mMaxTemperature;
        private double mPrecipProbability;
        private double mHumidity;
        private double mWindSpeed;
        private double mCloudCover;
        private double mOzone;

        public SevenDaysNextBuilder() {
        }

        public SevenDaysNext.SevenDaysNextBuilder setTime(int time) {
            mTime = time;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setIcon(String icon) {
            mIcon = icon;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setMinTemperature(double minTemperature) {
            mMinTemperature = minTemperature;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setMaxTemperature(double maxTemperature) {
            mMaxTemperature = maxTemperature;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setSummary(String summary) {
            mSummary = summary;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setPrecipProbality(double precipProbality) {
            mPrecipProbability = precipProbality;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setHumidity(double humidity) {
            mHumidity = humidity;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setWindSpeed(double windSpeed) {
            mWindSpeed = windSpeed;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setCloudCover(double cloudCover) {
            mCloudCover = cloudCover;
            return this;
        }

        public SevenDaysNext.SevenDaysNextBuilder setOzone(double ozone) {
            mOzone = ozone;
            return this;
        }

        public SevenDaysNext build() {
            return new SevenDaysNext(this);
        }
    }
}
