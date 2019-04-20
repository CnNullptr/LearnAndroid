package xin.justcsl.seventh.db;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport implements Comparable<County>
{
    private Integer countyId;
    private String countyName;
    private String countyCode;

    private Integer cityId;

    public Integer getCountyId()
    {
        return countyId;
    }

    public void setCountyId(Integer countyId)
    {
        this.countyId = countyId;
    }

    public String getCountyName()
    {
        return countyName;
    }

    public void setCountyName(String countyName)
    {
        this.countyName = countyName;
    }

    public String getCountyCode()
    {
        return countyCode;
    }

    public void setCountyCode(String countyCode)
    {
        this.countyCode = countyCode;
    }

    public Integer getCityId()
    {
        return cityId;
    }

    public void setCityId(Integer cityId)
    {
        this.cityId = cityId;
    }

    @Override
    public int compareTo(County o)
    {
        Integer thisAdCode = Integer.valueOf(this.getCountyCode());
        Integer otherAdCode = Integer.valueOf(o.getCountyCode());

        return thisAdCode - otherAdCode;
    }
}
