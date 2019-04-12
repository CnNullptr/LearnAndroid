package xin.justcsl.fourth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String content = intent.getStringExtra("content");
        Toast.makeText(context, "收到广播，内容为" + content, Toast.LENGTH_LONG).show();
    }
}
