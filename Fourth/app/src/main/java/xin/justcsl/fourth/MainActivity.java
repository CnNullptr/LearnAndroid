package xin.justcsl.fourth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button buttonForYes;
    private Button buttonForNo;
    private EditText editForUsername;
    private EditText editForPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        buttonForYes = findViewById(R.id.buttonForYes);
        buttonForNo = findViewById(R.id.buttonForNo);
        editForUsername = findViewById(R.id.editForUsername);
        editForPassword = findViewById(R.id.editForPassword);

        buttonForYes.setOnClickListener(this);
        buttonForNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonForYes:
                String userName = editForUsername.getText().toString();
                if (userName.trim().isEmpty())
                {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    break;
                }

                String password = editForPassword.getText().toString();
                if (password.trim().isEmpty())
                {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (userName.equals("admin") && password.equals("123456"))
                {
                    Intent intent = new Intent(this, BroadcastActivity.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                } else
                {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.buttonForNo:
                editForUsername.setText("");
                editForPassword.setText("");
                break;
            default:
                break;
        }
    }
}
