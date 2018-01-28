package am.project.ftpgo.business.switcher;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import am.project.ftpgo.R;
import am.project.ftpgo.business.BaseActivity;
import am.project.ftpgo.business.ftp.FTPService;
import am.project.ftpgo.util.ContextUtils;

public class SwitcherActivity extends BaseActivity {

    @Override
    protected int getContentViewLayout() {
        return 0;
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (FTPService.isStarted()) {
            stop();
        } else {
            start();
        }
    }

    private void stop() {
        Toast.makeText(this, R.string.switcher_toast_stop, Toast.LENGTH_SHORT).show();
        FTPService.stop(this);
        finish();
    }

    private void start() {
        Toast.makeText(this, R.string.switcher_toast_start, Toast.LENGTH_SHORT).show();
        if (!ContextUtils.isWifiConnected(this)) {
            Toast.makeText(this, R.string.main_toast_no_wifi, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!hasWriteExternalStoragePermission()) {
            requestWriteExternalStoragePermission();
            return;
        }
        if (!FTPService.isStarted()) {
            FTPService.start(this);
        }
        finish();
    }

    @Override
    public void onRequestWriteExternalStoragePermissionsResult(boolean granted) {
        super.onRequestWriteExternalStoragePermissionsResult(granted);
        if (granted) {
            start();
        }
    }
}
