package am.project.ftpgo.business.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import am.drawable.RadialGradientRippleAnimationDrawable;
import am.project.ftpgo.R;
import am.project.ftpgo.action.LocalActions;
import am.project.ftpgo.business.BaseActivity;
import am.project.ftpgo.business.about.AboutActivity;
import am.project.ftpgo.business.ftp.FTPService;
import am.project.ftpgo.util.ContextUtils;
import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View mSwitch;
    private RadialGradientRippleAnimationDrawable mBackground;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            final View root = getWindow().getDecorView();
            int visibility = root.getSystemUiVisibility();
            visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                visibility = visibility | View.SYSTEM_UI_FLAG_IMMERSIVE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                visibility = visibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                visibility = visibility | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            root.setSystemUiVisibility(visibility);
        }
        setSupportActionBar(R.id.main_toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mSwitch = findViewById(R.id.main_v_switch);
        mSwitch.setOnClickListener(this);
        mSwitch.setActivated(FTPService.isStarted());
        mBackground = new RadialGradientRippleAnimationDrawable();
        mBackground.setColor(ColorStateList.valueOf(0x802196F3),
                ColorStateList.valueOf(0x002196F3));
        mBackground.setDuration(3000);
        setTitle(null);
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
        if (LocalActions.ACTION_FTP_STARTED.equals(action) ||
                LocalActions.ACTION_FTP_STOPPED.equals(action)) {
            mSwitch.setActivated(FTPService.isStarted());
            if (FTPService.isStarted()) {
                mSwitch.setBackgroundDrawable(mBackground);
                mBackground.start();
            } else {
                mBackground.end();
                mSwitch.setBackgroundDrawable(null);
            }
        }
    }

    @Override
    public void onRequestWriteExternalStoragePermissionsResult(boolean granted) {
        super.onRequestWriteExternalStoragePermissionsResult(granted);
        if (granted) {
            start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (super.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.menu_main_settings:
                break;
            case R.id.menu_main_about:
                AboutActivity.start(this);
                break;
        }

        return false;
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
