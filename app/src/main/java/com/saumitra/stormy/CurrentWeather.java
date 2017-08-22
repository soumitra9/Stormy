package com.saumitra.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by saumitra on 20-08-2017.
 */

public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mHumidity;
    private double mTemperature;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

//    public CurrentWeather(String icon, long time, double humidity, double temperature, double precipChance, String summary) {
//        mIcon = icon;
//        mTime = time;
//        mHumidity = humidity;
//        mTemperature = temperature;
//        mPrecipChance = precipChance;
//        mSummary = summary;
//    }


    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }
    public String getFormattedTime() //unix time is no. of sec since jan 1970
    {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");//<space> a is for AM or PM
        //now we need correct timezone to get time, and timezone can vary
        //but we timezone was present in Json respnse of DarkSky, so we need to access it
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));//getTimeZone() gives string time zone
        Date dateTime=new Date(getTime()*1000);//coz it accepts value in milliseconds, getTime() is unix time
        String timeString=formatter.format(dateTime);// formatter.fprmate needs a Date object
        return timeString;//now check in main activity by log just to make sure

    }
    public int getIconId() {
        int iconId=R.drawable.clear_day;
        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
           return iconId;
    }



    public void setTime(long time) {
        mTime = time;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getTemperature() //initially we made it double but it disturbed the layout, we had not expected temp to be double
    {
        return (int)Math.round(mTemperature);//mTemperatue in setter is double so we rounded it & Math.round() returns long
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getPrecipChance() {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

}
