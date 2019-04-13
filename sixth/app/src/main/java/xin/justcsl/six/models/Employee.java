package xin.justcsl.six.models;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Employee extends LitePalSupport
{
    @Column(unique = true)
    private Integer id;

    private String name;
    private String sex;
    private String department;
    private String wages;

    public Integer getId()
    {
        return id;
    }

    public Employee()
    {
    }

    public Employee(String name, String sex, String department, String wages)
    {
        this.name = name;
        this.sex = sex;
        this.department = department;
        this.wages = wages;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getWages()
    {
        return wages;
    }

    public void setWages(String wages)
    {
        this.wages = wages;
    }

    @Override
    public String toString()
    {
        //StringBuffer 构造字符串更方便，同时此处使用 append 方法条理清晰一点
        StringBuffer sb = new StringBuffer();
        sb.append("ID:").append(id).append("\t姓名:").append(name).append("\t性别:")
                .append(sex).append("\t部门:").append(department).append("\t工资:").append(wages);

        return sb.toString();
    }
}
