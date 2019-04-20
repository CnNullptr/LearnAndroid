package xin.justcsl.seventh.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xin.justcsl.seventh.application.MyApplication;
import xin.justcsl.seventh.gson.Forecast;
import xin.justcsl.seventh.gson.Lives;
import xin.justcsl.seventh.util.HttpUtil;
import xin.justcsl.seventh.util.WeatherUtil;

public class AutoUpdateService extends Service
{
    public AutoUpdateService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        updateWeather();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 28800000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;

        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        String livesString = prefs.getString("lives", null);
        if (livesString != null)
        {
            Lives lives = WeatherUtil.handleLivesWeatherResponse(livesString);

            String adCode = lives.getAdCode();

            String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=79d1b7cebba1af2273156f9645cc44e2&extensions=base&city=" + adCode;

            HttpUtil.sendOkhttpRequest(url, new Callback()
            {
                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String responseText = response.body().string();
                    Lives newLives = WeatherUtil.handleLivesWeatherResponse(responseText);

                    if (newLives != null && newLives.getStatus().equals("1"))
                    {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).edit();
                        editor.putString("lives", responseText);
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e)
                {
                    e.printStackTrace();
                }
            });
        }

        String forecastString = prefs.getString("forecast", null);
        if (livesString != null)
        {
            Lives lives = WeatherUtil.handleLivesWeatherResponse(livesString);

            String adCode = lives.getAdCode();

            String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=79d1b7cebba1af2273156f9645cc44e2&extensions=all&city=" + adCode;

            HttpUtil.sendOkhttpRequest(url, new Callback()
            {
                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String responseText = response.body().string();
                    Forecast newForecast = WeatherUtil.handleForecastWeatherResponse(responseText);

                    if (newForecast != null && newForecast.getStatus().equals("1"))
                    {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).edit();
                        editor.putString("forecast", responseText);
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e)
                {
                    e.printStackTrace();
                }
            });
        }
    }
}
