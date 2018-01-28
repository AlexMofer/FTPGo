package am.project.ftpgo.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import am.project.ftpgo.R;
import am.project.ftpgo.action.GlobalActions;

/**
 * 通知构造器
 * Created by Alex on 2017/8/24.
 */
public class NotificationMaker {

    public static final int ID_FTP = 1;

    public static Notification getFTPRunning(Context context, String title, String text) {
        final PendingIntent intent = PendingIntent.getBroadcast(context, ID_FTP,
                new Intent(GlobalActions.ACTION_RESTART), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                NotificationChannelHelper.getChannelLow(context))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_notification_ftp)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(intent);
        return builder.build();
    }

    @SuppressWarnings("unused")
    private static PendingIntent getEmptyIntent(Context context) {
        return PendingIntent.getActivity(context, 0,
                new Intent(), PendingIntent.FLAG_ONE_SHOT);
    }
}
