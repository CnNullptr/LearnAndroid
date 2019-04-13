package xin.justcsl.six;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

import xin.justcsl.six.models.Employee;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button buttonForInsert;
    private Button buttonForSelectAll;
    private Button buttonForClear;
    private Button buttonForDropAll;

    private Button buttonForDropById;
    private Button buttonForSelectById;
    private Button buttonForUpdateById;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据库
        LitePal.initialize(this);

        //初始化控件
        init();
    }

    //初始化控件
    private void init()
    {
        buttonForInsert = findViewById(R.id.buttonForInsert);
        buttonForSelectAll = findViewById(R.id.buttonForSelectAll);
        buttonForClear = findViewById(R.id.buttonForClear);
        buttonForDropAll = findViewById(R.id.buttonForDropAll);

        buttonForDropById = findViewById(R.id.buttonForDropById);
        buttonForSelectById = findViewById(R.id.buttonForSelectById);
        buttonForUpdateById = findViewById(R.id.buttonForUpdateById);

        buttonForInsert.setOnClickListener(this);
        buttonForSelectAll.setOnClickListener(this);
        buttonForClear.setOnClickListener(this);
        buttonForDropAll.setOnClickListener(this);

        buttonForDropById.setOnClickListener(this);
        buttonForSelectById.setOnClickListener(this);
        buttonForUpdateById.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        TextView textForResult = findViewById(R.id.textForResult);

        Integer id;
        switch (v.getId())
        {
            case R.id.buttonForInsert:
                Employee insertOne = initBean();
                if (insertOne != null)
                {
                    insertOne.save();
                }
                break;
            case R.id.buttonForSelectAll:
                List<Employee> employees = LitePal.findAll(Employee.class);

                String allEmployees = "";
                //foreach
                for (Employee one : employees)
                {
                    //换行符
                    allEmployees = allEmployees + one + System.lineSeparator();
                }
                textForResult.setText(allEmployees);
                break;

            case R.id.buttonForClear:
                textForResult.setText("");
                break;

            case R.id.buttonForDropAll:
                //删除全部，没有约束条件
                LitePal.deleteAll(Employee.class, null);
                break;

            case R.id.buttonForDropById:
                id = initId();
                if (id != null)
                {
                    LitePal.delete(Employee.class, id);
                }
                break;
            case R.id.buttonForSelectById:
                id = initId();
                if (id != null)
                {
                    Employee oneForSelect = LitePal.find(Employee.class, id);
                    textForResult.setText(oneForSelect.toString());
                }
                break;
            case R.id.buttonForUpdateById:
                id = initId();
                if (id != null)
                {
                    Employee oneForUpdate = initBean();
                    if (oneForUpdate != null)
                    {
                        oneForUpdate.update(id);
                    }
                }
                break;
            default:
                break;
        }
    }

    //初始化 Edit 输入的内容，封装成 Bean，如果有一个数据未输入返回 null
    private Employee initBean()
    {
        EditText editForName = findViewById(R.id.editForName);
        RadioGroup radioGroupForSex = findViewById(R.id.radioGroupForSex);
        EditText editForDepartment = findViewById(R.id.editForDepartment);
        EditText editForWages = findViewById(R.id.editForWages);

        //姓名
        String name = editForName.getText().toString();
        if (name.trim().isEmpty())
        {
            Toast.makeText(this, "姓名未输入！", Toast.LENGTH_SHORT).show();
            return null;
        }
        //性别
        RadioButton clicked = findViewById(radioGroupForSex.getCheckedRadioButtonId());
        if (clicked == null)
        {
            Toast.makeText(this, "请选择性别！", Toast.LENGTH_SHORT).show();
            return null;
        }
        String sex = clicked.getText().toString();
        //部门
        String department = editForDepartment.getText().toString();
        if (department.trim().isEmpty())
        {
            Toast.makeText(this, "部门未输入！", Toast.LENGTH_SHORT).show();
            return null;
        }
        //部门
        String wages = editForWages.getText().toString();
        if (wages.trim().isEmpty())
        {
            Toast.makeText(this, "工资未输入！", Toast.LENGTH_SHORT).show();
            return null;
        }
        return new Employee(name, sex, department, wages);
    }

    private Integer initId()
    {
        EditText editForId = findViewById(R.id.editForId);

        String idStr = editForId.getText().toString().trim();
        if (idStr.isEmpty())
        {
            Toast.makeText(this, "ID未输入！", Toast.LENGTH_SHORT).show();
            return null;
        }
        return Integer.valueOf(idStr);
    }

}
