package com.saumitra.stormy;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.saumitra.stormy.Adapters.DayAdapter;
import com.saumitra.stormy.Weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity// the list activity is no different it is just to make ur device backword
{
    private Day[] mDays;                         //compatible, by adding support libraries

    // to use listActivity remember the SPECIAL ID's putted in the Layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);// the reason we created key as extra because, we can access it directly
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class); // we will get an error in parecable, coz we need to make data of Day object
        // parcelable.it is like WRAPPING up data, SHIPPING it and unwrapping it.
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String daysOfWeek=mDays[position].getDaysOfWeek();
        String conditions=mDays[position].getSummary();
        String highTemp=mDays[position].getTemperatureMax()+"";
        String message=String.format("On %s the high will be %s and it will be %s",daysOfWeek,conditions,highTemp);

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }
}