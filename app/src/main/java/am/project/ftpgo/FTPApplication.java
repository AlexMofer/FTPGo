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
import android.text.TextUtils;

import am.project.ftpgo.action.GlobalActions;
import am.project.ftpgo.business.main.MainActivity;
import am.project.ftpgo.notification.NotificationChannelHelper;
import am.project.ftpgo.sharedpreferences.TempSharedPreferencesManager;

/**
 * 自定义Application，可定义应用全局共享变量
 * Created by Alex on 2017/12/15.
 */
public class FTPApplication extends Application {

    private static LocalBroadcastManager mLocalBroadcastManager;// 应用内部广播
    @SuppressWarnings("all")
    private static Context mContext;

    private final BroadcastReceiver mBroadcastReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final String action = intent.getAction();
                    if (action == null)
                        return;
                    onReceiveBroadcast(intent);
                }
            };

    private final BroadcastReceiver mLocalBroadcastReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveLocalBroadcast(context, intent);
                }
            };

    /**
     * 接收到广播
     *
     * @param intent 意图
     */
    protected void onReceiveBroadcast(Intent intent) {
        final String action = intent.getAction();
        if (TextUtils.isEmpty(action))
            return;
        switch (action) {
            case GlobalActions.ACTION_RESTART:
                if (TempSharedPreferencesManager.isAppOnForeground(mContext)) {
                    return;
                }
                MainActivity.restart(mContext);
                break;
        }
    }

    /**
     * 接收到本地广播
     *
     * @param context Context
     * @param intent  意图
     */
    protected void onReceiveLocalBroadcast(Context context, Intent intent) {

    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        TempSharedPreferencesManager.resetForegroundActivity(mContext);
        super.onCreate();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalActions.ACTION_RESTART);
        registerReceiver(mBroadcastReceiver, intentFilter);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        final IntentFilter localIntentFilter = new IntentFilter();
        mLocalBroadcastManager.registerReceiver(mLocalBroadcastReceiver, localIntentFilter);
        NotificationChannelHelper.updateNotificationChannel(this);
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(mBroadcastReceiver);
        mLocalBroadcastManager.unregisterReceiver(mLocalBroadcastReceiver);
        super.onTerminate();
    }

    /**
     * 获取应用级 Context
     *
     * @return Application 级别的 Context
     */
    public static Context getContext() {
        return mContext;
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
        return Build.VERSION.SDK_INT < 23 || ActivityCompat.checkSelfPermission(mContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
}
