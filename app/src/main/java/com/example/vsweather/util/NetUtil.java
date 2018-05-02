package com.example.vsweather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;

    //Context类是应用环境全局信息接口(上下文)，启动activity、广播、接收事件等
    public static int getNetworkState(Context context)
    {
        //监测网络状态，发送广播时间，尝试连接，提供网络状态查询API
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo==null)
        {
            return NETWORN_NONE;
        }

        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE)
        {
            return NETWORN_MOBILE;
        }
        else if(nType == ConnectivityManager.TYPE_WIFI)
        {
            return NETWORN_WIFI;
        }
        return NETWORN_NONE;
    }
}
