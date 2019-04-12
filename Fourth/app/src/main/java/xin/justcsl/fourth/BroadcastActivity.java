package xin.justcsl.fourth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView textForUsername;
    private EditText editForBroadcast;
    private Button buttonForBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        init();
    }

    private void init()
    {
        Intent intent = this.getIntent();
        String userName = intent.getStringExtra("userName");

        textForUsername = findViewById(R.id.textForUsername);
        textForUsername.setText("欢迎你：" + userName);

        editForBroadcast = findViewById(R.id.editForBroadcast);

        buttonForBroadcast = findViewById(R.id.buttonForBroadcast);
        buttonForBroadcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonForBroadcast:
                String content = editForBroadcast.getText().toString();
                if (content.trim().isEmpty())
                {
                    Toast.makeText(this, "未输入内容！", Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent = new Intent("xin.justcsl.fourth.MY_BROADCAST");

                //Android 8.0 中必须指定广播接收器的位置和类名
                intent.setClassName("xin.justcsl.fourth", "xin.justcsl.fourth.MyBroadcastReceiver");

                intent.putExtra("content", content);
                sendBroadcast(intent);
                Log.d("test", content);
                break;

            default:
                break;
        }
    }
}
