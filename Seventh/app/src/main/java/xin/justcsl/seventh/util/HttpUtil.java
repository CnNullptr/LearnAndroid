package xin.justcsl.seventh.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil
{
    //向指定的 address 发送请求报文
    public static void sendOkhttpRequest(String address, okhttp3.Callback callback)
    {
        Log.d("httpUtil", address);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
