package xin.justcsl.seventh.gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Cast
{
    private String date;
    private String week;

    private String dayWeather;
    private String nightWeather;
    private String dayTemp;
    private String nightTemp;

    public Cast()
    {
    }

    public Cast(String date, String week, String dayWeather, String nightWeather, String dayTemp, String nightTemp)
    {
        this.date = date;
        this.week = week;
        this.dayWeather = dayWeather;
        this.nightWeather = nightWeather;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
    }

    public Cast(JSONObject json) throws JSONException
    {
        this.date = json.getString("date");
        this.week = json.getString("week");
        this.dayWeather = json.getString("dayweather");
        this.nightWeather = json.getString("nightweather");
        this.dayTemp = json.getString("daytemp");
        this.nightTemp = json.getString("nighttemp");
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getWeek()
    {
        return week;
    }

    public void setWeek(String week)
    {
        this.week = week;
    }

    public String getDayWeather()
    {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather)
    {
        this.dayWeather = dayWeather;
    }

    public String getNightWeather()
    {
        return nightWeather;
    }

    public void setNightWeather(String nightWeather)
    {
        this.nightWeather = nightWeather;
    }

    public String getDayTemp()
    {
        return dayTemp;
    }

    public void setDayTemp(String dayTemp)
    {
        this.dayTemp = dayTemp;
    }

    public String getNightTemp()
    {
        return nightTemp;
    }

    public void setNightTemp(String nightTemp)
    {
        this.nightTemp = nightTemp;
    }
}