package am.project.ftpgo.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import am.project.ftpgo.FTPApplication;
import am.project.ftpgo.util.Utils;
import am.util.ftpserver.FTPUser;

/**
 * 设置SP数据
 * 存储应用设置数据
 * Created by Alex on 2017/12/25.
 */

public final class SettingSharedPreferencesManager {
    private static final String SP_NAME = "setting";
    private static final int SP_MODE = Context.MODE_PRIVATE;
    private static final String KEY_AUTO_CLOSE = "auto_close";
    private static final String KEY_PORT = "port";
    private static final String KEY_MAX_LOGIN_FAILURES = "max_login_failures";
    private static final String KEY_LOGIN_FAILURE_DELAY = "login_failure_delay";
    private static final String KEY_ANONYMOUS_ENABLE = "anonymous_enable";
    private static final String KEY_ANONYMOUS_HOME_DIRECTORY = "anonymous_home_directory";
    private static final String KEY_USERS = "users";

    private SettingSharedPreferencesManager() {
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
            context = FTPApplication.getInstance();
        }
        return context.getSharedPreferences(SP_NAME, SP_MODE);
    }

    /**
     * 获取是否自动关闭
     *
     * @param context Context
     * @return 是否自动关闭
     */
    public static boolean isAutoClose(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_AUTO_CLOSE, true);
    }

    /**
     * 存储是否自动关闭
     *
     * @param context Context
     * @param auto    是否自动关闭
     */
    public static void putAutoClose(Context context, boolean auto) {
        getSharedPreferences(context).edit().putBoolean(KEY_AUTO_CLOSE, auto).apply();
    }

    /**
     * 获取端口号
     *
     * @param context Context
     * @return 端口号
     */
    public static int getPort(Context context) {
        return getSharedPreferences(context).getInt(KEY_PORT, -1);
    }

    /**
     * 存储端口号
     *
     * @param context Context
     * @param port    端口号
     */
    public static void putPort(Context context, int port) {
        getSharedPreferences(context).edit().putInt(KEY_PORT, port).apply();
    }

    /**
     * 获取最大登录失败次数
     *
     * @param context Context
     * @return 最大登录失败次数
     */
    public static int getMaxLoginFailures(Context context) {
        return getSharedPreferences(context).getInt(KEY_MAX_LOGIN_FAILURES, 5);
    }

    /**
     * 存储最大登录失败次数
     *
     * @param context Context
     * @param max     最大登录失败次数
     */
    public static void putMaxLoginFailures(Context context, int max) {
        if (max < 1) {
            max = 1;
        }
        getSharedPreferences(context).edit().putInt(KEY_MAX_LOGIN_FAILURES, max).apply();
    }

    /**
     * 获取登录失败等待时长
     *
     * @param context Context
     * @return 登录失败等待时长
     */
    public static int getLoginFailureDelay(Context context) {
        return getSharedPreferences(context).getInt(KEY_LOGIN_FAILURE_DELAY, 60000);
    }

    /**
     * 存储登录失败等待时长
     *
     * @param context Context
     * @param delay   登录失败等待时长
     */
    public static void putLoginFailureDelay(Context context, int delay) {
        if (delay < 1) {
            delay = 1;
        }
        getSharedPreferences(context).edit().putInt(KEY_LOGIN_FAILURE_DELAY, delay).apply();
    }

    /**
     * 获取是否开启匿名
     *
     * @param context Context
     * @return 是否开启匿名
     */
    public static boolean isAnonymousEnable(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_ANONYMOUS_ENABLE, true);
    }

    /**
     * 存储是否开启匿名
     *
     * @param context Context
     * @param enable  是否开启匿名
     */
    public static void putAnonymousEnable(Context context, boolean enable) {
        getSharedPreferences(context).edit().putBoolean(KEY_ANONYMOUS_ENABLE, enable).apply();
    }

    /**
     * 获取匿名主目录
     *
     * @param context Context
     * @return 匿名主目录
     */
    public static String getAnonymousHomeDirectory(Context context) {
        final String directory = getSharedPreferences(context)
                .getString(KEY_ANONYMOUS_HOME_DIRECTORY, null);
        if (TextUtils.isEmpty(directory)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return directory;
    }

    /**
     * 存储匿名主目录
     *
     * @param context   Context
     * @param directory 匿名主目录
     */
    public static void putAnonymousHomeDirectory(Context context, String directory) {
        getSharedPreferences(context).edit().putString(KEY_ANONYMOUS_HOME_DIRECTORY, directory)
                .apply();
    }

    /**
     * 获取用户
     *
     * @param context Context
     * @return 用户
     */
    @Nullable
    public static ArrayList<FTPUser> getUsers(Context context) {
        System.out.println("lalalalalal--------------------------------------------:" + Utils.getFTPUserJson());

        return null;// TODO
    }

    /**
     * 存储用户
     *
     * @param context Context
     * @param users   用户
     */
    public static void putUsers(Context context, List<FTPUser> users) {
        // TODO

    }
}
