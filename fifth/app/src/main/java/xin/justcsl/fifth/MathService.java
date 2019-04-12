package xin.justcsl.fifth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MathService extends Service
{
    public MathService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
