package am.project.ftpgo.util;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

/**
 * Context 工具类
 * Created by Alex on 2017/12/26.
 */

public class ContextUtils {

    private ContextUtils() {
        //no instance
    }

    /**
     * 判断WIFI是否连接
     *
     * @param context Context
     * @return true:连接， false:未连接
     */
    public static boolean isWifiConnected(Context context) {
        final WifiManager manager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (manager == null)
            return false;
        final DhcpInfo info = manager.getDhcpInfo();
        return info != null && info.ipAddress != 0;
    }

    /**
     * 获取WIFI IP地址
     *
     * @param context Context
     * @return IP地址
     */
    public static String getWifiIp(Context context) {
        final WifiManager manager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (manager == null)
            return "0.0.0.0";
        final DhcpInfo info = manager.getDhcpInfo();
        if (info == null)
            return "0.0.0.0";
        final int ip = info.ipAddress;
        return (0xFF & ip) + "." + (0xFF & ip >> 8) + "." + (0xFF & ip >> 16) + "."
                + (0xFF & ip >> 24);
    }

    /**
     * 获取启动Intent
     *
     * @param context Context
     * @return 启动Intent
     */
    @SuppressWarnings("ConstantConditions")
    public static Intent getLaunchIntent(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(
                context.getPackageName())
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
