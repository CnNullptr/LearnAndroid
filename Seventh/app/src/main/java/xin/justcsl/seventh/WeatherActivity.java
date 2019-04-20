package xin.justcsl.seventh;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xin.justcsl.seventh.application.MyApplication;
import xin.justcsl.seventh.gson.Cast;
import xin.justcsl.seventh.gson.Forecast;
import xin.justcsl.seventh.gson.Lives;
import xin.justcsl.seventh.util.HttpUtil;
import xin.justcsl.seventh.util.WeatherUtil;

public class WeatherActivity extends AppCompatActivity
{

    private ScrollView weatherLayout;

    //实时天气相关控件
    private TextView titleCity;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView humidityWindText;
    private TextView reportTimeText;

    //天气预报相关控件
    private LinearLayout forecastLayout;

    //选中城市的 ID，根据 WeatherActivity 打开的方式不同有不同的值
    public String selectedAdCode;

    //背景图片
    private ImageView imgBackground;

    //下拉刷新天气
    public SwipeRefreshLayout swipeRefreshLayout;

    //侧边栏实现
    public DrawerLayout drawerLayout;
    private Button selectCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        selectedAdCode = getIntent().getStringExtra("adCode");

        if(selectedAdCode == null)
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

            selectedAdCode = prefs.getString("defaultCity", null);
            if(selectedAdCode != null)
            {
                requestWeather();
            }
        }

        //我选择的最小支持 SDK 是 26，无须再检测是否支持沉浸式状态栏功能
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_weather);

        init();

        //数据缓存
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String livesString = prefs.getString("lives", null);
        if (livesString != null)
        {
            //存在有效缓存直接解析天气数据
            Lives lives = WeatherUtil.handleLivesWeatherResponse(livesString);
            showLivesWeatherInfo(lives);
        } else
        {
            // 无缓存时去服务器查询天气
            weatherLayout.setVisibility(View.INVISIBLE);
            requestLives();
        }
        String forecastString = prefs.getString("forecast", null);
        if (forecastString != null)
        {
            // 有缓存时直接解析天气数据
            Forecast forecast = WeatherUtil.handleForecastWeatherResponse(forecastString);
            showForecastWeatherInfo(forecast);
        } else
        {
            // 无缓存时去服务器查询天气
            weatherLayout.setVisibility(View.INVISIBLE);
            requestForecast();
        }
    }

    //初始化各控件
    private void init()
    {
        //父布局
        weatherLayout = findViewById(R.id.weather_layout);

        //实时天气相关
        titleCity = findViewById(R.id.title_city);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        humidityWindText = findViewById(R.id.humidity_wind_text);
        reportTimeText = findViewById(R.id.reportTime_text);

        //天气预报相关
        forecastLayout = findViewById(R.id.forecast_layout);

        imgBackground = findViewById(R.id.img_kackground);

        //todo 根据天气更换图片
        Glide.with(this).load(R.drawable.test).into(imgBackground);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getColor(R.color.colorPrimary));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                requestLives();
                requestForecast();
            }
        });

        //侧边栏实现
        drawerLayout = findViewById(R.id.drawer_layout);
        selectCityButton = findViewById(R.id.select_city_button);

        selectCityButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void requestLives()
    {
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=79d1b7cebba1af2273156f9645cc44e2&extensions=base&city=" + selectedAdCode;

        HttpUtil.sendOkhttpRequest(url, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String responseText = response.body().string();
                final Lives lives = WeatherUtil.handleLivesWeatherResponse(responseText);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (lives != null)
                        {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("lives", responseText);
                            editor.apply();
                            selectedAdCode = lives.getAdCode();
                            showLivesWeatherInfo(lives);
                        } else
                        {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void requestForecast()
    {
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=79d1b7cebba1af2273156f9645cc44e2&extensions=all&city=" + selectedAdCode;

        HttpUtil.sendOkhttpRequest(url, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String responseText = response.body().string();
                final Forecast forecast = WeatherUtil.handleForecastWeatherResponse(responseText);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (forecast != null)
                        {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("forecast", responseText);
                            editor.apply();
                            selectedAdCode = forecast.getAdCode();
                            showForecastWeatherInfo(forecast);
                        } else
                        {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void requestWeather()
    {
        requestLives();
        requestForecast();
    }

    private void showLivesWeatherInfo(Lives lives)
    {
        titleCity.setText(lives.getCityName());
        degreeText.setText(lives.getTemperature() + "℃");
        weatherInfoText.setText(lives.getWeather());
        humidityWindText.setText("相对湿度 " + lives.getHumidity() + " " + lives.getWindDirection() + "风 " + lives.getWindPower() + '级');
        reportTimeText.setText(lives.getReportTime());
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void showForecastWeatherInfo(Forecast forecast)
    {
        forecastLayout.removeAllViews();

        //casts
        List<Cast> casts = forecast.getCasts();

        for (Cast one : casts)
        {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(one.getDate());
            infoText.setText(one.getDayWeather());
            maxText.setText(one.getDayTemp() + '℃');
            minText.setText(one.getNightTemp() + '℃');
            forecastLayout.addView(view);
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
