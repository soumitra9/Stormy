package com.saumitra.stormy.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by saumitra on 23-08-2017.
 */

public class Day implements Parcelable{
    private long mTime;
    private  String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private String mTimeZone;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperatureMax() {

       return (int)Math.round(mTemperatureMax);//mTemperatue in setter is double so we rounded it & Math.round() returns long

    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }
    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }
    public String getDaysOfWeek(){
        SimpleDateFormat formatter= new SimpleDateFormat("EEEE");// thiis EEEE format gives us day according to our timezone
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime=new Date(getTime()*1000);
        return formatter.format(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i)  // here we need to write data of the object to Parcel parameter
    {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperatureMax);
        dest.writeString(mTimeZone);
        dest.writeString(mIcon);
    }
    //now we nedd to wite method to UNPARCELE it, which is actually a constructor that take Parcel parameter
    private Day(Parcel in)//here we want read data <<<"""EXACTLY IN SAME ORDER""">>>, how we wrote it
    {
        mTime=in.readLong();
        mSummary=in.readString();
        mTemperatureMax=in.readDouble();
        mTimeZone=in.readString();
        mIcon=in.readString();
    }
    public Day(){}//empty constructor
    public static final Creator<Day> CREATOR=new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);// here we are suppose to return the constructor
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
