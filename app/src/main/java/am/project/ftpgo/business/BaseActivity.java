package am.project.ftpgo.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;

import am.project.ftpgo.action.LocalActions;
import am.project.ftpgo.sharedpreferences.TempSharedPreferencesManager;
import am.util.mvp.AMAppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * 基础框架Activity
 * Created by Alex on 2017/12/15.
 */

public abstract class BaseActivity extends AMAppCompatActivity {

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;

    @Override
    protected void onAddLocalAction(IntentFilter filter) {
        super.onAddLocalAction(filter);
        filter.addAction(LocalActions.ACTION_WRITE_EXTERNAL_STORAGE_PERMISSION_GRANTED);
    }

    @Override
    protected void onReceiveLocalBroadcast(Context context, Intent intent) {
        super.onReceiveLocalBroadcast(context, intent);
        final String action = intent.getAction();
        if (LocalActions.ACTION_WRITE_EXTERNAL_STORAGE_PERMISSION_GRANTED.equals(action)) {
            onWriteExternalStoragePermissionGranted();
        }
    }

    /**
     * 文件读写权限已授予
     */
    protected void onWriteExternalStoragePermissionGranted() {
    }

    /**
     * 判断是否拥有文件读写权限
     *
     * @return 是否拥有权限
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected final boolean hasWriteExternalStoragePermission() {
        return Build.VERSION.SDK_INT < 23 ||
                isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 请求文件读写权限
     */
    protected void requestWriteExternalStoragePermission() {
        if (Build.VERSION.SDK_INT < 23)
            return;
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 判断权限是否被授予
     *
     * @param permission 权限
     * @return 是否被授予
     */
    public final boolean isPermissionGranted(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                onRequestWriteExternalStoragePermissionsResult(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            }
        }
    }

    public void onRequestWriteExternalStoragePermissionsResult(boolean granted) {
        if (granted) {
            sendLocalBroadcast(
                    LocalActions.ACTION_WRITE_EXTERNAL_STORAGE_PERMISSION_GRANTED);
        }
    }

    @Override
    protected void onResume() {
        TempSharedPreferencesManager.addForegroundActivity(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        TempSharedPreferencesManager.removeForegroundActivity(this);
        super.onStop();
    }
}
