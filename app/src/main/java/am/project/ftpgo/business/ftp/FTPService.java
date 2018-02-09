package am.project.ftpgo.business.ftp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

import org.apache.ftpserver.FtpServer;

import java.util.ArrayList;

import am.project.ftpgo.FTPApplication;
import am.project.ftpgo.R;
import am.project.ftpgo.action.LocalActions;
import am.project.ftpgo.notification.NotificationMaker;
import am.project.ftpgo.sharedpreferences.SettingSharedPreferencesManager;
import am.project.ftpgo.util.ContextUtils;
import am.project.ftpgo.util.Utils;
import am.util.ftpserver.FTPHelper;
import am.util.ftpserver.FTPUser;

public class FTPService extends Service {
    private static boolean STARTED = false;

    private FtpServer mFTP;
    private boolean mAutoClose = false;
    private final BroadcastReceiver mBroadcastReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                        onConnectivityChanged();
                    }
                }
            };

    public FTPService() {
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FTPService.class);
        context.startService(starter);
    }

    public static void stop(Context context) {
        Intent stopper = new Intent(context, FTPService.class);
        context.stopService(stopper);
    }

    public static boolean isStarted() {
        return STARTED;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (STARTED || !FTPApplication.hasWriteExternalStoragePermission()) {
            return super.onStartCommand(intent, flags, startId);
        }
        int port = SettingSharedPreferencesManager.getPort(this);
        if (port < 0 || port > 65535) {
            port = 2020;
        }
        while (!Utils.isPortAvailable(port)) {
            port++;
            if (port > 65535) {
                port = 2020;
                break;
            }
        }
        final int maxLoginFailures = SettingSharedPreferencesManager
                .getMaxLoginFailures(this);
        final int loginFailureDelay = SettingSharedPreferencesManager
                .getLoginFailureDelay(this);
        final boolean anonymousEnable = SettingSharedPreferencesManager
                .isAnonymousEnable(this);
        final String directory = SettingSharedPreferencesManager
                .getAnonymousHomeDirectory(this);
        final ArrayList<FTPUser> users = SettingSharedPreferencesManager
                .getUsers(this);
        mFTP = FTPHelper.createServer(port, maxLoginFailures, loginFailureDelay, anonymousEnable,
                directory, users);
        try {
            mFTP.start();
        } catch (Exception e) {
            mFTP = null;
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            stopSelf(startId);
        }
        final String title = getString(R.string.ftp_notification_title);
        final String text = getString(R.string.ftp_notification_text,
                ContextUtils.getWifiIp(this), port);
        startForeground(NotificationMaker.ID_FTP,
                NotificationMaker.getFTPRunning(this, title, text));
        STARTED = true;
        FTPApplication.sendLocalBroadcast(LocalActions.ACTION_FTP_STARTED);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAutoClose = SettingSharedPreferencesManager.isAutoClose(this);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        if (mFTP != null) {
            mFTP.stop();
            mFTP = null;
        }
        STARTED = false;
        super.onDestroy();
        FTPApplication.sendLocalBroadcast(LocalActions.ACTION_FTP_STOPPED);
    }

    protected void onConnectivityChanged() {
        if (mAutoClose && !ContextUtils.isWifiConnected(this)) {
            stopSelf();
        }
    }
}
