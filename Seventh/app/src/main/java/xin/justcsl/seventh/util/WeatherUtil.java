package xin.justcsl.seventh.util;

import org.json.JSONException;
import org.json.JSONObject;

import xin.justcsl.seventh.gson.Forecast;
import xin.justcsl.seventh.gson.Lives;

/**
 * 用于解析高德地图天气查询 API 返回 JSON 的工具类
 */
public class WeatherUtil
{

    //获取当前实时天气状况后转化为数据封装类
    public static Lives handleLivesWeatherResponse(String response)
    {
        try
        {
            JSONObject baseJson = new JSONObject(response);
            if (baseJson.getString("status").equals("1"))
            {
                JSONObject temp = baseJson.getJSONArray("lives").getJSONObject(0);
                return new Lives(temp);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //获取预报天气后转化为数据封装类
    public static Forecast handleForecastWeatherResponse(String response)
    {
        try
        {
            JSONObject allJson = new JSONObject(response);
            if (allJson.getString("status").equals("1"))
            {
                JSONObject temp = allJson.getJSONArray("forecasts").getJSONObject(0);
                return new Forecast(temp);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
