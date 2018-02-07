package am.project.ftpgo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import am.project.ftpgo.notification.NotificationChannelHelper;
import am.project.ftpgo.sharedpreferences.TempSharedPreferencesManager;

/**
 * 自定义Application，可定义应用全局共享变量
 * Created by Alex on 2017/12/15.
 */
public class FTPApplication extends Application {

    private static LocalBroadcastManager mLocalBroadcastManager;// 应用内部广播
    @SuppressWarnings("all")
    private static FTPApplication mApplication;

    private final BroadcastReceiver mLocalBroadcastReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveLocalBroadcast(context, intent);
                }
            };

    /**
     * 获取Application
     *
     * @return Application
     */
    public static FTPApplication getInstance() {
        return mApplication;
    }

    /**
     * 发送本地广播
     *
     * @param action 要发送的广播动作
     */
    public static void sendLocalBroadcast(@NonNull String action) {
        sendLocalBroadcast(new Intent(action));
    }

    /**
     * 发送本地广播
     *
     * @param intent Intent
     */
    public static void sendLocalBroadcast(@NonNull Intent intent) {
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 判断是否拥有文件读写权限
     *
     * @return 是否拥有权限
     */
    public static boolean hasWriteExternalStoragePermission() {
        return Build.VERSION.SDK_INT < 23 || ActivityCompat.checkSelfPermission(getInstance(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 接收到本地广播
     *
     * @param context Context
     * @param intent  意图
     */
    @SuppressWarnings("unused")
    protected void onReceiveLocalBroadcast(Context context, Intent intent) {

    }

    @Override
    public void onCreate() {
        mApplication = this;
        TempSharedPreferencesManager.resetForegroundActivity(this);
        super.onCreate();

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        final IntentFilter localIntentFilter = new IntentFilter();
        mLocalBroadcastManager.registerReceiver(mLocalBroadcastReceiver, localIntentFilter);
        NotificationChannelHelper.updateNotificationChannel(this);
    }

    @Override
    public void onTerminate() {
        mLocalBroadcastManager.unregisterReceiver(mLocalBroadcastReceiver);
        super.onTerminate();
    }
}
