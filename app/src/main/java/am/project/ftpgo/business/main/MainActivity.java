package am.project.ftpgo.business.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import am.project.ftpgo.R;
import am.project.ftpgo.action.LocalActions;
import am.project.ftpgo.business.BaseActivity;
import am.project.ftpgo.business.ftp.FTPService;
import am.project.ftpgo.util.ContextUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.main_toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        View mSwitch = findViewById(R.id.main_v_switch);
        mSwitch.setOnClickListener(this);
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
                // TODO 启动
                break;
            case LocalActions.ACTION_FTP_STOPPED:
                // TODO 关闭
                break;
        }
    }

    @Override
    public void onRequestWriteExternalStoragePermissionsResult(boolean granted) {
        super.onRequestWriteExternalStoragePermissionsResult(granted);
        if (granted) {
            start();
        }
    }

    // Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_v_switch:
                if (FTPService.isStarted())
                    stop();
                else
                    start();
                break;
        }
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
}
