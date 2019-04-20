package xin.justcsl.seventh.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import xin.justcsl.seventh.db.City;
import xin.justcsl.seventh.db.County;
import xin.justcsl.seventh.db.Province;

/**
 * 用于解析高德地图行政区查询 API 返回 JSON 的工具类
 */
public class DistrictUtil
{
    //返回所有省级行政区并存入数据库，成功返回 true
    public static boolean handleProvinceResponse(String response)
    {
        if (responAvaiable(response))
        {
            try
            {
                JSONObject responseInfo = new JSONObject(response);
                JSONArray allProvinces = responseInfo.getJSONArray("districts").getJSONObject(0).getJSONArray("districts");
                //使用 List 集合对返回的数据进行排序
                ArrayList<Province> provinces = new ArrayList<>();
                for (int i = 0; i < allProvinces.length(); i++)
                {
                    JSONObject oneProvince = allProvinces.getJSONObject(i);

                    Province temp = new Province();
                    temp.setProvinceName(oneProvince.getString("name"));
                    temp.setProvinceCode(oneProvince.getString("adcode"));

                    provinces.add(temp);
                }
                //sort 是快排实现
                Collections.sort(provinces);
                for (Province one : provinces)
                {
                    one.save();
                }
                return true;
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //保存一个省级行政区下属的所有市级行政区
    public static boolean handleCityResponse(String response, Integer provinceId)
    {
        if (responAvaiable(response))
        {
            try
            {
                JSONObject responseInfo = new JSONObject(response);
                JSONArray allCities = responseInfo.getJSONArray("districts").getJSONObject(0).getJSONArray("districts");
                ArrayList<City> cities = new ArrayList<>();
                for (int i = 0; i < allCities.length(); i++)
                {
                    JSONObject oneCity = allCities.getJSONObject(i);
                    City temp = new City();
                    temp.setCityName(oneCity.getString("name"));
                    temp.setCityCode(oneCity.getString("adcode"));
                    temp.setProvinceId(provinceId);
                    cities.add(temp);
                }
                Collections.sort(cities);
                for (City one : cities)
                {
                    one.save();
                }
                return true;

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //保存一个市级行政区下属的所有县级行政区
    public static boolean handleCountyResponse(String response, Integer cityId)
    {
        if (responAvaiable(response))
        {
            try
            {
                JSONObject responseInfo = new JSONObject(response);
                JSONArray allCounty = responseInfo.getJSONArray("districts").getJSONObject(0).getJSONArray("districts");
                ArrayList<County> counties = new ArrayList<>();
                for (int i = 0; i < allCounty.length(); i++)
                {
                    JSONObject oneCity = allCounty.getJSONObject(i);
                    County temp = new County();
                    temp.setCountyName(oneCity.getString("name"));
                    temp.setCountyCode(oneCity.getString("adcode"));
                    temp.setCityId(cityId);
                    counties.add(temp);
                }
                Collections.sort(counties);
                for(County one : counties)
                {
                    one.save();
                }
                return true;
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //判断返回的内容是否有效可用
    private static boolean responAvaiable(String response)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject responseInfo = new JSONObject(response);
                if ("1".equals(responseInfo.getString("status")))
                {
                    return true;
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
}
