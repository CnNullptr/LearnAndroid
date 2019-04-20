package xin.justcsl.seventh.db;

import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport implements Comparable<Province>
{
    //ORM 框架中最好不要使用基本数据类型（至少在 MyBatils 中是这样的，LitePal 没有深入研究）
    private Integer id;
    private String provinceName;
    private String provinceCode;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provienceName)
    {
        this.provinceName = provienceName;
    }

    public String getProvinceCode()
    {
        return provinceCode;
    }

    public void setProvinceCode(String provienceCode)
    {
        this.provinceCode = provienceCode;
    }

    @Override
    public int compareTo(Province o)
    {
        Integer thisAdCode = Integer.valueOf(this.getProvinceCode());
        Integer otherAdCode = Integer.valueOf(o.getProvinceCode());

        return thisAdCode - otherAdCode;
    }
}
