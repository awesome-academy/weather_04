package com.sun.weather04.screen.sevendaysnext;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.weather04.R;
import com.sun.weather04.data.model.Currently;
import com.sun.weather04.data.model.SevenDaysNext;
import com.sun.weather04.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SevenDaysNextAdapter extends RecyclerView.Adapter<SevenDaysNextAdapter.SevenDaysNextViewHolder> {

    private static final int TEMPERATURE_DIFFERENCE = 32;
    private static final double TEMPERATURE_RATIO = 1.8;

    private List<SevenDaysNext> mListSevenDaysNext;

    public SevenDaysNextAdapter(List<SevenDaysNext> listSevenDaysNext) {
        mListSevenDaysNext = listSevenDaysNext;
    }

    @Override
    public SevenDaysNextViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_seven_days_next, viewGroup, false);

        return new SevenDaysNextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SevenDaysNextViewHolder holder, int position) {
        holder.onBindData(mListSevenDaysNext.get(position));
    }

    @Override
    public int getItemCount() {
        return mListSevenDaysNext.size();
    }

    public class SevenDaysNextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtDay;
        private ImageView mIvIcon;
        private TextView mTxtRain;
        private TextView mTxtTemperature;

        public SevenDaysNextViewHolder(View itemView) {
            super(itemView);
            mTxtDay = itemView.findViewById(R.id.tv_item_day);
            mIvIcon = itemView.findViewById(R.id.iv_item_icon);
            mTxtRain = itemView.findViewById(R.id.tv_item_rain);
            mTxtTemperature = itemView.findViewById(R.id.tv_item_temperature);
        }

        public void onBindData(SevenDaysNext sevenDaysNext) {
            setTime(sevenDaysNext.getTime());
            setTemperature(sevenDaysNext.getMinTemperature(), sevenDaysNext.getMaxTemperature());
            setRain(sevenDaysNext.getPrecipProbability());
            setIcon(sevenDaysNext.getIcon());
        }

        private String dateFormat(int time) {
            Long longTime = (long) time;
            Date date = new Date(longTime * 1000L);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
            String day = simpleDateFormat.format(date);
            return day;
        }

        private void setTime(int time) {
            mTxtDay.setText(dateFormat(time));
        }

        private void setTemperature(double minTemperature, double maxTemperature) {
            double minTemperatureF = ((minTemperature - TEMPERATURE_DIFFERENCE) / TEMPERATURE_RATIO);
            int minTemperatureC = (int) minTemperatureF;
            double maxTemperatureF = ((maxTemperature - TEMPERATURE_DIFFERENCE) / TEMPERATURE_RATIO);
            int maxTemperatureC = (int) maxTemperatureF;

            mTxtTemperature.setText(minTemperatureC + Constant.DEGREE_C + Constant.HYPHEN + maxTemperatureC + Constant.DEGREE_C);
        }

        private void setRain(double rain) {
            double precip = rain*100;
            mTxtRain.setText(String.format(Constant.STRING_DISPLAY_FORMAT, precip, Constant.PERCENT));
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
    }
}
