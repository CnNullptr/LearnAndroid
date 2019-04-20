package xin.justcsl.seventh.gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Lives
{
    private String status;
    private String cityName;
    private String adCode;
    private String weather;
    private String temperature;
    //湿度
    private String humidity;
    //风向
    private String windDirection;
    //风力
    private String windPower;
    //更新时间
    private String reportTime;

    public Lives()
    {
    }

    public Lives(String status, String cityName, String adCode, String weather, String temperature, String humidity, String windDirection, String windPower, String reportTime)
    {
        this.status = status;
        this.cityName = cityName;
        this.adCode = adCode;
        this.weather = weather;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windDirection = windDirection;
        this.windPower = windPower;
        this.reportTime = reportTime;
    }

    public Lives(JSONObject json) throws JSONException
    {
        this.status = "1";
        this.cityName = json.getString("city");
        this.adCode = json.getString("adcode");
        this.weather = json.getString("weather");
        this.temperature = json.getString("temperature");
        this.humidity = json.getString("humidity");
        this.windDirection = json.getString("winddirection");
        this.windPower = json.getString("windpower");
        this.reportTime = json.getString("reporttime");
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getAdCode()
    {
        return adCode;
    }

    public void setAdCode(String adCode)
    {
        this.adCode = adCode;
    }

    public String getWeather()
    {
        return weather;
    }

    public void setWeather(String weather)
    {
        this.weather = weather;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public void setHumidity(String humidity)
    {
        this.humidity = humidity;
    }

    public String getWindDirection()
    {
        return windDirection;
    }

    public void setWindDirection(String windDirection)
    {
        this.windDirection = windDirection;
    }

    public String getWindPower()
    {
        return windPower;
    }

    public void setWindPower(String windPower)
    {
        this.windPower = windPower;
    }

    public String getReportTime()
    {
        return reportTime;
    }

    public void setReportTime(String reportTime)
    {
        this.reportTime = reportTime;
    }
}
