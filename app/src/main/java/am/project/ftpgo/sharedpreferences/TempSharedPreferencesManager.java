package am.project.ftpgo.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import am.project.ftpgo.FTPApplication;

/**
 * 临时SP数据
 * 存储应用运行时变化的数据
 * Created by Alex on 2017/12/25.
 */

public final class TempSharedPreferencesManager {
    private static final String SP_NAME = "temp";
    private static final int SP_MODE = Context.MODE_PRIVATE;
    private static final String KEY_FOREGROUND_ACTIVITY_COUNT = "foreground_activity_count";

    private TempSharedPreferencesManager() {
        //no instance
    }

    /**
     * 获取临时信息 SharedPreferences
     *
     * @param context Context
     * @return SharedPreferences
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        if (context == null) {
            context = FTPApplication.getContext();
        }
        return context.getSharedPreferences(SP_NAME, SP_MODE);
    }

    /**
     * 判断应用是否在前台
     *
     * @param context Context
     * @return 应用是否在前台
     */
    public static boolean isAppOnForeground(Context context) {
        return getSharedPreferences(context).getInt(KEY_FOREGROUND_ACTIVITY_COUNT, 0) > 0;
    }

    /**
     * 增加一个前台Activity
     *
     * @param context Context
     */
    public static void addForegroundActivity(Context context) {
        int count = getSharedPreferences(context).getInt(KEY_FOREGROUND_ACTIVITY_COUNT, 0);
        getSharedPreferences(context).edit().putInt(KEY_FOREGROUND_ACTIVITY_COUNT, ++count)
                .apply();
    }


    /**
     * 移除一个前台Activity
     *
     * @param context Context
     */
    public static void removeForegroundActivity(Context context) {
        int count = getSharedPreferences(context).getInt(KEY_FOREGROUND_ACTIVITY_COUNT, 0);
        count = count == 0 ? 0 : count - 1;
        getSharedPreferences(context).edit().putInt(KEY_FOREGROUND_ACTIVITY_COUNT, count)
                .apply();
    }

    /**
     * 重置前台Activity
     *
     * @param context Context
     */
    public static void resetForegroundActivity(Context context) {
        getSharedPreferences(context).edit().putInt(KEY_FOREGROUND_ACTIVITY_COUNT, 0).apply();
    }
}
