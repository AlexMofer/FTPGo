package am.project.ftpgo.business.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import am.project.ftpgo.FTPApplication;
import am.project.ftpgo.R;
import am.project.ftpgo.action.LocalActions;
import am.project.ftpgo.business.BaseActivity;
import am.project.ftpgo.business.ftp.FTPService;
import am.project.ftpgo.util.ContextUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.main_toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onAddLocalAction(IntentFilter filter) {
        super.onAddLocalAction(filter);
        filter.addAction(LocalActions.ACTION_FTP_STARTED);
        filter.addAction(LocalActions.ACTION_FTP_STOPPED);
    }

    @Override
    protected void onReceiveLocalBroadcast(Context context, Intent intent) {
        super.onReceiveLocalBroadcast(context, intent);
        final String action = intent.getAction();
        if (TextUtils.isEmpty(action))
            return;
        switch (action) {
            case LocalActions.ACTION_FTP_STARTED:
            case LocalActions.ACTION_FTP_STOPPED:
                invalidateOptionsMenu();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        boolean isStarted = FTPService.isStarted();
        final MenuItem starter = menu.findItem(R.id.menu_main_start);
        final MenuItem stopper = menu.findItem(R.id.menu_main_stop);
        starter.setEnabled(true);
        stopper.setEnabled(true);
        if (isStarted) {
            starter.setVisible(false);
            stopper.setVisible(true);
        } else {
            starter.setVisible(true);
            stopper.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_start:
                start();
                return true;
            case R.id.menu_main_stop:
                stop();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void start() {
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
    }

    private void stop() {
        if (FTPService.isStarted()) {
            FTPService.stop(this);
        }
    }

    @Override
    public void onRequestWriteExternalStoragePermissionsResult(boolean granted) {
        super.onRequestWriteExternalStoragePermissionsResult(granted);
        if (granted) {
            start();
        }
    }

    /**
     * 重启应用
     *
     * @param context Context
     */
    public static void restart(Context context) {
        FTPApplication.sendLocalBroadcast(LocalActions.ACTION_STOP_ALL_ACTIVITY);
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }
}
