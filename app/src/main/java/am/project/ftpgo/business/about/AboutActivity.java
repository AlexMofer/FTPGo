package am.project.ftpgo.business.about;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import am.project.ftpgo.BuildConfig;
import am.project.ftpgo.R;
import am.project.ftpgo.business.BaseActivity;
import am.project.ftpgo.business.browser.BrowserActivity;
import androidx.annotation.Nullable;

/**
 * 关于
 * Created by Alex on 2019/3/14.
 */
public class AboutActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 16) {
            final View view = getWindow().getDecorView();
            int visibility = view.getSystemUiVisibility();
            visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            view.setSystemUiVisibility(visibility);
        }
        setSupportActionBar(R.id.about_toolbar);
        ((TextView) findViewById(R.id.about_tv_version)).setText(
                getString(R.string.about_version, BuildConfig.VERSION_NAME));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_privacy:
                BrowserActivity.start(this, "file:///android_asset/html/privacy.html");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
