package am.project.ftpgo.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;

import am.project.ftpgo.R;

/**
 * 通知频道辅助器
 * Created by Alex on 2017/12/18.
 */
public class NotificationChannelHelper {
    private static final String CHANEL_ID_LOW = "am.project.ftpgo.notification.CHANEL_LOW";// 不发出提示音

    /**
     * 更新通知渠道
     *
     * @param context Context
     */
    public static void updateNotificationChannel(Context context) {
        updateChannelLow(context);
    }

    static String getChannelLow(Context context) {
        updateChannelLow(context);
        return CHANEL_ID_LOW;
    }

    @Nullable
    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
    }

    private static void updateChannelLow(Context context) {
        if (Build.VERSION.SDK_INT < 26)
            return;
        NotificationManager manager = getNotificationManager(context);
        if (manager == null)
            return;
        NotificationChannel channel = manager.getNotificationChannel(CHANEL_ID_LOW);
        final String name = context.getString(R.string.notification_name_low);
        if (channel == null) {
            channel = new NotificationChannel(CHANEL_ID_LOW, name,
                    NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
        }
        channel.setName(name);
    }

}
