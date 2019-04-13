package xin.justcsl.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText editTextForName;

    private RadioGroup radioGroupForSex;

    private CheckBox[] checkBoxForHobbies;

    private Spinner spinnerForGrade;

    private Button buttonForSum;

    private EditText editTextForSum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        buttonForSum.setOnClickListener(this);
    }

    private void init()
    {
        editTextForName = findViewById(R.id.editTextForName);

        radioGroupForSex = findViewById(R.id.radioGroupForSex);

        checkBoxForHobbies = new CheckBox[3];
        checkBoxForHobbies[0] = findViewById(R.id.checkBoxForSoccer);
        checkBoxForHobbies[1] = findViewById(R.id.checkBoxForBasket);
        checkBoxForHobbies[2] = findViewById(R.id.checkBoxForPing);

        spinnerForGrade = findViewById(R.id.spinnerForGrade);

        buttonForSum = findViewById(R.id.buttonForSum);

        editTextForSum = findViewById(R.id.editTextForSum);

        String[] ctype = new String[]{"未选择", "大一", "大二", "大三", "大四"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerForGrade.setAdapter(adapter);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonForSum:
                String name = editTextForName.getText().toString();
                if (name.trim().isEmpty())
                {
                    Toast.makeText(this, "姓名未输入！", Toast.LENGTH_SHORT).show();
                    break;
                }

                RadioButton clicked = findViewById(radioGroupForSex.getCheckedRadioButtonId());
                if (clicked == null)
                {
                    Toast.makeText(this, "请选择性别！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sex = clicked.getText().toString();

                String hobbies = "";
                for (CheckBox box : checkBoxForHobbies)
                {
                    if (box.isChecked())
                    {
                        hobbies = hobbies + box.getText().toString() + ' ';
                    }
                }

                if (spinnerForGrade.getSelectedItem().toString().equals("未选择"))
                {
                    Toast.makeText(this, "请选择年级！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String grade = spinnerForGrade.getSelectedItem().toString();

                String content = "大家好，我是" + name + "，性别" + sex + "，年纪" + grade + "，爱好：" + hobbies;
                editTextForSum.setText(content);

                break;
            default:
                break;
        }
    }
}
