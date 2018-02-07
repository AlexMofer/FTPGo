package am.project.ftpgo.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import am.project.ftpgo.R;
import am.project.ftpgo.util.ContextUtils;

/**
 * 通知构造器
 * Created by Alex on 2017/8/24.
 */
public class NotificationMaker {

    public static final int ID_FTP = 1;


    public static Notification getFTPRunning(Context context, String title, String text) {
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
                .setContentIntent(PendingIntent.getActivity(context, ID_FTP,
                        ContextUtils.getLaunchIntent(context), PendingIntent.FLAG_UPDATE_CURRENT));
        return builder.build();
    }
}
