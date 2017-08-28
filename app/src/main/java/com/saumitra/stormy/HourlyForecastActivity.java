package com.saumitra.stormy;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.saumitra.stormy.Adapters.DayAdapter;
import com.saumitra.stormy.Adapters.HourAdapter;
import com.saumitra.stormy.Weather.Hour;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.recyclerview.R.attr.layoutManager;

public class HourlyForecastActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
private Hour[] mHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        Parcelable[] parcelables=intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours= Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
        HourAdapter adapter = new HourAdapter(mHours,this);// Adapter act as bridge between data source and Recycler/List/GridView
        mRecyclerView.setAdapter(adapter);
        //setListAdapter(adapter);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);//Layout Manager tells the recycler view
        mRecyclerView.setLayoutManager(layoutManager);//how to display items, horizonytally , vertically??
        mRecyclerView.setHasFixedSize(true);

    }
}
