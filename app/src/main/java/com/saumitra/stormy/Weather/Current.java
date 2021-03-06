package com.saumitra.stormy.Weather;

import com.saumitra.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by saumitra on 20-08-2017.
 */

public class Current {
    private String mIcon;
    private long mTime;
    private double mHumidity;
    private double mTemperature;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

//    public Current(String icon, long time, double humidity, double temperature, double precipChance, String summary) {
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

           return Forecast.getIconId(mIcon);
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
