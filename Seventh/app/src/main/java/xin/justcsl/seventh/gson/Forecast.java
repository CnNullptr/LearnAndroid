package xin.justcsl.seventh.gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Forecast
{
    private String status;
    private String cityName;
    private String adCode;
    //更新时间
    private String reportTime;
    //预报内容
    private List<Cast> casts;

    public Forecast()
    {
    }

    public Forecast(String status, String cityName, String adCode, String reportTime, List<Cast> casts)
    {
        this.status = status;
        this.cityName = cityName;
        this.adCode = adCode;
        this.reportTime = reportTime;
        this.casts = casts;
    }

    public Forecast(JSONObject json) throws JSONException
    {
        this.status = "1";
        this.cityName = json.getString("city");
        this.adCode = json.getString("adcode");
        this.reportTime = json.getString("reporttime");

        List<Cast> castsList = new ArrayList<>();
        JSONArray temp = json.getJSONArray("casts");

        //从 1 开始跳过当天天气
        for (int i = 1; i < temp.length(); i++)
        {
            Cast castTemp = new Cast(temp.getJSONObject(i));
            castsList.add(castTemp);
        }
        this.casts = castsList;
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

    public String getReportTime()
    {
        return reportTime;
    }

    public void setReportTime(String reportTime)
    {
        this.reportTime = reportTime;
    }

    public List<Cast> getCasts()
    {
        return casts;
    }

    public void setCasts(List<Cast> casts)
    {
        this.casts = casts;
    }
}
