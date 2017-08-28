package com.saumitra.stormy;
// Layout/UI can be linked only in main thread, so if background thread is doing some work, make it return a
//encapsulated object and store it in GLOBAL/INSTANCE VARIABLE OF SAME TYPE
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.saumitra.stormy.Weather.Current;
import com.saumitra.stormy.Weather.Day;
import com.saumitra.stormy.Weather.Forecast;
import com.saumitra.stormy.Weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Forecast mForecast;
    public static  final String DAILY_FORECAST="DAILY_FORECAST";
    public static  final String HOURLY_FORECAST="HOURLY_FORECAST";

    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;//BindView is simple, just one more line after setCoontent nd its done
    @BindView(R.id.locationLabel) TextView mlocationLabel;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);//<<See just this line and it will initialize all the views
        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getForecast();
            }
        });
        getForecast();
    }
    private void getForecast() {
        String apiKey = "8d14b6a247c9586b3aed08f159f2135d";
        double latitude = 27.564118;
        double longitude = 80.676473;
        String forecastUrl = "https://api.darksky.net/forecast/" + apiKey +
                "/" + latitude + "," + longitude;
        if (isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();// this is okHttp's asynch method of requesting, from backgrnd thread begins
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback()//every method inside will now happen in background thread, that why we used it
                    //putting the request in queue, but here req is only 1,so it will happen fast
            {
                @Override
                public void onFailure(Call call, IOException e)//Background
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException//background
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                        try {
                            String jsonData = response.body().string();
                            Log.v(TAG, jsonData);
                            if (response.isSuccessful())// as sson as you receive response parse it into json and set current weather
                            {
                                mForecast = parseForecastDetails(jsonData);//this is setting the model
                                runOnUiThread(new Runnable()// this func wil let the main thread know that background thread
                                        //is done logging , so that main thread can update UI
                                {                           //the method runOnUiThread is from ACTIVITY class
                                    @Override
                                    public void run() {
                                        updateDisplay();
                                    }
                                });

                            } else {
                                alertUserAboutError();
                            }
                        }
                        catch (IOException e) {
                            Log.e(TAG, "Exception caught: ", e);
                        }
                        catch (JSONException e) {
                            Log.e(TAG, "Exception caught: ", e);
                        }


                }
            });
        }
        else {
            Toast.makeText(this, "network is unavailable",
                    Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "Main UI code is running!");
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }
    private Current getCurrentDetails(String jsonData) throws JSONException//json object throws jsonobject exception
                                                                        //but we want to deal with where method is called
    {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");// pay attention to getJSONObject, it just like getString()

        Current current = new Current();
        /* all this stuff, we cud have done in diff class just like Story calss did wid pages,
        but u kind of hav to it in this class only, BECOZ UR RECEIVING RESPONSE HERE, WHICH IS ACTUALLY JSONDATA
        AND U NEDD JSONDATA TO CREATE JSON OBJECT, IF U WISH TO CREATE THIS IN SEPARATE CLASS
        U R GONNA HAVE TO MAKE REQ AND GET RESPONSE THERE TOO
         */
        /*why we are creatin new obj of model class and returnin it even after declaring a global variable of same type
        BECOZ this ALL is happening in<<BACKGROUND THREAD>>>,and we need in main thread,because without that
        we CAN'T LINK the velues set, TO THE LAYOUT.*/
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));//by default u will get a Unix time
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);
        Log.i(TAG,"FormatTime: "+ current.getFormattedTime());// to get formated time u need timezone string and
                                                                    // unix time in long

        return current;
    }
    private Forecast parseForecastDetails(String jsonData) throws JSONException{
        Forecast forecast=new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(setHourlyForecast(jsonData));
        forecast.setDailyForecast(setDailyForecast(jsonData));
        return forecast;

    }

    private Day[] setDailyForecast(String jsonData)throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily=forecast.getJSONObject("daily");
        JSONArray data=daily.getJSONArray("data");
        Day[] days=new Day[data.length()];// we are just defining the day length, not initializing constituent objects like
        for(int i=0;i<data.length();i++)//like days[0],days[1]..... etc
        {
            JSONObject jsonDaily = data.getJSONObject(i);
            Day day=new Day();
            day.setSummary(jsonDaily.getString("summary"));//if u want to use days[i].setSummary.... then u have initialize
            day.setIcon(jsonDaily.getString("icon"));// days[i]=new days[i];
            day.setTimeZone(timezone);
            day.setTemperatureMax(jsonDaily.getDouble("temperatureMax"));
            day.setTime(jsonDaily.getLong("time"));
            days[i]=day;
        }
        return days;

    }
    private Hour[] setHourlyForecast(String jsonData)throws JSONException{

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly=forecast.getJSONObject("hourly");
        JSONArray data=hourly.getJSONArray("data");
        Hour[] hours=new Hour[data.length()];
        for(int i=0;i<data.length();i++){
            Hour hour=new Hour();
            JSONObject jsonHour = data.getJSONObject(i);
            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTimeZone(timezone);
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hours[i]=hour;
        }
        return hours;

    }
    private void updateDisplay() {
        Current current=mForecast.getCurrent();
        mTemperatureLabel.setText(current.getTemperature()+"");// this is atrick to covert double/into to string
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        mHumidityValue.setText(current.getHumidity()+"");
        mPrecipValue.setText(current.getPrecipChance()+"");
        mSummaryLabel.setText(current.getSummary());
        mIconImageView.setImageResource(current.getIconId());
        mlocationLabel.setText(current.getTimeZone());
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
    @OnClick (R.id.dailyButton) // with butterknofe u dont need to set button variable
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this,DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST,mForecast.getDailyForecast());
        startActivity(intent);
    }
    @OnClick (R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this,HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST,mForecast.getHourlyForecast());
        startActivity(intent);
    }

}
