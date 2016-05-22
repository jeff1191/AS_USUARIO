package es.ucm.as.presentacion.notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

import es.ucm.as.R;

/**
 * Created by Juan Lu on 17/05/2016.
 */
public class NotificacionEvento extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        PendingIntent aux = PendingIntent.getBroadcast(context, notificationId+6, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_notificacion)
                        .setContentTitle(intent.getExtras().getString("titulo"))
                        .setContentText("Revisa las notificaciones anteriores")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_notificacion))
                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + intent.getExtras().getInt("tono")))
                        .setContentIntent(aux)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.GREEN, 3000, 3000)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Hoy tienes el evento " + intent.getExtras().getString("texto"))
                                .setBigContentTitle(intent.getExtras().getString("titulo")));


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());
    }
}
