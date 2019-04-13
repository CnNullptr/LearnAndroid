package xin.justcsl.fifth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener
{
    //活动上的组件
    private Button buttonForBind;
    private Button buttonForUnbind;
    private Button buttonForSum;
    private Button buttonForCompare;

    private EditText editForNumOne;
    private EditText editForNumTwo;

    private TextView textForResult;

    private Integer numOne;
    private Integer numTwo;

    //服务相关
    private MathService mathService;
    private boolean isBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        buttonForBind = findViewById(R.id.buttonForBind);
        buttonForUnbind = findViewById(R.id.buttonForUnbind);
        buttonForSum = findViewById(R.id.buttonForSum);
        buttonForCompare = findViewById(R.id.buttonForCompare);

        buttonForBind.setOnClickListener(this);
        buttonForUnbind.setOnClickListener(this);
        buttonForSum.setOnClickListener(this);
        buttonForCompare.setOnClickListener(this);

        editForNumOne = findViewById(R.id.editForNumOne);
        editForNumTwo = findViewById(R.id.editForNumTwo);

        textForResult = findViewById(R.id.textForResult);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonForBind:
                if (!isBound)
                {
                    final Intent serviceIntent = new Intent(MainActivity.this, MathService.class);
                    bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
                    isBound = true;
                }
                break;

            case R.id.buttonForUnbind:
                if (isBound)
                {
                    isBound = false;
                    unbindService(connection);
                    mathService = null;
                }
                break;

            case R.id.buttonForSum:
                if (mathService == null)
                {
                    Toast.makeText(this, "未绑定服务", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (setNum())
                {
                    textForResult.setText("两数之和   " + mathService.add(numOne, numTwo));
                    break;
                } else
                {
                    Toast.makeText(this, "请正确输入参数", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.buttonForCompare:
                if (mathService == null)
                {
                    Toast.makeText(this, "未绑定服务", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (setNum())
                {
                    switch (mathService.compare(numOne, numTwo))
                    {
                        case -1:
                            textForResult.setText("第一个数小于第二个数");
                            break;
                        case 0:
                            textForResult.setText("两个数相等");
                            break;
                        case 1:
                            textForResult.setText("第一个数大于第二个数");
                            break;
                    }
                    break;
                } else
                {
                    Toast.makeText(this, "请正确输入参数", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    private ServiceConnection connection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mathService = ((MathService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mathService = null;
        }
    };

    //检验输入并转化成 Intger
    //使用 Intger 方便后续使用
    private boolean setNum()
    {
        String numOneStr = editForNumOne.getText().toString();
        String numTwoStr = editForNumTwo.getText().toString();

        if (numOneStr.trim().isEmpty() || numTwoStr.trim().isEmpty())
        {
            Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
            return false;
        }

        numOne = Integer.valueOf(numOneStr);
        numTwo = Integer.valueOf(numTwoStr);
        return true;
    }
}