package xin.justcsl.seventh;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.litepal.LitePal;

import xin.justcsl.seventh.application.MyApplication;

public class MainActivity extends AppCompatActivity
{
    private AMapLocationClient locationClientSingle = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LitePal.initialize(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        String locationCity = prefs.getString("location", null);

        if(locationCity == null)
        {
            checkPermission();
        }
    }

    //检查并获取权限
    private void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //获取到权限后开始定位
            startSingleLocation();
        } else
        {
            startSingleLocation();
        }
    }

    //获取权限失败
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //用户同意权限,开始定位
                    startSingleLocation();
                } else
                {
                    //用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(MainActivity.this, "定位失败，请手动选择位置", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //启动定位
    private void startSingleLocation()
    {
        if (null == locationClientSingle)
        {
            locationClientSingle = new AMapLocationClient(MyApplication.getContext());
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(new AMapLocationListener()
        {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation)
            {
                if (aMapLocation != null)
                {
                    if (aMapLocation.getErrorCode() == 0)
                    {
                        String adCode = aMapLocation.getAdCode();

                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).edit();

                        editor.putString("defaultCity", adCode);
                        editor.apply();

                        Intent intent = new Intent(MyApplication.getContext(), WeatherActivity.class);
                        startActivity(intent);
                        finish();

                    } else
                    {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("locationTest", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
        locationClientSingle.startLocation();
    }

    // 停止定位客户端
    private void stopSingleLocation()
    {
        if (null != locationClientSingle)
        {
            locationClientSingle.stopLocation();
        }
    }
}
