package xin.justcsl.fifth;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MathService extends Service
{
    private final IBinder mBinder = new LocalBinder();

    class LocalBinder extends Binder
    {
        MathService getService()
        {
            return MathService.this;
        }

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Toast.makeText(this, "本地绑定:MathService", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Toast.makeText(this, "取消绑定:MathService", Toast.LENGTH_SHORT).show();
        return false;
    }


    public Integer add(Integer a, Integer b)
    {
        return a + b;
    }

    public Integer compare(Integer a, Integer b)
    {
        return a.compareTo(b);
    }
}
