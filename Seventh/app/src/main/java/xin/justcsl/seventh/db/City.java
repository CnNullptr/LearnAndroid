package xin.justcsl.seventh.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport implements Comparable<City>
{
    private Integer id;
    private String cityName;
    private String cityCode;

    private Integer provinceId;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityCode()
    {
        return cityCode;
    }

    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }

    public Integer getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(Integer provienceId)
    {
        this.provinceId = provienceId;
    }

    @Override
    public int compareTo(City o)
    {
        Integer thisAdCode = Integer.valueOf(this.getCityCode());
        Integer otherAdCode = Integer.valueOf(o.getCityCode());

        return thisAdCode - otherAdCode;
    }
}
